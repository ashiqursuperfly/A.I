package sliding_puzzle;

public class Consts {
    public enum Heuristics {
        MANHATTAN, SIMPLE
    }

    public enum BoardConsts {
        BLANK("0"), UP("U"), DOWN("D"), L("L"), R("R");

        public String value;

        BoardConsts(String value) {
            this.value = value;
        }
    }

}
