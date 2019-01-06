#include <iostream>

using namespace std;

int main() {
    std:string in;
    cin >> in;

    int n = (int) in.length(), i = 0;
    int ans[n];

    for (int j = 0; j < n; ++j) {
        ans[j] = 0;
    }

    while (++i < n) {
        int j = ans[i - 1];

        while (j > 0 && in[i] != in[j])
            j = ans[j - 1];

        if (in[i] == in[j])
            ++j;

        ans[i] = j;
    }


    for (int cur : ans) {
        cout << cur << " ";
    }
}
