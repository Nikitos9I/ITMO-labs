#include <iostream>
#include <vector>

using namespace std;

long long getHash(const long long h[], int L, int R) {
    long long result = h[R];
    if (L > 0) result -= h[L - 1];
    return result;
}

int main() {
    std::string in;
    cin >> in;

    int m;
    cin >> m;

    const int p = 31;
    vector<long long> pow ((int) in.length());
    pow[0] = 1;

    for (size_t i = 1; i < pow.size(); ++i)
        pow[i] = pow[i - 1] * p;

    long long h[(int) in.length()];
    h[0] = in[0];
    for (int i = 1; i < in.length(); i++) {
        h[i] = h[i - 1] + pow[i] * in[i];
    }


    for (int i = 0; i < m; ++i) {
        int a, b, c, d;
        cin >> a >> b >> c >> d;
        a -= 1;
        b -= 1;
        c -= 1;
        d -= 1;

        long long h1 = h[a + b - 1];
        if (a)  h1 -= h[a - 1];
        long long h2 = h[c + d - 1];
        if (c)  h2 -= h[c - 1];

        if (getHash(h, a, b) * pow[c] == getHash(h, c, d) * pow[a])
            cout << "Yes" << endl;
        else
            cout << "No" << endl;
    }
}
