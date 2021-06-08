package Tree;

import java.util.Comparator;

public class CandidateItemComparator implements Comparator<CandidateItem> {

    @Override
    public int compare(CandidateItem h1, CandidateItem h2) {
        if (h1.supportCount > h2.supportCount) {
            return  -1;
        }
        if (h1.supportCount < h2.supportCount)  {
            return  1;
        }
        return  0;
    }
}
