package Methods;

import matrix.Matrix;
import matrix.MatrixMathematics;
import matrix.NoSquareException;
import utils.DataSet;

public class MCMP extends AbstractSolver {
    public MCMP(DataSet testSet, DataSet trainSet) {
        super(testSet, trainSet);
    }
    public Matrix leastSquares() throws NoSquareException {
        //(xt*X)^-1*xt*Y
        Matrix x=getTrainSet().getDataSet();
        Matrix y=getTrainSet().getResultSet();
        Matrix xt= MatrixMathematics.transpose(x);
        Matrix result=MatrixMathematics.multiply(xt,x);
        Matrix inverse=MatrixMathematics.inverse(result);
        result=MatrixMathematics.multiply(inverse,xt);
        return MatrixMathematics.multiply(result,y);
    }

}
