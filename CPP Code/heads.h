#include <iostream>
#include <fstream>
#include <string>
#include <set>
#include <map>
#include <vector>

using namespace std;

#define RULE pair<string, string>

#define FOR(i, begin, end) for (int i = (begin); i < (end); i++)
#define FR(i, end) FOR(i, 0, end)

#define NULLSTR "~"

void derive(vector<RULE> &, string, string, bool &, vector<int> &);
void printResult(vector<int> &, vector<RULE> &, string);
void extractTerminals(vector<RULE> &rules, vector<string> &terminals);
void extractVariables(vector<RULE> &rules, vector<string> &variables);
void removeNonTerminateables(vector<RULE> &rules, vector<string> &variables, vector<string> &terminals);
void removeNonReachables(vector<RULE> &rules, vector<string> &variables, vector<string> &terminals) ;
void removeUselessVars(vector<RULE> &rules);

bool isMadeOf(string hay, set<string> needles);
bool containsAny(string hay, set<string> needles);
