package Methods;

import java.util.Vector;

public class Gradient {

    private Vector<Float> coef;
    private int generation;
    private float l_r;

    public Gradient(float l_r,int generation) {
        coef=new Vector<>();
        this.generation = generation;
        this.l_r = l_r;
    }

    public void train(Vector<Vector<Float>> matrixX, Vector<Vector<Float>> matrixY){
        for(int i=0;i<matrixX.get(0).size();i++){
            coef.add(0f);
        }
        for(int i=0;i<this.generation;i++){
            this.update(matrixX,matrixY);
        }
    }

    private void update(Vector<Vector<Float>> matrixX, Vector<Vector<Float>> matrixY) {
        Vector<Float> yComp=new Vector<>();
        for(int i=0;i<matrixX.size();i++)
            yComp.add(predict(matrixX.get(i)));
        for(int i=0;i<coef.size();i++)
        {
            float gradient=0;
            for(int j=0;j<matrixX.size();j++)
                gradient+=(yComp.get(i)-matrixY.get(i).get(0))*matrixX.get(j).get(i);
            coef.set(i,coef.get(i)-l_r*gradient);
        }
    }

    private Float predict(Vector<Float> floats) {
        float y=0;
        for(int i = 0; i< floats.size(); i++)
            y+=floats.get(i)*coef.get(i);
        return y;
    }

    public double determineLoss(Vector<Vector<Float>> matrixX, Vector<Vector<Float>> matrixY){
        float med=0f;
        for(int i=0;i<matrixY.size();i++)
            med+=(predict(matrixX.get(i))-matrixY.get(i).get(0))*(predict(matrixX.get(i))-matrixY.get(i).get(0));
        for (int i = 0;i < coef.size();i++)
            System.out.print(coef.get(i)+" ");
        System.out.println();
        return Math.sqrt(med);
    }
}
