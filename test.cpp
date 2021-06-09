#include<bits/stdc++.h>

using namespace std;

vector<string> readfile(string file)
{
    vector<string> ans;
    ifstream inf (file.c_str());
    if (!inf)
    {
        cerr << "Uh oh, file could not be opened for reading!" << endl;
        exit(1);
    }
    while (inf)
    {
        string s;
        getline(inf, s);
        ans.emplace_back(s);
    }
    sort(begin(ans), end(ans));
    return ans;
    
}

int main(){
    ios::sync_with_stdio(false);
    cin.tie();
    
    // Test frequentItemSet
    vector<string> itemSetLib = readfile("output_item_list_lib.txt");
    vector<string> itemSet = readfile("output_item_list_manual.txt");
    if(itemSet == itemSetLib){
        cout<<"Correct frequentItemSet!";
    } else {
        cout<<"Incorrect frequentItemSet!";
    }
    
    // Test rule
    vector<string> assocRuleLib = readfile("output_rule_lib.txt");
    vector<string> assocRule = readfile("output_rule_manual.txt");
    if(assocRuleLib == assocRule){
        cout<<"Correct frequentItemSet!";
    } else {
        cout<<"Incorrect frequentItemSet!";
    }
}