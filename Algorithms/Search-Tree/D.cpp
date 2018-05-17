#include <iostream>
#include <vector>
 
using namespace std;
 
struct node {
    long long y, k, cnt;
    bool f;
    node *p;
    node *l;
    node *r;
    node *nx;
};
 
 
int cnt(node *t) {
    if (t == 0) {
        return 0;
    } else {
        return t->cnt;
    }
}
 
void update(node *t) {
    if (t != 0) {
        if (t->l != 0) {
            t->l->p = t;
        }
        if (t->r != 0) {
            t->r->p = t;
        }
 
        t->p = 0;
 
        t->cnt = 1 + cnt(t->l) + cnt(t->r);
    }
}
 
node *get(node *t) {
    if (t->nx != t) {
        t->nx = get(t->nx);
    }
    return t->nx;
}
 
void out(node *v, vector<long long> &result) {
    if (v == 0) {
        return;
    }
    out(v->l, result);
    result.push_back(v->k);
    out(v->r, result);
}
 
void merge(node *&t, node *l, node *r) {
    if (l == 0) {
        t = r;
        return;
    }
    if (r == 0) {
        t = l;
        return;
    }
    if (l->y > r->y) {
        merge(l->r, l->r, r);
        t = l;
    } else {
        merge(r->l, l, r->l);
        t = r;
    }
    update(t);
}
 
void split(node *t, node *&l, node *&r, long long x, long long add) {
    if (t == 0) {
        l = 0;
        r = 0;
        return;
    }
 
    int kol = add + cnt(t->l);
    if (x <= kol) {
        split(t->l, l, t->l, x, add);
        r = t;
    } else {
        split(t->r, t->r, r, x, add + 1 + cnt(t->l));
        l = t;
    }
    update(t);
}
 
long long find(node *t) {
    long long n = cnt(t->l) + 1;
    node *p;
    while (t->p != 0) {
        p = t->p;
        if (t == p->r) {
            n += cnt(p->l) + 1;
        }
        t = p;
    }
 
    return n;
}
 
long long randomLongLong() {
    long long res = rand();
    for (int i = 0; i < 3; i++) {
        res <<= 16;
        res += rand();
    }
    return res;
}
 
node *generation(long long x) {
    node *t = new node;
    t->k = x;
    t->l = t->r = t->p = 0;
    t->f = 0;
    t->nx = t;
    t->y = randomLongLong();
    t->cnt = 1;
    t->r = 0;
 
    return t;
}
 
node* getLeft(node* t) {
    while (t->l != 0) {
        t = t->l;
    }
 
    return t;
}
 
int main() {
 
    ios_base::sync_with_stdio(false);
 
    cin.tie(0);
    cout.tie(0);
 
    int n, m;
    cin >> n >> m;
 
    node *root = generation(0);
 
    for (int i = 1; i <= m + n + 1; ++i) {
        node *w = generation(0);
        merge(root, w, root);
    }
 
    node *l = 0;
    node *r = 0;
    long long a;
    for (int i = 1; i <= n; ++i) {
        cin >> a;
        split(root, l, root, a - 1, 0);
        split(root, root, r, 1, 0);
 
        node *t = r;
        t = getLeft(t);
 
        if (root->k == 0) {
            root->k = i;
            root->nx = get(t);
            merge(root, l, root);
            merge(root, root, r);
        } else {
            node *nx = get(root);
            merge(root, root, r);
            node *w = generation(i);
            w->nx = get(t);
            merge(l, l, w);
            merge(root, l, root);
 
 
            int b = find(nx);
            split(root, l, root, b - 1, 0);
            split(root, root, r, 1, 0);
 
            t = r;
            t = getLeft(t);
 
            root->nx = get(t);
            merge(root, l, r);
        }
 
    }
 
    vector<long long> result;
    out(root, result);
 
    n = result.size() - 1;
    while (result[n] == 0) {
        --n;
    }
 
    cout << n + 1 << "\n";
    for (int i = 0; i <= n; ++i) {
        cout << result[i] << " ";
    }
    return 0;
}
