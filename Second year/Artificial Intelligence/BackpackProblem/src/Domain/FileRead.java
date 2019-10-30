package Domain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileRead {

    private String fileName;
    public FileRead(String fileName){
        this.fileName=fileName;
    }

    public Item readData(){
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String totalItems=br.readLine();
            String w=br.readLine();
            String v=br.readLine();
            String capacity=br.readLine();
            List<Double> weight=new ArrayList<>(Integer.parseInt(totalItems));
            List<Double> value=new ArrayList<>(Integer.parseInt(totalItems));

            for(int i=0;i<Integer.parseInt(totalItems);i++){
                weight.add(Double.parseDouble(w.split(",")[i]));
                value.add(Double.parseDouble(v.split(",")[i]));
            }
            return new Item(Integer.parseInt(totalItems),weight,value,Integer.parseInt(capacity));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeToFile(String fileToWrite, StringBuilder dataToWrite){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileToWrite,true))) {
            PrintWriter writer = new PrintWriter(fileToWrite);
            writer.print("");
            writer.close();
            bw.write(String.valueOf(dataToWrite));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
