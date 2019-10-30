package utils;

import matrix.Matrix;

import java.io.*;
import java.util.Vector;

public class FileRead {

    private String fileName;
    public FileRead(String fileName){
        this.fileName=fileName;
    }

    public DataSet readData(){
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String independentVariables=br.readLine();
            String examples=br.readLine();
            Float[][] data=new Float[Integer.parseInt(examples)][Integer.parseInt(independentVariables)];
            /*for(int i=0;i<Integer.parseInt(examples);i++){
                data[i][0]=1f;
            }*/
            for(int i=0;i<Integer.parseInt(examples);i++){
                String line=br.readLine();
                for(int j=0;j<Integer.parseInt(independentVariables);j++){
                    data[i][j]=Float.parseFloat(line.split(",")[j]);
                }
            }
            return new DataSet(Integer.parseInt(independentVariables),Integer.parseInt(examples),new Matrix(data));

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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

    public NewDataSet readVector(){
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            Float independentVariables=Float.parseFloat(br.readLine());
            Float examples=Float.parseFloat(br.readLine());
            Vector<Vector<Float>> matrixX=new Vector<>();
            Vector<Vector<Float>> matrixY=new Vector<>();
            for(int i=0;i<examples;i++) {
                Vector<Float> vectorX=new Vector<>();
                Vector<Float> vectorY=new Vector<>();
                String line=br.readLine();
                for (int j = 0; j < independentVariables - 1; j++) {
                    vectorX.add(Float.parseFloat(line.split(",")[j]));
                }
                vectorY.add(Float.parseFloat(line.split(",")[line.split(",").length-1]));
                matrixX.add(vectorX);
                matrixY.add(vectorY);
            }
            return new NewDataSet(matrixX,matrixY,independentVariables,examples);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
