#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <unordered_map>
#include <algorithm>
#include <list>
 
using namespace std;
 
int main()
{
    freopen("formation.in", "r", stdin);
    freopen("formation.out", "w", stdout);
    int n = 0;
    int m = 0;
    vector<int> spisok;
    spisok.push_back(0);
    spisok.push_back(1);
    spisok.push_back(0);
    cin >> n;
    cin >> m;
    string s;
    for  (int i = 0; i < m; i++) {
        cin >> s;
        if (s == "left") {
            int z = 0;
            cin >> z;
            int x = 0;
            cin >> x;
            spisok.insert(find(spisok.begin(), spisok.end(), x), z);
        } else if (s == "right") {
            int z = 0;
            cin >> z;
            int x = 0;
            cin >> x;
            spisok.insert(++find(spisok.begin(), spisok.end(), x), z);
        } else if (s == "name") {
            int z = 0;
            cin >> z;
            auto pos = find(spisok.begin(), spisok.end(), z);
            cout << *prev(pos) << " " << *next(pos) << "\n";
        } else if (s == "leave") {
            int z = 0;
            cin >> z;
            spisok.erase(find(spisok.begin(), spisok.end(), z));
        }
    }
    fclose (stdin);
    fclose (stdout);
}
