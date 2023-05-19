package codeGenerator.Address;

import codeGenerator.varType;

/**
 * Created by mohammad hosein on 6/28/2015.
 */

public class DirectAddress {
    public int num;
    public codeGenerator.varType varType;


    public DirectAddress(int num, varType varType) {
        this.num = num;
        this.varType = varType;
    }

    public String toString() {
        return num + "";
    }
}
