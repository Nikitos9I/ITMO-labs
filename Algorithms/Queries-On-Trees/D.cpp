#include <iostream>
#include <vector>
#include <unordered_map>
 
#define un unsigned int
 
/**
 * HomeWork.Algoritms.lab6
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */
 
using namespace std;
 
const un N = 50001;
 
vector< vector<un> > edges(N, vector<un>());
vector<un> depth(N);
vector<un> parent(N);
vector<un> path(N);
vector<bool> isHeavy(N, false);
vector<un> weight(N, 0);
vector<un> height(N);
 
struct SegmentTree {
    vector<un> tree_;
    unordered_map<un, un> links_;
    un size_;
    un top_segment_tree;
 
    SegmentTree(vector<un> v) {
        size_ = v.size();
        un deg(1);
 
        for (size_t i = 0; i < size_; ++i) {
            links_.insert(make_pair(v[i], i));
        }
 
        top_segment_tree = v[size_ - 1];
 
        while (size_ > 1) {
            size_ >>= 1;
            ++deg;
        }
 
        size_ <<= deg;
        tree_.resize(size_ + size_ - 1, 0);
 
        for (size_t i = 0; i < v.size(); ++i) {
            tree_[size_ - 1 + i] = height[v[i]];
        }
 
        for (size_t i = size_ - 1; i > 0; --i) {
            tree_[i - 1] = max(tree_[(i - 1) * 2 + 1], tree_[(i - 1) * 2 + 2]);
        }
    }
 
    un query(un l, un r) {
        return queryImpl(0, 0, size_, (*links_.find(l)).second, (*links_.find(r)).second);
    }
 
    un queryImpl(un cur, un curL, un curR, un l, un r) {
        if (curL >= r || curR <= l) {
            return 0;
        }
 
        if (curL >= l && curR <= r) {
            return tree_[cur];
        }
 
        return max(queryImpl(cur * 2 + 1, curL, curL + (curR - curL) / 2, l, r), queryImpl(cur * 2 + 2, curL + (curR - curL) / 2, curR, l, r));
    }
 
    un top() {
        return top_segment_tree;
    }
 
    void change(un v, un h) {
        un cur = size_ - 1 + (*links_.find(v)).second;
        tree_[cur] = h;
        while (cur != 0) {
            --cur;
            cur >>= 1;
            tree_[cur] = max(tree_[cur * 2 + 1], tree_[cur * 2 + 2]);
        }
    }
};
 
vector<SegmentTree> st;
 
un dfs(un v, un p, un _depth);
void make();
un lca(un u, un v);
un query(un u, un v);
void change(un v, un h);
 
un lca(un u, un v) {
    if (path[u] == path[v]) {
        if (depth[u] < depth[v]) {
            return u;
        } else {
            return v;
        }
    }
 
    return depth[st[path[u]].top()] > depth[st[path[v]].top()]? lca(st[path[u]].top(), v) : lca(u, st[path[v]].top());
}
 
void make() {
    dfs(0, 0, 0);
    for (size_t i = 1; i < edges.size(); ++i) {
        if (edges[i].size() == 1) {
            un cur = i;
            vector<un> v;
 
            while (isHeavy[cur]) {
                v.push_back(cur);
                path[cur] = st.size();
                cur = parent[cur];
            }
 
            path[cur] = st.size();
            v.push_back(cur);
 
            if (cur != 0) {
                cur = parent[cur];
                v.push_back(cur);
            }
 
            st.push_back(SegmentTree(move(v)));
        }
    }
 
    path[0] = path[1];
}
 
un dfs(un v, un p, un _depth) {
    depth[v] = _depth;
    parent[v] = p;
    un maxWeight(0), heavy, weightSub;
 
    for (size_t i = 0; i < edges[v].size(); ++i) {
        if (edges[v][i] != p) {
            weightSub = dfs(edges[v][i], v, _depth + 1);
            weight[v] += weightSub;
            if (maxWeight < weightSub) {
                maxWeight = weightSub;
                heavy = edges[v][i];
            }
        }
    }
 
    if (edges[v].size() > 1 || _depth == 0) {
        isHeavy[heavy] = true;
    }
 
    return ++weight[v];
}
 
void change(un v, un h) {
    st[path[v]].change(v, h);
}
 
un query(un u, un v) {
    un res(0);
    un myLCA = lca(u, v);
    un myTOP = st[path[u]].top();
 
    if (path[u] != path[v]) {
        while (path[u] != path[myLCA]) {
            res = max(res, st[path[u]].query(u, myTOP));
            u = myTOP;
            myTOP = st[path[u]].top();
        }
    }
 
    if (path[u] != path[v]) {
        myTOP = st[path[v]].top();
        while (path[v] != path[myLCA]) {
            res = max(res, st[path[v]].query(v, myTOP));
            v = myTOP;
            myTOP = st[path[v]].top();
        }
    }
 
    res = max(res, st[path[myLCA]].query(depth[u] > depth[v] ? u : v, depth[u] < depth[v] ? u : v));
    res = max(height[myLCA], res);
    return res;
}
 
int main() {
    freopen("mail.in", "r", stdin);
    freopen("mail.out", "w", stdout);
 
    size_t n, m;
    cin >> n;
 
    for (size_t i = 1; i <= n; ++i) {
        cin >> height[i];
    }
 
    height[0] = 0;
    un u, v;
    char type;
 
    for (size_t i = 1; i < n; ++i) {
        cin >> u >> v;
        edges[u].push_back(v);
        edges[v].push_back(u);
    }
 
    edges[0].push_back(1);
    edges[1].push_back(0);
    make();
    cin >> m;
 
    for (size_t i = 0; i < m; ++i) {
        cin >> type >> u >> v;
        if (type == '?') {
            cout << query(u, v) << endl;
        } else {
            change(u, v);
            height[u] = v;
        }
    }
 
    fclose(stdin);
    fclose(stdout);
    return 0;
}
