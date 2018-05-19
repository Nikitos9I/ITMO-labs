#include <iostream>
#include <vector>
#include <unordered_set>
#include <queue>
#include <map>
 
/**
 * HomeWork.Discret_math.lab5
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */
 
using namespace std;
 
const int N = 50007;
const int ALPHABET_SIZE = 26;
 
int n;
int m;
int k;
 
int terminalCount = 0;
int transitionsCount = 0;
int nNew = 0;
 
int transitions[N][ALPHABET_SIZE];
int transitionsNew[N][ALPHABET_SIZE];
vector<int> transitionsReverse[N][ALPHABET_SIZE];
 
bool terminal[N];
bool visited[N];
bool newTerminal[N];
int iLoveJava[N];
 
void dfs(int v) {
    visited[v] = true;
    for (int i = 0; i < ALPHABET_SIZE; i++) {
        if (!visited[transitions[v][i]]) {
            dfs(transitions[v][i]);
        }
    }
}
 
queue<pair<int, int>> q;
vector<unordered_set<int>> p;
 
void solveEquals() {
    int classs[N];
 
    unordered_set<int> t;
    unordered_set<int> nt;
    vector<unordered_set<int>> sss;
 
    for (int i = 0; i < n; i++) {
        if (terminal[i]) {
            t.insert(i);
            classs[i] = 0;
        } else {
            nt.insert(i);
            classs[i] = 1;
        }
    }
 
    p.push_back(t);
    p.push_back(nt);
    sss.push_back(t);
    sss.push_back(nt);
 
    int adder = -1;
    for (; ++adder < ALPHABET_SIZE;) {
        q.push({0, adder});
        q.push({1, adder});
    }
 
    while (!q.empty()) {
        const pair<int, int> cur = q.front();
 
        q.pop();
        map<int, vector<int>> inv;
 
        for (int q: sss[cur.first]) {
            for (int r: transitionsReverse[q][cur.second]) {
                int i = classs[r];
                inv[i].push_back(r);
            }
        }
 
        for (pair<int, vector<int>> x: inv) {
            if (!x.second.empty()) {
                if (inv[x.first].size() < p[x.first].size()) {
                    unordered_set<int> tmp;
                    p.push_back(tmp);
 
                    for (int r: inv[x.first]) {
                        p[x.first].erase(r);
                        p[p.size() - 1].insert(r);
                    }
 
                    for (int r: p[p.size() - 1]) {
                        classs[r] = static_cast<int>(p.size() - 1);
                    }
 
                    sss.push_back(p[p.size() - 1]);
 
                    for (int i = 0; i < ALPHABET_SIZE; i++) {
                        q.push(make_pair(sss.size() - 1, i));
                    }
                }
            }
        }
    }
}
 
void makeEqualDKA() {
    for (const unordered_set<int>& x: p) {
        if (x.find(0) != x.end()) {
            for (int y: x) {
                iLoveJava[y] = 0;
            }
        }
 
        if (x.find(1) != x.end() && iLoveJava[1] == -1) {
            nNew++;
 
            for (int y : x) {
                iLoveJava[y] = 1;
            }
        }
    }
 
    for (int j = 0; j < p.size(); j++) {
        unordered_set<int> x = p[j];
 
        int i = *x.begin();
 
        if (!visited[i] || iLoveJava[i] != -1) {
            continue;
        }
 
        iLoveJava[i] = ++nNew;
 
        for (int y: p[j]) {
            iLoveJava[y] = nNew;
        }
    }
 
    for (int i = 0; i < n; i++) {
        if (terminal[i] && iLoveJava[i] != -1 && !newTerminal[iLoveJava[i]]) {
            newTerminal[iLoveJava[i]] = true;
            terminalCount++;
        }
    }
 
    terminalCount -= newTerminal[0]? 1: 0;
 
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < ALPHABET_SIZE; j++) {
            if (iLoveJava[i] > 0 && iLoveJava[transitions[i][j]] > 0 && transitionsNew[iLoveJava[i]][j] == 0) {
                transitionsNew[iLoveJava[i]][j] = iLoveJava[transitions[i][j]];
                transitionsCount++;
            }
        }
    }
}
 
void printDKA() {
    cout << nNew;
    cout << " ";
    cout << transitionsCount;
    cout << " ";
    cout << terminalCount;
    cout << endl;
 
    int iterator = 0;
    while (++iterator < n) {
        if (newTerminal[iterator]) {
            cout << iterator;
            cout << " ";
        }
    }
 
    cout << endl;
 
    iterator = 0;
    while (++iterator < n) {
        int inner = -1;
        while (++inner < ALPHABET_SIZE) {
            if (transitionsNew[iterator][inner] != 0) {
                cout << iterator;
                cout << " ";
                cout << transitionsNew[iterator][inner];
                cout << " ";
                cout << (char)('a' + inner);
                cout << endl;
            }
        }
    }
}
 
void readDKA() {
    cin >> n;
    cin >> m;
    cin >> k;
    n++;
 
    for (int i = 0; i < n; i++) {
        terminal[i] = false;
        iLoveJava[i] = -1;
        newTerminal[i] = false;
        visited[i] = false;
        for (int j = 0; j < ALPHABET_SIZE; j++) {
            transitions[i][j] = 0;
            transitionsNew[i][j] = 0;
        }
    }
 
    for (int i = 0; i < k; i++) {
        int a;
        cin >> a;
 
        terminal[a] = true;
    }
 
    for (int i = 0; i < m; i++) {
        int a;
        cin >> a;
        int b;
        cin >> b;
        char c;
        cin >> c;
 
        transitions[a][c - 'a'] = b;
    }
 
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < ALPHABET_SIZE; j++) {
            transitionsReverse[transitions[i][j]][j].push_back(i);
        }
    }
}
 
int main() {
//    freopen("in.txt", "r", stdin);
//    freopen("out.txt", "w", stdout);
 
    freopen("fastminimization.in", "r", stdin);
    freopen("fastminimization.out", "w", stdout);
 
    readDKA();
    dfs(1);
    solveEquals();
    makeEqualDKA();
    printDKA();
 
    return 0;
}
