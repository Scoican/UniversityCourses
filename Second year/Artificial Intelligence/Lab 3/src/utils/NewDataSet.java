package utils;

import java.util.Vector;

public class NewDataSet {
    Vector<Vector<Float>> matrixX;
    Vector<Vector<Float>> matrixY;
    Float variables;
    Float examples;

    public NewDataSet(Vector<Vector<Float>> matrixX, Vector<Vector<Float>> matrixY, Float variables, Float examples) {
        this.matrixX = matrixX;
        this.matrixY = matrixY;
        this.variables = variables;
        this.examples = examples;
    }

    public Vector<Vector<Float>> getMatrixX() {
        return matrixX;
    }

    public void setMatrixX(Vector<Vector<Float>> matrixX) {
        this.matrixX = matrixX;
    }

    public Vector<Vector<Float>> getMatrixY() {
        return matrixY;
    }

    public void setMatrixY(Vector<Vector<Float>> matrixY) {
        this.matrixY = matrixY;
    }

    public Float getVariables() {
        return variables;
    }

    public void setVariables(Float variables) {
        this.variables = variables;
    }

    public Float getExamples() {
        return examples;
    }

    public void setExamples(Float examples) {
        this.examples = examples;
    }

    public void printing(){
        System.out.println("X=");
        for(int i=0;i<this.examples;i++){
            for (int j=0;j<this.variables-1;j++){
                System.out.print(matrixX.get(i).get(j)+" ");
            }
            System.out.println();
        }
        System.out.println("Y=");
        for(int i=0;i<this.examples;i++){
            System.out.println(matrixY.get(i).get(0));
        }
    }

    public void normalization(){
        for(int i=0;i<this.matrixX.get(0).size();i++){
            Float med=0f;
            for(int j=0;j<this.matrixX.size();j++){
                med+=this.matrixX.get(j).get(i);
            }
            med /= this.matrixX.get(0).size();
            Float dev=0f;
            for(int j=0;j<this.matrixX.size();j++){
                dev+=(this.matrixX.get(j).get(i)-med)*(this.matrixX.get(j).get(i)-med);
            }
            dev = 1/((dev/this.matrixX.get(0).size())*(dev/this.matrixX.get(0).size()));
            for(int j=0;j<this.matrixX.size();j++){
                this.matrixX.get(j).set(i,(matrixX.get(j).get(i)/dev));
            }
        }

    }
}
