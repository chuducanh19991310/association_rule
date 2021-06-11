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
        Double minSupport = 0.02;
        Double minConf = 0.01;
        FPGrowth fpGrowth =
                new FPGrowth("/home/chuducanh/association_rule/DataSetA.txt", minSupport);
        Long startTime = System.currentTimeMillis();
        fpGrowth.execute();
        Hashtable<ArrayList<String>, Integer>
                itemSet =  fpGrowth.getFrequenseItemSet();

        System.out.println("--------------- Frequense item set ---------------");
        String outputFolder = "/home/chuducanh/association_rule";
        System.out.println(itemSet.size());
        PrintWriter outFreqItemSet = new PrintWriter( outputFolder + "/output_item_list.txt");
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
        FindRule findRule = new FindRule(minConf);
        ArrayList<Rule> listRule = findRule.getAssosiationRule(itemSet);
        System.out.println(listRule.size());
        PrintWriter outRule = new PrintWriter(outputFolder + "/output_rule.txt");
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
        System.out.println("Time: " + (endTime - startTime)/1000.0);
        outFreqItemSet.flush();
        outFreqItemSet.close();
        outRule.flush();
        outRule.close();
    }
}
