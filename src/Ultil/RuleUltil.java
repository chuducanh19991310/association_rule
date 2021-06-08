package Ultil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RuleUltil {
    public static boolean checkItemSetInterval(
            ArrayList<String> itemSet1,
            ArrayList<String> itemSet2
    ) {
        Set<String> set1 = new HashSet<>(itemSet1);
        Set<String> set2 = new HashSet<>(itemSet2);
        set2.retainAll(set1);
        if (set2.size() != 0) {
            return  true;
        }
        return  false;
    }
}
