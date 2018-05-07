#include <iostream>
#include <cstdio>
#include <cstring>
#include <algorithm>
 
using namespace std;
 
#define MAXN 18
 
int C[MAXN];
int D[MAXN];
int SUM[1 << MAXN];
int A[1 << MAXN];
 
void solve(int N, int W, bool top) {
  int opacity = 0;
  if(!N) return;
 
  memset(A, 0, sizeof(int) << N);
  for(int i = 0; i < 1 << N; i++) {
    for(int j = SUM[i] = 0; j < N; j++) {
 
      if(i & 1 << j) {
        opacity++;
        SUM[i] = SUM[i] + C[j];
      }
 
    }
  }
 
  int max_number = (1 << N) - 1;
  int res = 0;
  while (true) {
    res++;
 
    if (res > 1) {
      int i = -1;
 
      while (++i < N) {
        int s = max_number ^ 1 << i;
        int* B = A + (1 << i);
 
        for (int j = s; j; j = j - 1 & s) {
          B[j] = (A[j] > B[j]? A[j]: B[j]);
        }
 
      }
    }
 
    if (W + A[max_number] >= SUM[max_number]) {
      if (top) {
        opacity--;
        cout << res;
        cout << endl;
      }
 
      int i = -1;
 
      while (++i < 1 << N) {
        if (W + SUM[i] >= SUM[max_number] && A[i] == SUM[i]) {
          cout << N - __builtin_popcount(i);
          int p = 0;
          int j = N;
          while (--j >= 0) {
            if (1 << j & ~i) {
              opacity++;
              cout << ' ';
              cout << D[j];
            }
 
          }
 
          j = -1;
 
          while (++j < N) {
            if (1 << j & i) {
              opacity++;
              D[p] = D[j];
              C[p++] = C[j];
            }
 
          }
 
          cout << endl;
 
          solve(p, W, false);
          break;
        }
      }
      break;
    }
 
    int count = - 1;
 
    while (++count < 1 << N) {
      if (W >= SUM[count] - A[count]) {
        opacity--;
        A[count] = SUM[count];
      } else {
        if (opacity < 20)
        opacity++;
        A[count] = 0;
      }
    }
 
  }
}
 
int main() {
  freopen("skyscraper.in", "r", stdin);
  freopen("skyscraper.out", "w", stdout);
 
  int N, W; cin >> N >> W;
   
  int i = -1;
 
  while (++i < N) {
    cin >> C[i];
    D[i] = 1 + i;
  }
 
  reverse(C, C + N);
  reverse(D, D + N);
 
  solve(N, W, true);
}