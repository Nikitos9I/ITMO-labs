#include <iostream>
#include <vector>

/**
 * HomeWork.Algoritms.lab6
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */
 
using namespace std;
 
vector< vector<unsigned int> > edges(200000, vector<unsigned int>());
vector<unsigned int> vertSize(200000);
vector<int> beforeCentr(200000);
 
size_t dfs(unsigned int p, unsigned int v) {
    size_t res = 1;
    for (size_t i = 0; i < edges[v].size(); ++i) {
        if (edges[v][i] != p) {
            res += dfs(v, edges[v][i]);
        }
    }
 
    vertSize[v] = res;
    return res;
}
 
unsigned int searchCentr(unsigned int v);
 
void make(unsigned int v, int prev) {
    unsigned int cur = searchCentr(v);
    beforeCentr[cur] = prev;
    vector<unsigned int> nextStep;
 
    for (unsigned int i : edges[cur]) {
        nextStep.push_back(i);
    }
 
    edges[cur].clear();
    for (unsigned int i : nextStep) {
        for (auto j = edges[i].begin(); j != edges[i].end(); ++j) {
            if (*j == cur) {
                edges[i].erase(j);
                break;
            }
        }
        make(i, cur);
    }
}
 
unsigned int searchCentr(unsigned int v) {
    dfs(v, v);
    size_t size = vertSize[v];
    bool end = false;
    unsigned int p = v;
    while (!end) {
        end = true;
        for (size_t i = 0; i < edges[v].size(); ++i) {
            if (edges[v][i] != p && vertSize[edges[v][i]] > size / 2) {
                end = false;
                p = v;
                v = edges[v][i];
                break;
            }
        }
    }
 
    return v;
}
 
int main() {
    size_t n;
    cin >> n;
    unsigned int u, v;
 
    for (size_t i = 1; i < n; ++i) {
        cin >> u >> v;
        edges[--u].push_back(--v);
        edges[v].push_back(u);
    }
 
    make(0, -1);
    for (size_t i = 0; i < n; ++i) {
        cout << beforeCentr[i] + 1 << ' ';
    }
 
    return 0;
}
