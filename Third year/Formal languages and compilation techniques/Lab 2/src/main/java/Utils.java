import Automaton.FiniteAutomaton;
import Automaton.Transition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Utils {

    private static void printMenu() {
        System.out.println("---Finite automaton---");
        System.out.println("1. Print the set of states");
        System.out.println("2. Print the alphabet");
        System.out.println("3. Print transitions");
        System.out.println("4. Print final states");
        System.out.println("5. Sequence validator");
        System.out.println("6. Longest sequence");
        System.out.println("7. Print automaton");
        System.out.println("8. Read new automaton");
        System.out.println("0. Exit");
    }

    private static FiniteAutomaton readNewAutomaton() throws IOException {
        List<String> statesToBeRead;
        List<Integer> stateStatusesToBeRead = new ArrayList<>();
        List<String> alphabetToBeRead = new ArrayList<>();
        List<Transition> transitionsToBeRead = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String source;
        String destination;
        String value;

        System.out.println("Enter states:");
        statesToBeRead = Arrays.asList(reader.readLine().split(" "));
        System.out.println("Enter state Statuses:");
        String[] statuses = reader.readLine().split(" ");
        for (String status : statuses) {
            stateStatusesToBeRead.add(Integer.parseInt(status));
        }
        System.out.println("Enter number of transitions:");
        int numberOfTransition = Integer.parseInt(reader.readLine());
        for (int i = 0; i < numberOfTransition; i++) {
            String[] transitions= reader.readLine().split("/");
            source = transitions[0];
            destination = transitions[1];
            value = transitions[2];
            if (!alphabetToBeRead.contains(value)) {
                alphabetToBeRead.add(value);
            }
            if(!statesToBeRead.contains(source)||!statesToBeRead.contains(destination)){
                System.out.println("Wrong state inserted!");
                break;
            }
            transitionsToBeRead.add(new Transition(source, destination, value));
        }
        return new FiniteAutomaton(statesToBeRead, stateStatusesToBeRead, transitionsToBeRead, alphabetToBeRead);
    }

    static void manageMenu() throws IOException {
        printMenu();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        FiniteAutomaton automaton = new FiniteAutomaton("Matrix");
        List<String> sequence;
        String command;
        while (true) {
            command = reader.readLine();
            switch (command) {
                case "0":
                    return;
                case "1":
                    automaton.printStates();
                    break;
                case "2":
                    automaton.printAlphabet();
                    break;
                case "3":
                    automaton.printTransitions();
                    break;
                case "4":
                    automaton.printFinalStates();
                    break;
                case "5":
                    System.out.println("Please enter sequence:");
                    sequence = Arrays.asList(reader.readLine().split(" "));
                    if (automaton.sequenceValidator(sequence)) {
                        System.out.println("Sequence is valid");
                    } else {
                        System.out.println("Sequence is invalid");
                    }
                    break;
                case "6":
                    System.out.println("Please enter sequence:");
                    sequence = Arrays.asList(reader.readLine().split(" "));
                    automaton.printLongestSequence(sequence);
                    break;
                case "7":
                    automaton.printAutomaton();
                    break;
                case "8":
                    automaton = readNewAutomaton();
                    break;
                default:
                    System.out.println("Wrong command!");
            }
        }
    }
}
