import Jama.Matrix;

import java.util.Vector;

public class LeastSquaresMethod {
    public static Matrix leastSquareWrapper(DataSet dataset){
        Matrix initialMatrix = dataset.getMatrix();
        Matrix features = dataset.getFeatures();
        //Matrix transposed = initialMatrix.transpose();
        return leastSquare(features,dataset.getResults());
    }

    public static Matrix leastSquare(Matrix initialMatrix, Matrix results){
        Matrix transposeMatrix = initialMatrix.transpose();
        Matrix result = transposeMatrix.times(initialMatrix);
        result = result.inverse();
        result = result.times(transposeMatrix);
        result = result.times(results);
        return result;
    }

    public static Vector<Double> getCoefficients(Matrix m){
        int rows = m.getRowDimension();
        int cols = m.getColumnDimension();
        Vector<Double> results = new Vector<>();
        int k = 0;
        for(int i=0;i<rows;i++) {
            for (int j = 0; j < cols; j++) {
                results.add(k,m.get(i,j));
                k++;
            }
        }
        return results;
    }
}
