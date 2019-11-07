import BinaryTree.*;
import Utils.Constants;
import Utils.IOActions;

import java.io.IOException;
import java.util.*;

class Analyzer {
    private BinaryTree symbolTable = new BinaryTree();
    private BinaryTree variableTable = new BinaryTree();

    Analyzer() {
        populateSymbolTable();
    }

    private void populateSymbolTable() {
        IOActions io = new IOActions(Constants.TABLE_SYMBOL, null);
        try {
            List<String> lines = io.read();
            Map<String, Integer> unorderedSymbolsTable = new HashMap<>();
            for (String line : lines) {
                unorderedSymbolsTable.put(line.split(Constants.SEPARATOR)[0], Integer.parseInt(line.split(Constants.SEPARATOR)[1]));
            }
            List<String> orderedKeys = getSortedKeys(unorderedSymbolsTable);
            for (String key : orderedKeys) {
                symbolTable.add(key, unorderedSymbolsTable.get(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getSortedKeys(Map<String, Integer> unorderedSymbolsTable) {
        List<String> orderedKeys = new ArrayList<>(unorderedSymbolsTable.keySet());
        Collections.sort(orderedKeys);
        return orderedKeys;
    }

    void interpret(String input, String output) throws Exception {
        IOActions ioActions = new IOActions(input, output);
        int lineIndex = 1;
        try {
            List<String> content = ioActions.read();
            for (String line : content) {
                String[] parts = line.split(" ");
                for (int i = 0; i < parts.length; i++) {
                    if (symbolTable.containsKey(parts[i])) {
                        ioActions.write(symbolTable.findValue(parts[i]) + Constants.LINE);
                    } else {
                        if (variableTable.containsKey(parts[i])) {
                            writeIdentifierOrConstant(parts, i, ioActions, variableTable);
                        } else {
                            if (i > 0 && (parts[i - 1].equalsIgnoreCase("float") || parts[i - 1].equalsIgnoreCase("int") || parts[i - 1].equalsIgnoreCase(","))) {
                                if (parts[i].length() > 8) {
                                    throw new Exception("Syntax error at line " + lineIndex + ",column " + i + ":identifier to long");
                                }
                                variableTable.add(parts[i], generateValue());
                                writeIdentifierOrConstant(parts, i, ioActions, variableTable);
                            } else {
                                if (isConstant(parts[i])) {
                                    variableTable.add(parts[i], generateValue());
                                    writeIdentifierOrConstant(parts, i, ioActions, variableTable);
                                } else {
                                    throw new Exception("Syntax error at line " + lineIndex + " ,column " + i);
                                }
                            }
                        }
                    }
                }
                lineIndex++;
            }
            write();
            ioActions.finishWriting();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write() throws IOException {
        IOActions ioActions = new IOActions(null, Constants.OUTPUT_TABLE);
        Stack<Node> stack = new Stack<Node>();
        Node current = variableTable.getRoot();
        stack.push(current);
        while (!stack.isEmpty()) {
            current = stack.pop();
            ioActions.write(current.getKey() + " " + current.getValue());
            if (current.getRight() != null) {
                stack.push(current.getRight());
            }

            if (current.getLeft() != null) {
                stack.push(current.getLeft());
            }
        }
        ioActions.finishWriting();
    }

    private static boolean isNumeric(String strNum) {
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    private void writeIdentifierOrConstant(String[] parts, int i, IOActions ioActions, BinaryTree variableTable) throws IOException {
        if (isConstant(parts[i])) {
            ioActions.write(symbolTable.findValue("constant").toString() + " " + variableTable.findValue(parts[i]).toString());
        } else {
            ioActions.write(symbolTable.findValue("identifier").toString() + " " + variableTable.findValue(parts[i]).toString());
        }
    }

    private boolean isConstant(String string) {
        for (int i = 0; i < string.length(); i++) {
            if ((string.charAt(i) > '9' || string.charAt(i) < '0') && string.charAt(i) != '.') {
                return false;
            }
        }
        return true;
    }

    private Integer generateValue() {
        int number;
        do {
            number = new Random().nextInt(4001);
        } while (variableTable.containsValue(number));
        return number;
    }
}
