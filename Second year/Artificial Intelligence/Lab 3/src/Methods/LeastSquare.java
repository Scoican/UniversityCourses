package Methods;

import matrix.Matrix;

import java.util.Vector;

public class LeastSquare {

    private Vector<Vector<Float>> auxiliaryVector;
    public void train(Vector<Vector<Float>> matrixX,Vector<Vector<Float>> matrixY){
        Vector<Vector<Float>> transposeX=this.matrixTranspose(matrixX);
        //((Xt*X)^-1*Xt)*Y)
        auxiliaryVector =new Vector<>();
        auxiliaryVector =this.matrixMultiplication(this.matrixMultiplication(this.matrixInverse(this.matrixMultiplication(transposeX,matrixX)),transposeX),matrixY);

        float loss=0f;
        for(int i=0;i<matrixY.size();i++){
            loss+=matrixY.get(i).get(0)-this.predict(matrixX.get(i));
        }
        loss/=matrixY.size();
    }

    private Float predict(Vector<Float> x) {
        float yp=0;
        int i;
        for(i=0;i<x.size();i++)
            yp+=x.get(i)* auxiliaryVector.get(i).get(0);
        return yp;
    }

    private Vector<Vector<Float>> matrixTranspose(Vector<Vector<Float>> matrixX) {
        Vector<Vector<Float>> transpose=new Vector<>();
        for(int i=0;i<matrixX.get(0).size();i++)
        {
            Vector<Float> line=new Vector<>();
            for(int j=0;j<matrixX.size();j++)
                line.add(matrixX.get(j).get(i));
            transpose.add(line);
        }
        return transpose;
    }

    private Vector<Vector<Float>> matrixMultiplication(Vector<Vector<Float>> a,Vector<Vector<Float>>b){
        Vector<Vector<Float>> result=new Vector<>();
        for(int i=0;i<a.size();i++){
            Vector<Float> line=new Vector<>();
            for(int j=0;j<b.get(0).size();j++)
                line.add(0f);
            result.add(line);
        }
        for(int i=0;i<a.size();i++){
            for(int j=0;j<b.get(0).size();j++){
                for(int x=0;x<a.get(i).size();x++) {
                    result.get(i).set(j,result.get(i).get(j)+a.get(i).get(x)*b.get(x).get(j));
                }
            }
        }
        return result;
    }

    private Vector<Vector<Float>> matrixInverse(Vector<Vector<Float>> matrix){
        Float determinant=this.getDeterminant(matrix);
        print(matrix);
        if(determinant==0){
            return new Vector<>();
        }
        Vector<Vector<Float>> transpose=matrixTranspose(matrix);
        transpose=this.matrixComplement(transpose);
        for(int i=0;i<transpose.size();i++)
            for(int j=0;j<transpose.get(i).size();j++)
                transpose.get(i).set(j,transpose.get(i).get(j)*(1/determinant));
        return transpose;
    }

    private Vector<Vector<Float>> matrixComplement(Vector<Vector<Float>> transpose) {
        Vector<Vector<Float>> complement=new Vector<>();
        for(int i=0;i<transpose.size();i++){
            Vector<Float> line=new Vector<>();
            for(int j=0;j<transpose.get(i).size();j++){
                float result=(i+j)*getDeterminant(getCofactor(transpose,j,i));
                if(Float.floatToIntBits(result)%2==0){
                    line.add(1f);
                }else{
                    line.add(-1f);
                }
            }
            complement.add(line);
        }
        return complement;
    }

    private Float getDeterminant(Vector<Vector<Float>> matrix) {
        if(matrix.size()==1){
            return matrix.get(0).get(0);
        }
        Vector<Vector<Float>> temporaryVector;
        int sing=1;
        float result=0;
        for(int i =0;i<matrix.size();i++){
            temporaryVector=this.getCofactor(matrix,i,0);
            result +=sing*matrix.get(0).get(i)*getDeterminant(temporaryVector);
            sing*=-1;
        }
        return result;
    }

    private Vector<Vector<Float>> getCofactor(Vector<Vector<Float>> matrix, int col, int row) {
        Vector<Vector<Float>> temporaryVector=new Vector<>();
        for(int i=0;i<matrix.size();i++){
            Vector<Float> line=new Vector<>();
            for(int j=0;j<matrix.get(i).size();j++){
                if(i!=row&&j!=col){
                    line.add(matrix.get(i).get(j));
                }
                if(i==row){
                    break;
                }
            }
            if(!line.isEmpty()){
                temporaryVector.add(line);
            }
        }
        return temporaryVector;
    }

    public double determineLoss(Vector<Vector<Float>> matrixX, Vector<Vector<Float>> matrixY){
        Float med=0f;
        for (int i = 0;i < matrixY.size();i++) {
            med += (predict(matrixX.get(i)) - matrixY.get(i).get(0))*(predict(matrixX.get(i)) - matrixY.get(i).get(0));
        }
        for(int i = 0; i< auxiliaryVector.size(); i++)
            System.out.print(auxiliaryVector.get(i).get(0)+" ");
        System.out.println();
        return Math.sqrt(med);
    }

    public void print(Vector<Vector<Float>> matrix){
        for(Vector<Float> vec: matrix){
            for(Float number:vec){
                System.out.print(number+" ");
            }
            System.out.println();
        }
    }
}
