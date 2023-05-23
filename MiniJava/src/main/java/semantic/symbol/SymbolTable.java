package semantic.symbol;

import codeGenerator.Address.DirectAddress;
import codeGenerator.Address.ImidiateAddress;
import codeGenerator.Memory;
import codeGenerator.varType;
import errorHandler.ErrorHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private Map<String, Klass> klasses;
    private Map<String, DirectAddress> keyWords;
    private Memory mem;
    private SymbolType lastType;

    public SymbolTable(Memory memory) {
        mem = memory;
        klasses = new HashMap<>();
        keyWords = new HashMap<>();
        keyWords.put("true", new ImidiateAddress(1, varType.Bool));
        keyWords.put("false", new ImidiateAddress(0, varType.Bool));
    }

    public void setLastType(SymbolType type) {
        lastType = type;
    }

    public void addClass(String className) {
        if (klasses.containsKey(className)) {
            ErrorHandler.printError("This class already defined");
        }
        klasses.put(className, new Klass());
    }

    public void addField(String fieldName, String className) {
        klasses.get(className).addField(fieldName, new Symbol(lastType, mem.getDataAddress()));
        mem.increaseDataAddress();
    }

    public void addMethod(String className, String methodName, int address) {
        klasses.get(className).addMethod(methodName, new Method(address, lastType));
    }

    public void addMethodParameter(String className, String methodName, String parameterName) {
        klasses.get(className).getMethod(methodName).addParameter(parameterName);
    }

    public void addMethodLocalVariable(String className, String methodName, String localVariableName) {
        klasses.get(className).addMethodLocalVariable(methodName, localVariableName, new Symbol(lastType,
                mem.getDataAddress()));
        mem.increaseDataAddress();
    }

    public void setSuperClass(String superClass, String className) {
        klasses.get(className).setSuperClass(klasses.get(superClass));
    }

    public DirectAddress get(String keywordName) {
        return keyWords.get(keywordName);
    }

    public Symbol get(String fieldName, String className) {
//        try {
        return klasses.get(className).getField(fieldName);
//        }catch (NullPointerException n)
//        {
//            n.printStackTrace();
//            return null;
//        }
    }

    public Symbol get(String className, String methodName, String variable) {
        Symbol res = klasses.get(className).getMethod(methodName).getVariable(variable);
        if (res.isNull()) res = get(variable, className);
        return res;
    }

    public Symbol getNextParam(String className, String methodName) {
        return klasses.get(className).getMethod(methodName).getNextParameter();
    }

    public void startCall(String className, String methodName) {
//        try {
        klasses.get(className).getMethod(methodName).reset();
//        }catch (NullPointerException n)
//        {
//            n.printStackTrace();
//        }
    }

    public int getMethodCallerAddress(String className, String methodName) {
        return klasses.get(className).getMethod(methodName).callerAddress;
    }

    public int getMethodReturnAddress(String className, String methodName) {
        return klasses.get(className).getMethod(methodName).returnAddress;
    }

    public SymbolType getMethodReturnType(String className, String methodName) {
//        try {
        return klasses.get(className).getMethod(methodName).returnType;
//        }catch (NullPointerException ed){
//            ed.printStackTrace();
//            return null;
//        }

    }

    public int getMethodAddress(String className, String methodName) {
        return klasses.get(className).getMethod(methodName).codeAddress;
    }




    class Method {
        public int codeAddress;
        public Map<String, Symbol> parameters;
        public Map<String, Symbol> localVariable;
        private ArrayList<String> orderdParameters;
        public int callerAddress;
        public int returnAddress;
        public SymbolType returnType;
        private int index;

        public Method(int codeAddress, SymbolType returnType) {
            this.codeAddress = codeAddress;
            this.returnType = returnType;
            this.orderdParameters = new ArrayList<>();
            this.returnAddress = mem.getDataAddress();
            mem.increaseDataAddress();
            this.callerAddress = mem.getDataAddress();
            mem.increaseDataAddress();
            this.parameters = new HashMap<>();
            this.localVariable = new HashMap<>();
        }

        public Symbol getVariable(String variableName) {
            if (parameters.containsKey(variableName)) return parameters.get(variableName);
            if (localVariable.containsKey(variableName)) return localVariable.get(variableName);
            return new NullSymbol();
        }

        public void addParameter(String parameterName) {
            parameters.put(parameterName, new Symbol(lastType, mem.getDataAddress()));
            mem.increaseDataAddress();
            orderdParameters.add(parameterName);
        }

        private void reset() {
            index = 0;
        }

        private Symbol getNextParameter() {
            return parameters.get(orderdParameters.get(index++));
        }
    }

}

//class Symbol{
//    public SymbolType type;
//    public int address;
//    public Symbol(SymbolType type , int address)
//    {
//        this.type = type;
//        this.address = address;
//    }
//}
//enum SymbolType{
//    Int,
//    Bool
//}