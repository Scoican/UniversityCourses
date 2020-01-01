import Utils.Constants;

public class Main {
    public static void main(String[] argv){
        Analyzer analyzer = new Analyzer();
        try {
            analyzer.interpret(Constants.INPUT_1,Constants.OUTPUT_1);
            //analyzer.interpret(Constants.INPUT_2,Constants.OUTPUT_2);
            //analyzer.interpret(Constants.INPUT_3,Constants.OUTPUT_3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}