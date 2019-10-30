import Methods.Gradient;
import Methods.LeastSquare;
import Methods.LeastSquaresMethod;
import Methods.MCMP;
import javafx.scene.layout.GridPane;
import matrix.NoSquareException;
import utils.DataSet;
import utils.FileRead;
import utils.NewDataSet;
import utils.Utils;

public class Main {
    public static void main(String[] args) {
        String testFile="example_parkinson_01_test";
        String trainFile="example_parkinson_01_train";
        FileRead frTest = new FileRead(testFile);
        FileRead frTrain = new FileRead(trainFile);
        NewDataSet newTestSet=frTest.readVector();
        NewDataSet newTrainSet=frTrain.readVector();

        LeastSquare method=new LeastSquare();
        newTrainSet.normalization();
        method.train(newTrainSet.getMatrixX(),newTrainSet.getMatrixY());

        double loss=method.determineLoss(newTestSet.getMatrixX(),newTestSet.getMatrixY());
        System.out.println("Loss:"+loss);

        Gradient method2=new Gradient(0.000005f, 50);
        method2.train(newTrainSet.getMatrixX(),newTrainSet.getMatrixY());
        System.out.println(method2.determineLoss(newTestSet.getMatrixX(),newTestSet.getMatrixY()));


        /*
        LeastSquaresMethod ls=new LeastSquaresMethod(trainSet);
        System.out.println(ls.calculateError(testSet));

        MCMP mcmp=new MCMP(testSet,trainSet);
        trainSet.normalize();
        try {
            //trainSet.printing();
            System.out.println(Utils.quadraticAverage(mcmp.test(mcmp.leastSquares())));
        } catch (NoSquareException e) {
            e.printStackTrace();
        }*/
    }
}
