package utils;

import matrix.Matrix;

public class DataSet {
    private Integer independentVariables;
    private Integer examples;
    private Matrix matrix;

    public DataSet(Integer independentVariables, Integer examples) {
        this.independentVariables = independentVariables;
        this.examples = examples;
        this.matrix = new Matrix(examples,independentVariables);
    }

    public DataSet(Integer independentVariables, Integer examples, Matrix matrix) {
        this.independentVariables = independentVariables;
        this.examples = examples;
        this.matrix = matrix;
    }

    public Integer getIndependentVariables() {
        return independentVariables;
    }

    public void setIndependentVariables(Integer independentVariables) {
        this.independentVariables = independentVariables;
    }

    public Integer getExamples() {
        return examples;
    }

    public void setExamples(Integer examples) {
        this.examples = examples;
    }


    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public void printing() {
        System.out.println(this.independentVariables);
        System.out.println(this.examples);
        for (int i = 0; i < matrix.getNrows(); i++) {
            for (int j = 0; j < matrix.getNcols(); j++) {
                System.out.print(matrix.getValueAt(i,j)+" ");
            }
            System.out.println();
        }
    }

    public Matrix getResultSet(){
        Matrix results=new Matrix(examples,1);
        for(int i=0;i<examples;i++){
            results.setValueAt(i,0,matrix.getValueAt(i,matrix.getNcols()-1));
        }
        return results;
    }

    public Matrix getDataSet(){
        Matrix results=new Matrix(examples,independentVariables);
        for (int i = 0; i < matrix.getNrows(); i++) {
            for (int j = 0; j < matrix.getNcols()-1; j++) {
                results.setValueAt(i,j,matrix.getValueAt(i,j));
            }
        }
        return results;
    }

    public void normalize() {
        Matrix normalizedMatrix= new Matrix(matrix.getNrows(),matrix.getNcols());
        for (int i = 0; i < matrix.getNrows(); i++) {
            for (int j = 0; j < matrix.getNcols()-1; j++) {
                normalizedMatrix.setValueAt(i,j,matrix.getValueAt(i,j));
            }
        }
        for(int i=1;i<matrix.getNcols();i++){
            for(int j=0;j<matrix.getNrows();j++){
                normalizedMatrix.setValueAt(j,i,this.setTo01(this.minFromSet(i),this.maxFromSet(i),matrix.getValueAt(j,i)));
            }
        }
        this.matrix=normalizedMatrix;
    }

    public float setTo01(float min,float max,float value){
        //System.out.println(min+" "+max+" "+value+" "+(value - min) / (max - min));
        return (value - min) / (max - min);
    }

    public float minFromSet(int i){
        float min=matrix.getValueAt(1,i);
        for(int j=0;j<matrix.getNrows();j++){
            if(matrix.getValueAt(j,i)<min){
                min=matrix.getValueAt(j,i);
            }
        }
        return min;
    }

    public float maxFromSet(int i){
        float max=matrix.getValueAt(1,i);
        for(int j=0;j<matrix.getNrows();j++){
            if(matrix.getValueAt(j,i)>max){
                max=matrix.getValueAt(j,i);
            }
        }
        return max;
    }
}
