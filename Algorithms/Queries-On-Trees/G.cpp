#include <vector>
#include <iostream>
#include <stdio.h>
#include <string.h>

/**
 * HomeWork.Algoritms.lab6
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */
 
using namespace std;
 
typedef long long ll;
 
#define pb push_back
#define for_tests(t, tt) int t; scanf ("%d", &t); for (int tt = 1; tt <= t; tt++)
#define UMZA 2
 
const int MAXN = 100010;
 
int degs;
int subsz[MAXN], h[MAXN], hmax[MAXN], chainno[MAXN], ult[MAXN], inichain[MAXN], indchain[MAXN], s[MAXN], nchain, pai[MAXN];
 
vector<int> adj[MAXN];
 
struct arv{
    int lz, s;
}   tree[4*MAXN];
 
void go(int v) {
    subsz[v] = 1;
    if (pai[v] != -1)
        h[v] = h[pai[v]]+1;
    for (int a=0;a<adj[v].size();a++) {
        int nxt = adj[v][a];
        go(nxt);
        subsz[v] += subsz[nxt];
    }
}
 
void HDL(int v) {
    s[degs] = v;
    if (inichain[nchain] == -1) inichain[nchain] = degs;
    indchain[v] = degs++;
    chainno[v] = nchain;
 
    int mai = -1;
    int imai = 0;
    for (int nxt : adj[v]) {
        if (mai <= subsz[nxt]) {
            mai = subsz[nxt];
            imai = nxt;
        }
    }
 
    if (mai != -1)
        HDL(imai);
 
    for (int nxt : adj[v]) {
        if (nxt == imai) continue;
        nchain++;
        HDL(nxt);
    }
}
 
int qry(int idx,int i,int f, int l, int r) {
    if (i > r || f < l) return 0;
 
    if (tree[idx].lz != 0) {
        if (i != f) {
            int op = tree[idx].lz;
            tree[idx * 2].lz = op;
            tree[idx * 2 + 1].lz = op;
            int m = (i + f) / 2;
//            tree[idx * 2].s = (m - i + 1) * (op - 1);
//            tree[idx * 2 + 1].s = (f - m) * (op - 1);
        }
 
        tree[idx].lz = 0;
    }
 
    if (i >= l && f <= r) {
        return tree[idx].s;
    }
 
    int m = (i+f)/2;
    return qry(idx * 2, i, m, l, r) + qry(idx * 2 + 1, m + 1, f, l, r);
}
 
 
void upd(int idx, int i, int f,int l,int r,int op) {
    if (i > r || f < l) return;
 
    if (tree[idx].lz != 0) {
        if (i != f) {
            int op = tree[idx].lz;
            int m = (i + f) / 2;
//            tree[idx * 2].s = (m - i + 1) * (op - 1);
//            tree[idx * 2 + 1].s = (f - m) * (op - 1);
            tree[idx * 2].lz = op;
            tree[idx * 2 + 1].lz = op;
        }
 
        tree[idx].lz = 0;
    }
 
    if (i >= l && f <= r) {
        tree[idx].s = (f - i + 1) * (op - 1);
        tree[idx].lz = op;
        return;
    }
 
    int m = (i + f) / 2;
 
    upd(idx * 2, i, m, l, r, op);
    upd(idx * 2 + 1, m + 1, f, l, r, op);
    tree[idx].s = tree[idx * 2].s + tree[idx * 2 + 1].s;
}
 
int main () {
//    freopen("in.txt", "r", stdin);
//    freopen("out.txt", "w", stdout);
 
    int n;
 
    scanf ("%d", &n);
 
    memset(inichain, -1, sizeof (inichain));
 
    int raiz = 0;
 
    for (int a=1;a<=n;a++) {
        scanf ("%d", &pai[a]);
        if (pai[a] == -1)
            raiz = a;
        else
            adj[pai[a]].pb(a);
    }
 
    memset(ult, -1, sizeof (ult));
    degs = 1;
 
    go(raiz);
    HDL(raiz);
    upd(1, 1, degs - 1, 1, degs-1, UMZA);
 
    int m;
    scanf ("%d", &m);
 
    for (int a = 0; a < m; a++) {
        int res = 0;
        for_tests(t, tt) {
            int v;
 
            scanf ("%d", &v);
 
            while(v != -1) {
                int cab = s[inichain[chainno[v]]];
 
                if (ult[cab] < a)
                    hmax[cab] = h[cab];
 
                ult[cab] = a;
                res += max(0, h[v] - hmax[cab] + 1);
                hmax[cab] = max(hmax[cab], h[v] + 1);
                v = pai[cab];
            }
        }
 
        printf ("%d\n", res);
    }
}
