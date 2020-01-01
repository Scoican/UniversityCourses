package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FiniteAutomaton {

    private String filename;
    private List<String> states;
    private List<Integer> statesStatus;
    private List<Transition> transitions;
    private List<String> alphabet;
    private List<String> finalStates;
    private List<String> initialStates;

    public FiniteAutomaton(String filename){

        this.filename = filename;
        this.states = new ArrayList<>();
        this.statesStatus = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.finalStates = new ArrayList<>();
        this.initialStates = new ArrayList<>();
        this.ReadFile();
        this.CalculateStates();
    }

    public FiniteAutomaton(List<String> states, List<Integer> statesStatuses, List<Transition> transitions, List<String> alphabet) {
        this.states = states;
        this.statesStatus = statesStatuses;
        this.transitions = transitions;
        this.alphabet = alphabet;
        this.initialStates = new ArrayList<>();
        this.finalStates = new ArrayList<>();
        this.CalculateStates();
    }

    public String getFileName() {
        return filename;
    }

    public List<String> getStates() {
        return states;
    }

    public List<Integer> getStatesStatuses() {
        return statesStatus;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public List<String> getInitialStates() {
        return initialStates;
    }

    public void printAutomaton() {
        for (String state : this.states) {
            System.out.print(state + " ");
        }
        System.out.println();
        for (Integer status : this.statesStatus) {
            System.out.print(status + " ");
        }
        System.out.println();
        for (Transition transition : this.transitions) {
            System.out.println(transition.toString());
        }
        for (String letter : this.alphabet) {
            System.out.print(letter + " ");
        }
        System.out.println();
    }

    private void ReadFile() {
        int lineIndex = 0;
        String[] lineElements;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = null;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (line != null) {
                if (lineIndex == 0) {
                    lineElements = line.split(" ");
                    for (String state : lineElements) {
                        state = state.replace("\n", "");
                        this.states.add(state);
                    }
                }
                if (lineIndex == 1) {
                    lineElements = line.split(" ");
                    for (String status : lineElements) {
                        this.statesStatus.add(Integer.parseInt(status));
                    }
                }
                if (lineIndex > 1) {
                    lineElements = line.split(" ");
                    lineElements[2] = lineElements[2].replace("\n", "");
                    this.transitions.add(new Transition(lineElements[0], lineElements[1], lineElements[2]));
                    if (!this.alphabet.contains(lineElements[2])) {
                        this.alphabet.add(lineElements[2]);
                    }
                }
                lineIndex++;
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void CalculateStates() {
        for (int i = 0; i < states.size(); i++) {
            if (statesStatus.get(i) == 2) {
                finalStates.add(states.get(i));
            }
            if (statesStatus.get(i) == 1) {
                initialStates.add(states.get(i));
            }
        }
    }

    public void printStates() {
        for (String state : this.states) {
            System.out.print(state + " ");
        }
        System.out.println();
    }

    public void printAlphabet() {
        for (String letter : this.alphabet) {
            System.out.print(letter + " ");
        }
        System.out.println();
    }

    public void printTransitions() {
        for (Transition transition : this.transitions) {
            System.out.println(transition.toString());
        }
    }

    public void printFinalStates() {
        for (String state : this.finalStates) {
            System.out.print(state + " ");
        }
        System.out.println();
    }

    public boolean validateSequence(List<String> sequence) {
        String currentState = this.initialStates.get(0);
        String nextState;
        for (int i = 0; i < sequence.size(); i++) {
            nextState = "";
            for (Transition transition : this.transitions) {
                if (transition.getSource().equals(currentState) && transition.getElement().equals(sequence.get(i))) {
                    nextState = transition.getDestination();
                    break;
                }
            }
            if (nextState.equals("")) {
                return false;
            }

            if (this.finalStates.contains(nextState) && i == sequence.size() - 1) {
                return true;
            }

            currentState = nextState;
        }
        return false;
    }

    public void printLongestPrefix(List<String> sequence) {
        List<String> subSequence = new ArrayList<>();
        for (int i = sequence.size(); i > 0; i--) {
            if (validateSequence(sequence.subList(0, i)) && sequence.subList(0,i).size() > subSequence.size()) {
                subSequence = sequence.subList(0, i);
            }
        }
        for (String sequenceElement : subSequence) {
            System.out.print(sequenceElement + " ");
        }
        System.out.println("\n");
    }

    public boolean validateSequenceString(String sequence) {
        String currentState = this.initialStates.get(0);
        String nextState;
        boolean isTrue = true;
        for (int i = 0; i < sequence.length() && isTrue; i++) {
            nextState = "";
            String character = sequence.substring(i,i+1);
            for (Transition transition : this.transitions) {
                if (transition.getSource().equals(currentState) && transition.getElement().equals(character)) {
                    nextState = transition.getDestination();
                    break;
                }
            }
            if (nextState.equals("")) {
                isTrue = false;
            }

            if (this.finalStates.contains(nextState) && i == sequence.length() - 1) {
                isTrue = true;
            }

            currentState = nextState;
        }
        return isTrue;
    }

}
