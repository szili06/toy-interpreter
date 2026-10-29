package interpreter.model.exp;

public enum RelOp {
    EQ("=="), LT("<"), LTE("<="), GT(">"), GTE(">="), NEQ("!=");
    private final String symbol;
    RelOp(String symbol) {
        this.symbol = symbol;
    }
    public String toString() {
        return symbol;
    }
}
