package RuleExtract;

import Ultil.RuleUltil;

import java.util.*;

public class FindRule {

    public  Double minConf = 0.1;

    public FindRule() {

    }
    public FindRule(
            Double minConf
    ) {
        this.minConf = minConf;
    }


    public ArrayList<Rule> getAssosiationRule(
            Hashtable<ArrayList<String>, Integer> frequenseItemSet
    ) {
        ArrayList<Rule> listRule = new ArrayList<>();
        for (ArrayList<String> itemSet1: frequenseItemSet.keySet()) {
            for (ArrayList<String> itemSet2: frequenseItemSet.keySet()) {
                if (!RuleUltil.checkItemSetInterval(itemSet1, itemSet2)) {
                    ArrayList<String> combination = (ArrayList<String>) itemSet1.clone();
                    combination.addAll(itemSet2);
                    Collections.sort(combination);
                    if (frequenseItemSet.get(combination) == null) {
                        continue;
                    }
                    Integer itemSetCount = frequenseItemSet.get(itemSet1);
                    Integer supportCount = frequenseItemSet.get(combination);
                    double conf = Double.valueOf(supportCount) / Double.valueOf(itemSetCount);
                    if (conf >= minConf) {
                        listRule.add(new Rule(itemSet1, itemSet2));
                    }
                }
            }
        }
        return  listRule;
    }
}
