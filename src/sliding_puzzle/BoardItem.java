package sliding_puzzle;

public class BoardItem {
    String value;

    public BoardItem(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
