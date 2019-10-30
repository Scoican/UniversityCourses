package Methods;

import matrix.Matrix;
import utils.DataSet;

import java.util.ArrayList;

public class LeastSquaresMethod {
    private DataSet dataSet;
    private Integer n;
    private Integer m;
    Matrix matrix;
    ArrayList<Double> xmean=new ArrayList<>();
    ArrayList<Double> coef=new ArrayList<>();

    public LeastSquaresMethod(DataSet dataSet){
        this.dataSet=dataSet;
        this.n=this.dataSet.getIndependentVariables();
        this.m=this.dataSet.getExamples();
        this.matrix=this.dataSet.getMatrix();
    }

    private void calculateMeanX(){
        for(int j=0;j<n-1;j++){
            double sum=0;
            for(int i=0;i<m;i++){
                sum+=matrix.getValueAt(i,j);
            }
            double mean=sum/m;
            xmean.add(mean);
        }
    }

    private Double calculateMeanY(){
        double sum=0;
        for(int i=0;i<m;i++){
            sum+=matrix.getValueAt(i,n-1);
        }
        return sum/m;
    }

    private void calculateParameters(){
        calculateMeanX();
        double ymean=calculateMeanY();
        for(int j=0;j<n-1;j++){
            double num=0;
            double denum=0;
            for(int i=0;i<m;i++){
                num+=(matrix.getValueAt(i,j)-xmean.get(j))*(matrix.getValueAt(i,n-1)-ymean);
                denum+=Math.pow(matrix.getValueAt(i,j)-xmean.get(j),2);
            }
            double co=num/denum;
            coef.add(co);
        }
    }

    public Double calculateError(DataSet dataSet){
        calculateParameters();
        int n=dataSet.getIndependentVariables();
        int m=dataSet.getExamples();
        Matrix mat=dataSet.getMatrix();
        double s=0;
        for(int i=0;i<m;i++){
            double sum=0;
            for(int j=0;j<n-1;j++){
                sum+=(mat.getValueAt(i,j)*coef.get(j));
            }
            double error=Math.abs(sum-mat.getValueAt(i,n-1));
            s+=error;
        }
        return s/m;
    }
}
