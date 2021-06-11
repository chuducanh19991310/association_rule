package Tree;

import java.io.File;
import java.util.*;

import Ultil.TreeUltil;

public class FPGrowth {

    public File target;
    public Double minSupport;
    public Hashtable<ArrayList<String>, Integer> FreqenseItemSet;
    public int numberOfGlobalTransactions;

    void addToItemCount(
            Hashtable<String, Integer> hashtable,
            String value
    ) {
        if (Objects.isNull(hashtable.get(value))) {
            hashtable.put(value, 0);
        }
        hashtable.put(value,
                hashtable.get(value) + 1);
    }

    public FPGrowth(
            String fileName,
            Double minSupport) {
        target = new File(fileName);
        this.minSupport = minSupport;
        FreqenseItemSet = new Hashtable<>();
    }

    public void readData(
            ArrayList<ArrayList<String>> transactions
    ) {
        try {
            Scanner scanner = new Scanner(target);
            while (scanner.hasNextLine()) {
                ArrayList<String> transaction = new ArrayList<>();
                String line = scanner.nextLine();
//                StringTokenizer rawItemToken = new StringTokenizer(line, ",");
                StringTokenizer rawItemToken = new StringTokenizer(line, ",");
                while (rawItemToken.hasMoreTokens()) {
                    String item = rawItemToken.nextToken();
                    transaction.add(item);
                }
                transactions.add(transaction);
                System.out.println("Read line: " + (transactions.size()+1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void growth(
            FPTree fpTree,
            ArrayList<String> suffix,
            ArrayList<CandidateItem> candidateTable,
            Integer minCount
    )   {
        System.out.println("Scan " + minCount);
        if (TreeUltil.isSinglePath(fpTree)) {
            ArrayList<String> items = new ArrayList<>();
            Hashtable<String, Integer> numberOfItem = new Hashtable<>();
            while (fpTree != null) {
                if (fpTree.item != null) {
                    items.add(fpTree.item);
                }
                if (fpTree.getChildren().size() != 0) {
                    fpTree = fpTree.getChildren().get(0);
                } else {
                    fpTree = null;
                }
            }
            for (CandidateItem v: candidateTable) {
                if (v.item != null) {
                    numberOfItem.put(v.item, v.supportCount);
                }
            }
            ArrayList<ArrayList<String>> result
                    = generateCombinations(items);
            for (ArrayList<String> itemSet: result) {
                int count = minCount;
                for (String item: itemSet) {
                    count = Math.min(count, numberOfItem.get(item));
                }
                itemSet.addAll(suffix);
                Collections.sort(itemSet);
                FreqenseItemSet.put(itemSet, count);
            }
        } else {
            for (int i = candidateTable.size()-1; i >= 0; i --) {
                ArrayList<String>  combination = new ArrayList<>();
                combination.add(candidateTable.get(i).item);
                combination.addAll(suffix);
                combination.remove(null);
                int count = Math.min(candidateTable.get(i).getSupportCount(), minCount);
                Collections.sort(combination);
                FreqenseItemSet.put(combination, count);
                ArrayList<ArrayList<String>>
                        newTransactions = new ArrayList<>();
                FPTree tmpNode = candidateTable.get(i).getFirstNode();
                while (tmpNode != null) {
                    FPTree tmpNode2 = tmpNode;
                    ArrayList<String> newTransaction = new ArrayList<>();
                    while (tmpNode2.item != null) {
                        if (tmpNode2 != tmpNode) {
                            newTransaction.add(tmpNode2.item);
                        }
                        tmpNode2 = tmpNode2.getParent();
                    }
                    for (int j = 1; j <= tmpNode.getCount(); j++){
                        newTransactions.add(newTransaction);
                    }
                    tmpNode = tmpNode.next;
                }
                ArrayList<CandidateItem> newHeaderTable = this.getCandidateItem(newTransactions);
                FPTree newTree = TreeUltil.buildTree(newTransactions, newHeaderTable);
                if (newTree.getChildren().size() > 0) {
                    growth(newTree, combination, newHeaderTable, count);
                }
            }
        }
    }

    private ArrayList<ArrayList<String>> generateCombinations(ArrayList<String> items) {
        ArrayList<ArrayList<String>> combinations = new ArrayList<>();
        if(items.size() != 0){
            String s = items.get(0);
            if(items.size() > 1){
                items.remove(0);
                ArrayList<ArrayList<String>> combinationsSub = generateCombinations(items);
                combinations.addAll(combinationsSub);
                for(ArrayList<String> a: combinationsSub){
                    for(int i = 0; i < a.size(); i++){
                        ArrayList<String> combination = new ArrayList<String>();
                        for(int j = 0; j <= i; j++){
                            combination.add(a.get(j));
                        }
                        combination.add(s);
                        combinations.add(combination);
                    }
                }
            }
            ArrayList<String> combination = new ArrayList<>();
            combination.add(s);
            combinations.add(combination);
        }
        return combinations;
    }

    public ArrayList<CandidateItem> getCandidateItem(
        ArrayList<ArrayList<String>> newTransactions
     ) {
         Hashtable<String, Integer> itemCountsInternal = new Hashtable<>();
         ArrayList<CandidateItem> candidateItemList = new ArrayList<>();
         for (ArrayList<String> transaction: newTransactions) {
             for (String item: transaction) {
                 addToItemCount(itemCountsInternal, item);
             }
         }
         for (String item : itemCountsInternal.keySet()) {
             if (itemCountsInternal.get(item) >= numberOfGlobalTransactions*minSupport) {
                CandidateItem node = new CandidateItem(
                        item,
                        itemCountsInternal.get(item)
                );
                 candidateItemList.add(node);
             }
         }
         Collections.sort(candidateItemList,
                 new CandidateItemComparator());
         return candidateItemList;
     }


    public void execute(){
        ArrayList<ArrayList<String>>
                transactions = new ArrayList<>();
        readData(transactions);
        numberOfGlobalTransactions = transactions.size();
        ArrayList<CandidateItem> headerTable
                = getCandidateItem(transactions);
        FPTree fpTree
                = TreeUltil.buildTree(transactions, headerTable);
        ArrayList<String> nullList = new ArrayList<>();
        growth(fpTree, nullList, headerTable, Integer.MAX_VALUE);
    }

    public Hashtable<ArrayList<String>, Integer> getFrequenseItemSet() {
        return this.FreqenseItemSet;
    }
}
