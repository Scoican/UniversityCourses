package utils;

import java.util.Vector;
import java.lang.Math;
public class Utils {

    public static double quadraticAverage(Vector<Float> errors){
        float quadraticSum=0f;
        for(Float err:errors){
            quadraticSum=quadraticSum+err*err;
        }
        return Math.sqrt(quadraticSum);
    }


}
