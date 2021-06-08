package Ultil;

import Tree.FPTree;
import Tree.CandidateItem;

import java.util.ArrayList;
import java.util.Objects;

public class TreeUltil {

    public static boolean isSinglePath(FPTree fpTree) {
        boolean isSinglePath = true;
        if(fpTree.children.size() > 1){
            isSinglePath = false;
            return isSinglePath;
        }else{
            for(FPTree child: fpTree.children){
                if(isSinglePath){
                    isSinglePath = isSinglePath(child);
                }else{
                    break;
                }
            }
        }
        return isSinglePath;
    }

    public static FPTree buildTree(
            ArrayList<ArrayList<String>> transactions,
            ArrayList<CandidateItem> headerTable
    ) {
        FPTree fpTree = new FPTree();
        fpTree.root = true;
        for (ArrayList<String> transaction: transactions) {
            ArrayList<String> filterTransaction = new ArrayList<>();
            // Đã được sắp xếp vì for theo headerNode
            for (CandidateItem h: headerTable) {
                if (transaction.contains(h.item)) {
                    filterTransaction.add(h.item);
                }
            }
            TreeUltil.addToTree(filterTransaction, headerTable, fpTree, 0);
        }
        return fpTree;
    }

    public static void addToTree(
            ArrayList<String> transaction,
            ArrayList<CandidateItem> candidateTable,
            FPTree tree,
            int pos
    ) {
        if (pos >= transaction.size()) {
            return;
        }
        FPTree subTree = null;
        ArrayList<FPTree> children = tree.children;
        String item = transaction.get(pos);
        for (FPTree child: children) {
            if (child.item.equals(item)) {
                subTree = child;
                child.count ++;
                break;
            }
        }
        if (Objects.isNull(subTree)) {
            subTree = new FPTree(item);
            subTree.setParent(tree);
            tree.getChildren().add(subTree);
            for(CandidateItem h: candidateTable) {
                if (h.item.equals(item)) {
                    FPTree tmp = h.firstNode;
                    if (Objects.isNull(tmp)) {
                        h.firstNode = subTree;
                    } else {
                        while (! Objects.isNull(tmp.next)) {
                            tmp = tmp.next;
                        }
                        tmp.next = subTree;
                    }
                }
            }
        }
        addToTree(transaction, candidateTable, subTree, pos+1);
    }
}
