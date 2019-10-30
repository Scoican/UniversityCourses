import Jama.Matrix;

import java.util.HashMap;
import java.util.Vector;

public class DataSet {
    private Vector<Vector<Double>> data;
    private Vector<Double> originalResultSet;
    private Matrix matrix ;
    private Integer nrAttributes;
    private Integer nrExamples;

    DataSet(Vector<Vector<Double>> dataSet, Integer nrAttributes, Integer nrExamples, boolean normalization){
        this.data =dataSet;
        this.nrAttributes=nrAttributes;
        this.nrExamples=nrExamples;
        this.originalResultSet=copy(this.getResultSet());
        if(normalization) {
            this.setDataToInterval();
        }
        this.matrix=this.readMatrix();
    }

    private Vector<Double> copy(Vector<Double> list){

        Vector<Double> copyList = new Vector<>();
        for(int i=0;i<list.size();i++)
            copyList.add(i,list.get(i));
        return copyList;

    }

    private void setDataToInterval() {
        for(int i=0;i<nrAttributes;i++){
            for(int j=0;j<nrExamples;j++){
                Double mapped = this.mapToBinary(getMinFromSet(i),getMaxFromSet(i),data.get(i).get(j));
                Vector<Double> values = data.get(i);
                values.set(j,mapped);
                data.set(i,values);
            }
        }
    }

    private Double mapToBinary(Double minFromSet, Double maxFromSet, Double value) {
        return (value - minFromSet) / (maxFromSet - minFromSet);
    }

    private Double getMinFromSet(int index) {
        Double min = data.get(index).get(0);
        for(int i=0;i<nrExamples;i++) {
            if (data.get(index).get(i) < min) {
                min = data.get(index).get(i);
            }
        }
        return min;
    }

    public void printing(){
        for(Vector<Double> vector:data){
            for(Double nr:vector){
                System.out.print(nr+" ");
            }
            System.out.println(" ");
        }
    }

    private Double getMaxFromSet(int index) {
        Double max = data.get(index).get(0);
        for(int i=0;i<nrExamples;i++) {
            if (data.get(index).get(i) > max) {
                max = data.get(index).get(i);
            }
        }
        return max;
    }

    private Matrix readMatrix() {
        if (data.size() > 0) {
            int rows = data.get(0).size();
            int cols = data.size();
            Matrix matrix = new Matrix(rows, cols);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    matrix.set(i, j, data.get(j).get(i));
                }
            }
            return matrix;
        }
        else
            return new Matrix(0,0);
    }

    public Vector<Vector<Double>> getData() {
        return data;
    }

    public void setData(Vector<Vector<Double>> data) {
        this.data = data;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public Integer getNrAttributes() {
        return nrAttributes;
    }

    public void setNrAttributes(Integer nrAttributes) {
        this.nrAttributes = nrAttributes;
    }

    public Integer getNrExamples() {
        return nrExamples;
    }

    public void setNrExamples(Integer nrExamples) {
        this.nrExamples = nrExamples;
    }

    public Matrix getFeatures(){

        return  matrix.getMatrix(0, matrix.getRowDimension() - 1, 0, matrix.getColumnDimension() - 2);
    }

    public Matrix getResults(){

        return matrix.getMatrix(0,matrix.getRowDimension() -1,matrix.getColumnDimension() -1,matrix.getColumnDimension() -1);
    }

    public Vector<Double> getResultSet(){
        return data.get(nrAttributes-1);
    }

    public Vector<Double> getOriginalResultSet(){
        return this.originalResultSet;
    }

    public double translate(double value,double left_min,double left_max,double right_min,double right_max){
        double left = left_max - left_min;
        double right = right_max - right_min;
        double value_scaled = (value - left_min)/left;
        return right_min + (value_scaled * right);
    }

    public Integer getNrClasses(){

        Vector<Double> result = getResultSet();
        HashMap<Double,Integer> map_result = new HashMap<>();
        for(Double res : result){
            map_result.put(res,1);
        }
        return map_result.size();
    }

    public Vector<Double> getColumn(int index){

        Vector<Double> column = new Vector<>(nrAttributes);
        for(int i=0;i<nrAttributes;i++)
            column.add(i,data.get(i).get(index));
        return column;
    }
}
