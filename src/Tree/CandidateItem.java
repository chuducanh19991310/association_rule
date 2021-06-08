package Tree;

public class CandidateItem {
    public String item;
    public int supportCount;
    public FPTree firstNode;

    public CandidateItem(
            String item,
            int supportCount
    ) {
        this.item = item;
        this.supportCount = supportCount;
    }

    public int getSupportCount() {
        return supportCount;
    }

    public void setSupportCount(int supportCount) {
        this.supportCount = supportCount;
    }

    public FPTree getFirstNode() {
        return firstNode;
    }

    public void setFirstNode(FPTree firstNode) {
        this.firstNode = firstNode;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
