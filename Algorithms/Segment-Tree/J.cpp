#include <iostream>
#include <cstring>
 
/**
 * HomeWork.Algoritms.lab4
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */
 
using namespace std;
 
int const MAX_INT = 2000000000;
int k;
int n;
int m;
int minimum;
int ind;
 
struct Node {
    int value;
    int right;
    int left;
};
 
Node *input;
 
void findAns(int i, int left, int right) {
    if (i * 2 + 2 >= 2 * k - 1) {
        if (input[i].left >= left && input[i].right <= right) {
            if (minimum >= input[i].value) {
                minimum = input[i].value;
                ind = i;
            }
        }
        return;
    }
 
    if (input[i].right < left || input[i].left > right) {
        return;
    }
 
    if (input[i].left >= left && input[i].right <= right) {
        if (minimum >= input[i].value) {
            minimum = input[i].value;
            ind = i;
        }
        return;
    }
 
    if (input[i].value != 0) {
        input[2 * i + 1].value = max(input[i].value, input[2 * i + 1].value);
        input[2 * i + 2].value = max(input[i].value, input[2 * i + 2].value);
    }
 
    findAns(i * 2 + 1, left, right);
    findAns(i * 2 + 2, left, right);
}
 
void newQuer(int i , int left, int right, int value) {
    if (i * 2 + 2 >= 2 * k - 1) {
        if (input[i].left >= left && input[i].right <= right) {
            input[i].value = max(input[i].value, value);
        }
        return;
    }
 
    if (input[i].right < left || input[i].left > right) {
        return;
    }
 
    if (input[i].left >= left && input[i].right <= right) {
        input[i].value = max(input[i].value, value);
        return;
    }
 
    if (input[i].value > value) {
        return;
    }
 
    if (input[i].value != 0) {
        input[2 * i + 1].value = max(input[i].value, input[2 * i + 1].value);
        input[2 * i + 2].value = max(input[i].value, input[2 * i + 2].value);
        input[i].value = 0;
    }
 
    newQuer(i * 2 + 1, left, right, value);
    newQuer(i * 2 + 2, left, right, value);
 
    input[i].value = max(input[i].value, min(input[2 * i + 1].value, input[2 * i + 2].value));
}
 
int findInd() {
    if (ind > k - 2 && ind < k - 1 + n) {
        return ind;
    }
 
    while (ind * 2 + 2 < 2 * k - 1) {
        input[ind * 2 + 1].value = max(input[ind].value, input[ind * 2 + 1].value);
        input[ind * 2 + 2].value = max(input[ind].value, input[ind * 2 + 2].value);
 
        if (input[ind * 2 + 1].value == input[ind].value) {
            ind = ind * 2 + 1;
            continue;
        }
 
        ind = ind * 2 + 2;
 
    }
 
    return ind;
}
 
int do2power(int n) {
    int k = 1;
    while (k < n) {
        k *= 2;
    }
 
    return k;
}
 
int main() {
 
    ios_base::sync_with_stdio(false);
 
    scanf("%d", &n);
    scanf("%d", &m);
 
    k = do2power(n);
    int size = 2 * k - 1;
    input = new Node[size];
 
    for (int i = 0; i < 2 * k - 1; i++) {
        input[i].value = 0;
        input[i].left = input[i].right = i;
    }
 
    for (int i = k - 1 + n; i < 2 * k - 1; i++) {
        input[i].value = MAX_INT;
    }
 
    for (int i = k - 2; i >= 0; --i) {
        input[i].left = input[i * 2 + 1].left;
        input[i].right = input[i * 2 + 2].right;
    }
 
    for (int i = 0; i < m; i++) {
        char in[7];
        scanf("%s", &in);
 
        if (strcmp("defend", in) == 0) {
            int left;
            scanf("%d", &left);
            left = left + k - 2;
            int right;
            scanf("%d", &right);
            right = right + k - 2;
            int value;
            scanf("%d", &value);
 
            newQuer(0, left, right, value);
 
        } else {
            int left;
            scanf("%d", &left);
            left = left + k - 2;
            int right;
            scanf("%d", &right);
            right = right + k - 2;
 
            minimum = MAX_INT;
            findAns(0, left, right);
 
            printf("%d %d\n", minimum, findInd() - k + 2);
        }
    }
 
    return 0;
}
