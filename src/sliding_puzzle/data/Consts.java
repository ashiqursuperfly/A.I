package sliding_puzzle.data;

public enum Consts {

    InputSeparator(","), BLANK("#"), GivenStateAndGoalStateInputSeparator("-->");

    public String value;

    Consts(String value) {
        this.value = value;
    }

    public enum Heuristics {
        MANHATTAN, SIMPLE
    }

    public enum Moves {
        UP("U"), DOWN("D"), LEFT("L"), RIGHT("R");

        public String value;

        Moves(String value) {
            this.value = value;
        }
    }

}
