#include "heads.h"

vector<RULE> rules;

string initVar;


void main() {
	
	fstream rulecin("rules.txt");

	cout << "Please enter initial variable: ";
	cin  >> initVar;

	cout << "Please enter rules in this form X --> Y or * to finish." << endl;

	while (true) {

		string p1, temp, p2; cin >> p1; 

		if (p1 == "*") break;

		cin >> temp >> p2;

		rules.push_back(pair<string, string> (p1, p2));

	}

	string t;

	cout << "Do you want to remove useless variables? (y/n) ";
	cin  >> t;

	if (t == "y" || t == "yes") {

		removeUselessVars(rules);

		cout << endl << "New rules are: " << endl;

		for (auto &rule : rules) {
			cout << "\t" << rule.first << " --> " << rule.second << endl;
		}

		cout << endl;

	}

	cout << "Please enter the sentence to derive or * to finish" << endl;

	while (true) {

		string text; cout << endl << ">> "; cin >> text;

		if (text == "*") break;

		bool couldDerive = false;
		vector<int> res;

		derive(rules, text, initVar, couldDerive, res);

		if (couldDerive) {

			cout << endl;

			printResult(res, rules, initVar);

		}
		else {
			cout << "Could not derive this sentence." << endl;
		}

	}

}