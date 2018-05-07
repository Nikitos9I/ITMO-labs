#include <iostream>
#include <vector>
#include <unordered_set>
#include <algorithm>
 
/**
 * HomeWork->Algoritms->lab5
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1->0->0
 */
 
using namespace std;
 
struct Node {
    int x;
    int y;
    int number;
 
    Node* left = nullptr;
    Node* right = nullptr;
    Node* parent = nullptr;
 
    Node(int x, int y, int number) {
        this->x = x;
        this->y = y;
        this->number = number;
    }
};
 
struct Star {
    int x;
    int y;
    int number;
 
    Star(int x, int y, int number) {
        this->x = x;
        this->y = y;
        this->number = number;
    }
};
 
bool comp(Star* x, Star* y) {
    return x->x < y->x;
}
 
Node* root;
Node* lastAdded;
 
const int MAX_VALUE = 300000;
 
Star* myNewArray[MAX_VALUE];
int ans[MAX_VALUE][3];
 
void build(Node* current, Node* lastAdded) {
    if (current->y > lastAdded->y) {
        if (lastAdded->right != nullptr) {
            current->left = lastAdded->right;
            lastAdded->right->parent = current;
        }
 
        lastAdded->right = current;
        current->parent = lastAdded;
    } else {
        if (lastAdded->parent == nullptr) {
            root = current;
            current->left = lastAdded;
            lastAdded->parent = current;
        } else {
            build(current, lastAdded->parent);
        }
    }
}
 
void printTree(Node* t) {
    if (t != nullptr) {
        printTree(t->left);
        int parentKey = t->parent != nullptr? t->parent->number: 0;
        int leftKey = t->left != nullptr? t->left->number: 0;
        int rightKey = t->right != nullptr? t->right->number: 0;
        ans[t->number][0] = parentKey;
        ans[t->number][1] = leftKey;
        ans[t->number][2] = rightKey;
        printTree(t->right);
    }
}
 
int main() {
    ios_base::sync_with_stdio(false);
 
    int n;
    scanf("%d", &n);
    int inputCopyX[n];
 
    for (int i = 0; i < n; i++) {
        int x;
        scanf("%d", &x);
 
        inputCopyX[i] = i + 1;
        int y;
        scanf("%d", &y);
 
        Star* node = new Star(x, y, i + 1);
        myNewArray[i] = node;
    }
 
    sort(myNewArray, myNewArray + n, comp);
 
    lastAdded = new Node(myNewArray[0]->x, myNewArray[0]->y, myNewArray[0]->number);
    root = lastAdded;
 
    for (int i = 1; i < n; i++) {
        Node* current = new Node(myNewArray[i]->x, myNewArray[i]->y, myNewArray[i]->number);
 
        build(current, lastAdded);
 
        lastAdded = current;
    }
 
    printf("YES\n");
    printTree(root);
 
    for (int i = 0; i < n; i++) {
        printf("%d %d %d\n", ans[inputCopyX[i]][0], ans[inputCopyX[i]][1], ans[inputCopyX[i]][2]);
    }
 
    return 0;
}
