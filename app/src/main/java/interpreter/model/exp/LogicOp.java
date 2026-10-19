package interpreter.model.exp;

public enum BoolOp {
    AND("and"), OR("or");
    private final String symbol;
    BoolOp(String symbol) { this.symbol = symbol; }
    @Override public String toString() { return this.symbol; }
}
