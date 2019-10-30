import java.io.*;
import java.util.Vector;

public class FileRead {
    private String fileName;
    private boolean condition;
    public FileRead(String fileName,boolean condition){
        this.fileName=fileName;
        this.condition=condition;
    }

    public void writeToFile(String fileToWrite, StringBuilder dataToWrite){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt",true))) {
            PrintWriter writer = new PrintWriter(fileToWrite);
            writer.print("");
            writer.close();
            bw.write(String.valueOf(dataToWrite));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataSet readVector(){
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            Integer independentVariables=Integer.parseInt(br.readLine());
            Integer examples=Integer.parseInt(br.readLine());
            Vector<Vector<Double>> matrix=new Vector<>();
            for(int i=0;i<independentVariables;i++) {
                matrix.add(new Vector<>(examples));
            }
            for(int i=0;i<examples;i++) {
                String line=br.readLine();
                String[] values = line.split(",");
                for(int j=0;j<independentVariables;j++){
                    matrix.get(j).add(i,Double.parseDouble(values[j]));
                }
            }
            return new DataSet(matrix,independentVariables,examples,condition);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
