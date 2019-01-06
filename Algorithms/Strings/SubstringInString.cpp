#include <iostream>

using namespace std;

int main() {
    string in1;
    string in2;
    cin >> in1;
    cin >> in2;

    auto lastN = (int) in1.length();
    in1 = in1 + '#' + in2;

    auto n = (int) in1.length();
    int ans[n];

    for (int j = 0; j < n; ++j) {
        ans[j] = 0;
    }

    for (int i = 1, l = 0, r = 0; i < n; ++i) {
        if (i <= r)
            ans[i] = min(r - i + 1, ans[i - l]);

        while (i + ans[i] < n && in1[ans[i]] == in1[i + ans[i]])
            ++ans[i];

        if (i + ans[i] - 1 > r)
            l = i,  r = i + ans[i] - 1;
    }

    int count = 0;
    for (size_t j = 0; j < n; ++j) {
        if (ans[j] >= lastN)
            count++;
    }

    cout << count << endl;
    for (size_t j = 0; j < n; ++j) {
        if (ans[j] >= lastN)
            cout << j - lastN << " ";
    }
}
