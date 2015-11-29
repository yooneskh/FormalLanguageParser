#include "heads.h"

extern string initVar;


void removeUselessVars(vector<RULE> &rules) {

	vector<string> variables, terminals;

	extractTerminals(rules, terminals);
	extractVariables(rules, variables);

	removeNonTerminateables(rules, variables, terminals);
	removeNonReachables(rules, variables, terminals);

}

void extractTerminals(vector<RULE> &rules, vector<string> &terminals) {

	set<string> result;

	for (auto &rule : rules) {

		string test = rule.first + rule.second;

		FR (i, test.size()) {
			if (test[i] >= 'a' && test[i] <= 'z') {
				result.insert(string (1, test[i]));
			}
		}

	}

	terminals.clear();
	
	for (auto &term : result) {
		terminals.push_back(term);
	}

}

void extractVariables(vector<RULE> &rules, vector<string> &variables) {

	set<string> result;

	for (auto &rule : rules) {

		string test = rule.first + rule.second;

		FR (i, test.size()) {
			if (test[i] >= 'A' && test[i] <= 'Z') {
				result.insert(string (1, test[i]));
			}
		}

	}

	variables.clear();
	
	for (auto &term : result) {
		variables.push_back(term);
	}

}

void removeNonTerminateables(vector<RULE> &rules, vector<string> &variables, vector<string> &terminals) {

	set<string> termables;

	for (string t : terminals) {
		termables.insert(t);
	}

	bool again = true;

	while (again) {
		
		again = false;

		for (auto &rule : rules) {
			if ( (termables.find(rule.first) == termables.end()) && (rule.second == NULLSTR || isMadeOf(rule.second, termables)) ) {
				termables.insert(rule.first);
				again = true;
			}
		}

	}

	vector<RULE> newRules;

	for (RULE &rule : rules) {
		if (termables.find(rule.first) != termables.end() && isMadeOf(rule.second, termables)) {
			newRules.push_back(rule);
		}
	}

	rules.clear();
	rules.insert(rules.end(), newRules.begin(), newRules.end());

	extractVariables(rules, variables);
	extractTerminals(rules, terminals);
}

void removeNonReachables(vector<RULE> &rules, vector<string> &variables, vector<string> &terminals) {

	set<string> reaches;
	
	reaches.insert(initVar);

	bool again = true;

	while (again) {

		again = false;

		for (auto &rule : rules) {
			if (reaches.find(rule.first) != reaches.end()) {
				FR (i, rule.second.size()) {
					if (rule.second[i] >= 'A' && rule.second[i] <= 'Z' && reaches.find(string(1, rule.second[i])) == reaches.end()) {
						again = true;
						reaches.insert(string(1, rule.second[i]));
					}
				}
			}
		}

	}

	vector<RULE> newRules;

	for (string t : terminals) {
		reaches.insert(t);
	}

	for (auto &rule : rules) {
		if (reaches.find(rule.first) != reaches.end() && isMadeOf(rule.second, reaches)) {
			newRules.push_back(rule);
		}
	}

	rules.clear();
	rules.insert(rules.begin(), newRules.begin(), newRules.end());

	extractVariables(rules, variables);
	extractTerminals(rules,terminals);

}

bool isMadeOf(string hay, set<string> needles) {

	int i = 0;
	bool could = true;

	while (could && i < hay.size()) {

		could = false;

		for (string needle : needles) { 

			if (hay.substr(i, needle.size()) == needle) {
				i += needle.size();
				could = true;
			}

		}

	}

	return could;

}

bool containsAny(string hay, set<string> needles) {

	for (string needle : needles) {
		if (hay.find(needle) != string::npos) return true;
	}

	return false;

}