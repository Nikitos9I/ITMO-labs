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


    for (size_t j = 1; j < n; ++j) {
        cout << ans[j] << " ";
    }
}
