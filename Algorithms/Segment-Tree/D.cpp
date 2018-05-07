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
 
int const MIN_VALUE = -2147483645;
int const MAX_VALUE = 2147483645;
int k;
int size;
 
struct Node {
    int left;
    int right;
    int leftBorder;
    int rightBorder;
    int color;
    int children;
    int number;
};
 
Node *input;
 
int sum(int a, int b) {
    if (a != MIN_VALUE && b != MIN_VALUE) {
        return a + b;
    } else {
        if (a != MIN_VALUE) {
            return a;
        } else if (b != MIN_VALUE) {
            return b;
        }
    }
 
    return MIN_VALUE;
}
 
void newQuer(int i, int left, int right, int value) {
    if (i * 2 + 2 >= 2 * k - 1) {
        if (input[i].left >= left && input[i].right <= right) {
            input[i].color = value * input[i].children;
            if (value == 1) {
                input[i].number = 1;
                input[i].leftBorder = input[i].left;
                input[i].rightBorder = input[i].right;
            } else {
                input[i].number = 0;
                input[i].leftBorder = input[i].rightBorder = 0;
            }
        }
        return;
    }
 
    if (input[i].right < left || input[i].left > right) {
        return;
    }
 
    if (input[i].left >= left && input[i].right <= right) {
        input[i].color = value * input[i].children;
        if (value == 1) {
            input[i].number = 1;
            input[i].leftBorder = input[i].left;
            input[i].rightBorder = input[i].right;
        } else {
            input[i].number = 0;
            input[i].leftBorder = input[i].rightBorder = 0;
        }
 
        return;
    }
 
    if (input[i].color == input[i].children) {
        input[2 * i + 1].color = input[2 * i + 1].children;
        input[2 * i + 2].color = input[2 * i + 2].children;
        input[2 * i + 1].number = input[i].number;
        input[2 * i + 2].number = input[i].number;
        input[2 * i + 1].leftBorder = input[2 * i + 1].left;
        input[2 * i + 1].rightBorder = input[2 * i + 1].right;
        input[2 * i + 2].leftBorder = input[2 * i + 2].left;
        input[2 * i + 2].rightBorder = input[2 * i + 2].right;
        input[i].color = MIN_VALUE;
    }
 
    if (input[i].color == 0) {
        input[2 * i + 1].color = 0;
        input[2 * i + 2].color = 0;
        input[2 * i + 1].number = 0;
        input[2 * i + 2].number = 0;
        input[2 * i + 1].leftBorder = 0;
        input[2 * i + 1].rightBorder = 0;
        input[2 * i + 2].leftBorder = 0;
        input[2 * i + 2].rightBorder = 0;
        input[i].color = MIN_VALUE;
    }
 
    newQuer(i * 2 + 1, left, right, value);
    newQuer(i * 2 + 2, left, right, value);
 
    input[i].color = sum(input[2 * i + 1].color, input[2 * i + 2].color);
    input[i].leftBorder = input[2 * i + 1].leftBorder != 0? input[2 * i + 1].leftBorder: input[2 * i + 2].leftBorder;
    input[i].rightBorder = input[2 * i + 2].rightBorder != 0? input[2 * i + 2].rightBorder: input[2 * i + 1].rightBorder;
    if (input[2 * i + 2].leftBorder - input[2 * i + 1].rightBorder == 1) {
        input[i].number = sum(input[2 * i + 1].number, input[2 * i + 2].number) - 1;
    } else {
        input[i].number = sum(input[2 * i + 1].number, input[2 * i + 2].number);
    }
}
 
int do2power(int n) {
    int k = 1;
    while (k < n) {
        k *= 2;
    }
 
    return k;
}
 
int main() {
 
    freopen("painter.in", "r", stdin);
    freopen("painter.out", "w", stdout);
    ios_base::sync_with_stdio(false);
 
    int n;
    cin >> n;
 
    char in[n];
    int left[n];
    int right[n];
    int minimum = MAX_VALUE;
    int maximum = MIN_VALUE;
    for (int i = 0; i < n; i++) {
        char symb;
        cin >> symb;
        in[i] = symb;
        int nowLeft;
        cin >> nowLeft;
        left[i] = nowLeft;
        minimum = left[i] < minimum? left[i] : minimum;
        int nowRight;
        cin >> nowRight;
        right[i] = nowRight;
//        cout << nowLeft << " " << nowRight << " l/r" << endl;
    }
 
    for (int i = 0; i < n; i++) {
        if (left[i] < 0) {
            left[i] -= min(0, minimum);
        } else {
            left[i] -= min(-1, minimum);
        }
        right[i] += left[i];
        maximum = maximum < right[i]? right[i]: maximum;
        right[i] -= 1;
    }
 
    size = maximum;
    k = do2power(size);
 
    input = new Node[2*k - 1];
 
    for (int i = 0; i < 2 * k - 1; i++) {
        input[i].color = MIN_VALUE;
        input[i].left = input[i].right = i;
        input[i].leftBorder = input[i].rightBorder = input[i].number = 0;
        input[i].children = 1;
    }
 
    for (int i = k - 2; i >= 0; --i) {
        input[i].left = input[i * 2 + 1].left;
        input[i].right = input[i * 2 + 2].right;
        input[i].children = input[2 * i + 1].children + input[2 * i + 2].children;
    }
 
    for (int i = 0; i < n; i++) {
        if (in[i] == 'B') {
            newQuer(0, left[i] + k - 1, right[i] + k - 1, 1);
            printf("%d %d\n", input[0].number, input[0].color);
        } else {
            newQuer(0, left[i] + k - 1, right[i] + k - 1, 0);
            printf("%d %d\n", input[0].number, input[0].color);
        }
 
    }
 
    fclose(stdin);
    fclose(stdout);
 
    return 0;
}
