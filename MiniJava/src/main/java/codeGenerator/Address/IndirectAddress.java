package codeGenerator.Address;


public class IndirectAddress extends DirectAddress {
    public IndirectAddress(int num, codeGenerator.varType varType) {
        super(num, varType);
    }

    @Override
    public String toString() {
        return "@" + num;
    }
}
