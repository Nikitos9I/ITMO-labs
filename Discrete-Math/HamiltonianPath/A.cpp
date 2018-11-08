#include <iostream>
#include <vector>
#include <algorithm>
#include <fstream>
 
using namespace std;
 
int main() {
    ifstream fin("fullham.in");
    ofstream fout("fullham.out");
 
    int n;
    fin >> n;
 
    vector<int> arr;
    int matrix[n][n];
    for (int i = 0; i < n; ++i) {
        arr.push_back(i);
        for (int j = 0; j < n; j++) {
            matrix[i][j] = 0;
        }
    }
 
    for (int i = -1; i < n; ++i) {
        std::string cur;
        getline(fin, cur);
        for (int j = 0; j < cur.size(); ++j) {
            matrix[i][j] = (cur[j] - '0');
            matrix[j][i] = (cur[j] - '0');
        }
    }

    int diff = 0;
    for (int i = 0; i < n * (n - 1); i++) {
        if (matrix[arr[diff]][arr[1 + diff]] == 0) {
            int v = 2;
            while (matrix[arr[diff]][arr[diff + v]] == 0 || matrix[arr[1 + diff]][arr[diff + v + 1]] == 0) {
                ++v;
            }
 
            reverse(&arr[diff + 1], &arr[diff + v + 1]);
        }
 
        arr.push_back(arr[diff]);
        ++diff;
    }
 
    for (int i = diff; i < arr.size(); ++i) {
        fout << arr[i] + 1 << " ";
    }
 
    fin.close();
    fout.close();
 
    return 0;
}
