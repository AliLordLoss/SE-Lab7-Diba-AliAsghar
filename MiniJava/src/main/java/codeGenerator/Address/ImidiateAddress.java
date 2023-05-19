package codeGenerator.Address;

public class ImidiateAddress extends DirectAddress {
    public ImidiateAddress(int num, codeGenerator.varType varType) {
        super(num, varType);
    }

    @Override
    public String toString() {
        return "#" + num;
    }
}
