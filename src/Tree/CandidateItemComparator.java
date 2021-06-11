package Tree;

import java.util.Comparator;

public class CandidateItemComparator implements Comparator<CandidateItem> {

    @Override
    public int compare(CandidateItem h1, CandidateItem h2) {
        /*
                 @Override
            public int compare(CandidateItem h1, CandidateItem h2) {
                return h1.item.compareTo(h2.item);
            }
         */
        // Sap xep theo thu tu tu dien se lam chuong trinh chay cham
        // Sao xep theo support count chay nhanh hon.
        if (h1.supportCount > h2.supportCount) {
            return  -1;
        }
        if (h1.supportCount < h2.supportCount)  {
            return  1;
        }
        return  0;
    }
}
