import RuleExtract.FindRule;
import RuleExtract.Rule;
import Tree.FPGrowth;

import java.util.ArrayList;
import java.util.Hashtable;

public class Application {
    public static void main(String[] args) {
        FPGrowth fpGrowth =
                new FPGrowth("/home/chuducanh/Downloads/T10I4D100K.txt", 0.01);
        Long startTime = System.currentTimeMillis();
        fpGrowth.execute();
        Hashtable<ArrayList<String>, Integer>
                itemSet =  fpGrowth.getFrequenseItemSet();
        System.out.println("--------------- Frequense item set ---------------");
        System.out.println(itemSet.size());
        for (ArrayList<String> item: itemSet.keySet()) {
            for (String one: item) {
                System.out.print(one + ", ");
            }
            System.out.println(": " + itemSet.get(item));
        }

        System.out.println("--------------- List rule ---------------");
        FindRule findRule = new FindRule(0.01);
        ArrayList<Rule> listRule = findRule.getAssosiationRule(itemSet);
        System.out.println(listRule.size());
        for (Rule rule : listRule) {
            for (String item: rule.first) {
                if (item.equals(rule.first.get(rule.first.size()-1)))
                {
                    System.out.print(item + " ");
                } else
                System.out.print(item + ", ");
            }
            System.out.print("-> ");

            for (String item: rule.second) {
                if (item.equals(rule.second.get(rule.second.size()-1)))
                {
                    System.out.print(item + " ");
                } else
                    System.out.print(item + ", ");
            }
            System.out.println();
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) / 1000);
    }
}
