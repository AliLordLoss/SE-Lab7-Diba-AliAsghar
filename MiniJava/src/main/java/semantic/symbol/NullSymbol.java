package semantic.symbol;

public class NullSymbol extends Symbol {
    public NullSymbol() {
        super();
    }
    public NullSymbol(SymbolType type, int address) {
        super(type, address);
    }

    @Override
    public Boolean isNull() {
        return true;
    }
}
