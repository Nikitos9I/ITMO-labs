#include <iostream>
#include <vector>
#include <set>
#include <bits/stdc++.h>
using namespace std;

int const p = 31;
long long pows[2000000];
vector<string> s;

vector<int> check(int n) {
    vector<int> res;
    long long h[15000];
    h[0] = (s[0][0] - 'a' + 1) * p;
    for (int i = 1; i < n; i++) {
        h[i] = (h[i - 1] + s[0][i] - 'a' + 1) * p;
    }
    for (int i = n; i < s[0].length(); i++) {
        h[i] = (h[i - 1] - (s[0][i - n] - 'a' + 1) * pows[n] + (s[0][i] - 'a' + 1)) * p;
    }

    set<long long> se;
    for (int i = n - 1; i < s[0].length(); i++) {
        se.insert(h[i]);
    }
    for (int j = 1; j < s.size(); j++) {
        string const &t = s[j];
        set<long long> set2;
        h[0] = (t[0] - 'a' + 1) * p;
        for (int i = 1; i < n; i++) {
            h[i] = (h[i - 1] + t[i] - 'a' + 1) * p;
        }
        for (int i = n; i < t.length(); i++) {
            h[i] = (h[i - 1] - (t[i - n] - 'a' + 1) * pows[n] + (t[i] - 'a' + 1)) * p;
        }

        for (int i = n - 1; i < t.length(); i++) {
            if (se.count(h[i])) {
                set2.insert(h[i]);
                se.erase(se.find(h[i]));
                if (j == s.size() - 1) {
                    res.push_back(i);
                }
            }
        }
        se = set2;
    }

    return res;
}

int main() {
    ios::sync_with_stdio(false);
    int k;
    cin >> k;
    int n = 0;
    int index = 0;
    for (int i = 0; i < k; i++) {
        string t;
        cin >> t;
        s.push_back(t);
        if (n == 0 || n > t.length()) {
            n = t.length();
            index = i;
        }
    }
    if (k == 1) {
        cout << s[0];
        return 0;
    }
    if (index != 0) {
        swap(s[index], s[0]);
    }

    pows[0] = 1;
    for (int i = 1; i < 15000; i++)
        pows[i] = pows[i - 1] * p;

    int l = 0;
    int r = n + 1;
    vector<int> answ;
    while (r - l > 1) {
        int mid = (r + l) / 2;
        vector<int> ans = check(mid);
        if (ans.size() > 0) {
            answ = ans;
            l = mid;
        } else {
            r = mid;
        }
    }

    if (answ.size() == 0) {
        cout << "";
    } else {
        vector<string> ans;
        for (int i = 0; i < answ.size(); i++) {
            ans.push_back(s[s.size() - 1].substr(answ[i] - l + 1, l));
        }
        sort(ans.begin(), ans.end());
        cout << ans[0];
    }
    return 0;
}
