package RuleExtract;

import java.util.ArrayList;

public class Rule {
    public ArrayList<String> first;
    public ArrayList<String> second;

    public Rule(
            ArrayList<String> first,
            ArrayList<String> second
    ) {
        this.first = first;
        this.second = second;
    }
}
