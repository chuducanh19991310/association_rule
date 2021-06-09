import RuleExtract.FindRule;
import RuleExtract.Rule;
import Tree.FPGrowth;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

public class Application {
    public static void main(String[] args) throws FileNotFoundException {
//        FPGrowth fpGrowth =
//                new FPGrowth("/home/rikikudo/Code/test/DataSetA.csv", 0.05);
        FPGrowth fpGrowth =
                new FPGrowth("/home/rikikudo/Code/test/data1.txt", 0.02);
        Long startTime = System.currentTimeMillis();
        fpGrowth.execute();
        Hashtable<ArrayList<String>, Integer>
                itemSet =  fpGrowth.getFrequenseItemSet();

        System.out.println("--------------- Frequense item set ---------------");
        System.out.println(itemSet.size());
        PrintWriter outFreqItemSet = new PrintWriter("/home/rikikudo/Code/test/output_item_list.txt");
        for (ArrayList<String> item: itemSet.keySet()) {
            for (String one: item) {
                if (item.get(item.size()-1).equals(one)) {
                    outFreqItemSet.print(one);
                } else
                    outFreqItemSet.print(one + ", ");
            }
            outFreqItemSet.println(": " + itemSet.get(item));
        }

        System.out.println("--------------- List rule ---------------");
        FindRule findRule = new FindRule(0.1);
        ArrayList<Rule> listRule = findRule.getAssosiationRule(itemSet);
        System.out.println(listRule.size());
        PrintWriter outRule = new PrintWriter("/home/rikikudo/Code/test/output_rule.txt");
        for (Rule rule : listRule) {
            for (String item: rule.first) {
                if (item.equals(rule.first.get(rule.first.size()-1)))
                {
                    outRule.print(item + " ");
                } else
                    outRule.print(item + ", ");
            }
            outRule.print("-> ");

            for (String item: rule.second) {
                if (item.equals(rule.second.get(rule.second.size()-1)))
                {
                    outRule.print(item);
                } else
                    outRule.print(item + ", ");
            }
            outRule.println();
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime));
        outFreqItemSet.flush();
        outFreqItemSet.close();
        outRule.flush();
        outRule.close();
    }
}
