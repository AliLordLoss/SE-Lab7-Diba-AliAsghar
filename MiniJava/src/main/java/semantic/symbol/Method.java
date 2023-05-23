package semantic.symbol;

import errorHandler.ErrorHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Method {
    private final int codeAddress;
    private final Map<String, Symbol> parameters;
    private final Map<String, Symbol> localVariable;
    private final ArrayList<String> orderdParameters;
    private final int callerAddress;
    private final int returnAddress;
    private final SymbolType returnType;
    private int index;

    public Method(int codeAddress, SymbolType returnType, int returnAddress, int callerAddress) {
        this.codeAddress = codeAddress;
        this.returnType = returnType;
        this.returnAddress = returnAddress;
        this.callerAddress = callerAddress;
        this.orderdParameters = new ArrayList<>();
        this.parameters = new HashMap<>();
        this.localVariable = new HashMap<>();
    }

    public Symbol getVariable(String variableName) {
        if (parameters.containsKey(variableName)) return parameters.get(variableName);
        if (localVariable.containsKey(variableName)) return localVariable.get(variableName);
        return new NullSymbol();
    }

    public void addParameter(String parameterName, SymbolType lastType, int address) {
        parameters.put(parameterName, new Symbol(lastType, address));
        orderdParameters.add(parameterName);
    }

    public void reset() {
        index = 0;
    }

    public Symbol getNextParameter() {
        return parameters.get(orderdParameters.get(index++));
    }

    public SymbolType getReturnType() {
        return returnType;
    }

    public int getReturnAddress() {
        return returnAddress;
    }

    public int getCallerAddress() {
        return callerAddress;
    }

    public int getCodeAddress() {
        return codeAddress;
    }

    public void addLocalVariable(String name, Symbol symbol) {
        if (this.localVariable.containsKey(name)) {
            ErrorHandler.printError("This variable already defined");
        }
        this.localVariable.put(name, symbol);
    }
}
