package semantic.symbol;

import errorHandler.ErrorHandler;

import java.util.HashMap;
import java.util.Map;

public class Klass {
    private final Map<String, Symbol> fields;
    private final Map<String, Method> methods;
    private Klass superClass;

    public Klass() {
        fields = new HashMap<>();
        methods = new HashMap<>();
    }

    public void addField(String name, Symbol field) {
        this.fields.put(name, field);
    }

    private Boolean methodExists(String name) {
        return this.methods.containsKey(name);
    }

    public void addMethod(String name, Method method) {
        if (this.methodExists(name)) {
            ErrorHandler.printError("This method already defined");
        }
        this.methods.put(name, method);
    }

    public Method getMethod(String name) {
        return this.methods.get(name);
    }

    public Symbol getField(String fieldName) {
        if (fields.containsKey(fieldName)) {
            return fields.get(fieldName);
        }
        return superClass.getField(fieldName);

    }

    public void setSuperClass(Klass superClass) {
        this.superClass = superClass;
    }

    public void addMethodLocalVariable(String methodName, String localVariableName, Symbol symbol) {
        this.getMethod(methodName).addLocalVariable(localVariableName, symbol);
    }
}
