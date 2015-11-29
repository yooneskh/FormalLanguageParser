#include "heads.h"

#define ISENT pair<string, vector<int>>

bool hasHope(string sentence, string target) {
	return sentence.size() <= target.size();
}

void derive(vector<RULE> &rules, string target, string initVar, bool &couldDerive, vector<int> &res) {

	set<ISENT> inters, nexts;

	inters.insert( ISENT(initVar, vector<int>()) );

	while (inters.size()) {

		for (auto &sent : inters) {

			if (!hasHope(sent.first, target)) continue;

			for (int i = 0; i < rules.size(); i++) {

				RULE rule = rules[i];

				size_t foundIndex = sent.first.find(rule.first);

				if (foundIndex != string::npos) {

					string next = sent.first; 
					next.replace(foundIndex, rule.first.size(), rule.second);

					vector<int> tres = sent.second;
					tres.push_back(i + 1);

					if (next == target) {
						couldDerive = true;
						res = tres;
						return;
					}

					nexts.insert( ISENT(next, tres) );
				}

			}

		}

		inters.clear();
		inters.insert(nexts.begin(), nexts.end());
		nexts.clear();

	}
}

void printResult(vector<int> &result, vector<RULE> &rules, string initVar) {

	string preve = initVar;

	for (int i = 0; i < result.size(); i++) {

		string nexts = preve;

		nexts.replace(nexts.find(rules[result[i] - 1].first), rules[result[i] - 1].first.size(), rules[result[i] - 1].second);

		cout << "\t" << preve << "\t--(" << result[i] << ")--> " << nexts << endl;

		preve = nexts;

	}

}