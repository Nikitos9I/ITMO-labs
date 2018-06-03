#include <iostream>
#include <vector>
#include <cstring>

/**
 * HomeWork.Algoritms.lab6
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */
 
using namespace std;
 
const int max_n = 300007;
const int inf = int(2e9);
 
vector<int> to[max_n];
 
int s[max_n];
int p[max_n];
int ind_st[max_n];
int leader[max_n];
 
long long promise_sum[max_n * 4];
long long st_sum[max_n * 4];
long long d[max_n];
 
int n;
 
long long get(int v);
 
void push(int v, int ls, int rs) {
    if (promise_sum[v] != 0) {
        promise_sum[ls] += promise_sum[v];
        promise_sum[rs] += promise_sum[v];
        promise_sum[v] = 0;
        st_sum[v] = get(ls) + get(rs);
    }
}
 
long long get(int v) {
    return promise_sum[v] + st_sum[v];
}
 
long long get_ind(int v, int l, int r, int ind) {
    if (r - l == 1)  {
        return get(v);
    } else {
        int m = (l + r) /2, ls = v * 2 +1, rs = ls + 1;
        push(v, ls, rs);
 
        if (ind < m) {
            return get_ind(ls, l, m, ind);
        } else {
            return get_ind(rs, m, r, ind);
        }
    }
}
 
inline long long find(int v) {
    return get_ind(0, 0, n, ind_st[v]);
}
 
void attache_tree(int root, int parent_root) {
    for (int i = 0, mem_to; i < (int)to[root].size(); ++i) {
        mem_to = to[root][i];
        if (mem_to != parent_root) {
            p[mem_to] = root;
            d[mem_to] = d[root] +1;
            attache_tree(mem_to, root);
        }
    }
}
 
int dfs_for_calc_s(int v) {
    int size = 0;
 
    for (int i = 0; i < (int)to[v].size(); ++i ) {
        if (p[v] != to[v][i]) {
            size += dfs_for_calc_s(to[v][i]);
        }
    }
 
    return s[v] = (size == 0? 1: size + 1);
}
 
void dfs_for_build_lhd(int v, int ldr) {
    static int time = -1;
    time++;
    ind_st[v] = time;
    leader[v] = ldr;
 
    int ind_heavy = inf, size = 0;
    for (int i = 0, mem_to; i < (int)to[v].size(); i++) {
        mem_to = to[v][i];
 
        if (p[v] != mem_to)
            if (size < s[mem_to]) {
                ind_heavy = mem_to;
                size = s[mem_to];
            }
    }
 
    if (ind_heavy  != inf)
        dfs_for_build_lhd(ind_heavy, ldr);
 
    for (int i = 0, mem_to; i < (int)to[v].size(); i++) {
        mem_to = to[v][i];
 
        if (mem_to != p[v] && mem_to != ind_heavy) {
            dfs_for_build_lhd(mem_to, mem_to);
        }
    }
}
 
void addlr(int v, int l, int r, int L, int R, int val) {
    if (r == R && l == L) {
        promise_sum[v] += val;
    } else {
        int m = (l + r) /2, ls = v * 2 + 1, rs = ls + 1;
        push(v, ls, rs);
 
        if (L < m) {
            addlr(ls, l, m, L, min(R, m), val);
        }
 
        if (R > m) {
            addlr(rs, m, r, max(m, L), R, val);
        }
 
        st_sum[v] = get(ls) + get(rs);
    }
}
 
void add_LHD(int v, int u, int val) {
    while (true) {
        if (leader[v] == leader[u]) {
            if (d[v] > d[u]) swap(v, u);
            addlr(0, 0, n, ind_st[v], ind_st[u] + 1, val);
            break;
        }
 
        if (d[leader[v]] < d[leader[u]]) swap(v, u);
        addlr(0, 0, n, ind_st[leader[v]], ind_st[v] + 1, val);
        v = leader[v];
        v = p[v];
    }
}
 
void build_LHD() {
    p[0] = 0;
    d[0] = 0;
    attache_tree(0, 0);
    dfs_for_calc_s(0);
    dfs_for_build_lhd(0, 0);
}
 
int main() {
    cin >> n;
 
    for (int i = 0; i < n-1; ++i) {
        int v, u;
        cin >> u >> v;
        to[--v].push_back(--u);
        to[u].push_back(v);
    }
 
    build_LHD();
 
    int m; char com;
    cin >> m;
 
    for (int i = 0; i < m; ++i) {
        int a, b, c;
        cin >> com;
        if (com == '+') {
            cin >> a >> b >> c;
            add_LHD(--a, --b, c);
        } else {
            cin >> a;
            cout << find(--a) << endl;
        }
    }
 
    return 0;
}
