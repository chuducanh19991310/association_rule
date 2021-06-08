package Tree;

import java.util.ArrayList;

public class FPTree {
    public boolean root;
    public ArrayList<FPTree> children;
    public FPTree parent;
    public String item;
    public int count;
    public FPTree next;

    public FPTree() {
        root = false;
        count = Integer.MAX_VALUE;
        children = new ArrayList<>();
    }

    public FPTree(String item) {
        this.item = item;
        root = false;
        count = 1;
        children = new ArrayList<>();
    }

    public FPTree(boolean root, ArrayList<FPTree> children, FPTree parent, String item, int count, FPTree next) {
        this.root = root;
        this.children = children;
        this.parent = parent;
        this.item = item;
        this.count = count;
        this.next = next;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public ArrayList<FPTree> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<FPTree> children) {
        this.children = children;
    }

    public FPTree getParent() {
        return parent;
    }

    public void setParent(FPTree parent) {
        this.parent = parent;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public FPTree getNext() {
        return next;
    }

    public void setNext(FPTree next) {
        this.next = next;
    }
}
