package sliding_puzzle.data;

public enum Consts {

    InputSeparator(","), BLANK("0");

    public String value;

    Consts(String value) {
        this.value = value;
    }

    public enum Heuristics {
        MANHATTAN_HEURISTIC, MISPLACEMENT_HEURISTIC
    }

    public enum Moves {
        UP("U",4), DOWN("D",3), LEFT("L",2), RIGHT("R",1);

        public String value;
        public int weight;

        Moves(String value,int weight) {
            this.value = value;
            this.weight = weight;
        }
    }

}
