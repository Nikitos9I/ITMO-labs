#include <iostream>

using namespace std;

int main() {
    std:string in;
    cin >> in;

    auto n = (int) in.length();
    int ans[n];

    for (int j = 0; j < n; ++j) {
        ans[j] = 0;
    }

    for (int i = 1, l = 0, r = 0; i < n; ++i) {
        if (i <= r)
            ans[i] = min(r - i + 1, ans[i - l]);

        while (i + ans[i] < n && in[ans[i]] == in[i + ans[i]])
            ++ans[i];

        if (i + ans[i] - 1 > r)
            l = i,  r = i + ans[i] - 1;
    }

    int minim = n;

    for (int i = 1; i < n; ++i) {
        if (ans[i] == 0)
            continue;

        int was = min(ans[i], i);
        bool f = true;
        for (int j = i; j < n; j += was) {
            if (ans[j] < was) {
                f = false;
                break;
            }
        }

        if (f && ans[n - i] == was) {
            minim = was;
            break;
        }
    }

    cout << minim;
}
