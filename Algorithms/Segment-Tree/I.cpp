#include <iostream>
#include <cstring>
#include <vector>
#include <algorithm>
 
/**
 * HomeWork.Algoritms.lab4
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */
 
using namespace std;
 
struct Node {
    int sum;
    int max;
    int left;
    int right;
    int value;
    bool actual;
    Node *leftChild = nullptr;
    Node *rightChild = nullptr;
 
    Node(int sum, int max, bool actual, int value, int left, int right) {
        this->sum = sum;
        this->max = max;
        this->actual = actual;
        this->value = value;
        this->left = left;
        this->right = right;
    }
 
};
 
Node *tree;
 
void push(Node *current) {
//        System.out.println("++");
 
    int middle = (current->left + current->right) / 2;
 
    if (current->leftChild == nullptr) {
        current->leftChild = new Node(current->value * (middle - current->left + 1),
                                     current->value * (middle - current->left + 1),
                                     true, current->value, current->left, middle);
    } else {
        if (current->actual) {
            current->leftChild->sum = current->leftChild->max = current->value * (current->leftChild->right - current->leftChild->left + 1);
            current->leftChild->value = current->value;
            current->leftChild->actual = true;
        }
    }
 
    if (current->rightChild == nullptr) {
        current->rightChild = new Node(current->value * (current->right - middle),
                                      current->value * (current->right - middle),
                                      true, current->value, middle + 1, current->right);
        current->actual = false;
    } else {
        if (current->actual) {
            current->rightChild->sum = current->rightChild->max = current->value * (current->rightChild->right - current->rightChild->left + 1);
            current->rightChild->value = current->value;
            current->rightChild->actual = true;
            current->actual = false;
        }
    }
}
 
int get(Node *current, int value) {
    if (current->left == current->right) {
        return current->left - 1;
    }
 
    push(current);
 
    if (current->leftChild->max > value) {
        return get(current->leftChild, value);
    } else {
        return get(current->rightChild, value - current->leftChild->sum);
    }
}
 
void quer(Node *current, int value, int left, int right) {
    if (current->right < left || current->left > right) {
        return;
    }
 
    if (current->left >= left && current->right <= right) {
        current->max = current->sum = value * (current->right - current->left + 1);
        current->actual = true;
        current->value = value;
        return;
    }
 
    push(current);
 
    quer(current->leftChild, value, left, right);
    quer(current->rightChild, value, left, right);
 
    current->sum = current->leftChild->sum + current->rightChild->sum;
    current->max = max(current->leftChild->max, current->rightChild->max + current->leftChild->sum);
}
 
int main() {
    ios_base::sync_with_stdio(false);
 
    int n;
    cin >> n;
 
    tree = new Node(0, 0, false, 0, 1, n);
 
    while (true) {
        string in;
        cin >> in;
        if (in == ("Q")) {
            int height;
            cin >> height;
            if (height >= tree->max) {
                printf("%d\n", n);
            } else {
                int ans = get(tree, height);
                printf("%d\n", ans);
            }
        } else if (in == "I") {
            int left;
            cin >> left;
            int right;
            cin >> right;
            int value;
            cin >> value;
            quer(tree, value, left, right);
        } else {
            break;
        }
    }
 
    return 0;
}
