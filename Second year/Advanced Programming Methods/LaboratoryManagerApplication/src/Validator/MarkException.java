package Validator;

import Domain.Mark;

/**
 * Class that validates the arguments of a Mark entity
 */
public class MarkException implements Validator<Mark> {

    @Override
    public void validate(Mark entity){
        String error="Wrong info:";
        if(entity.getValue()==null||entity.getValue()<1||entity.getValue()>10){
            error=error+"Value;";
        }
        if(entity.getFeedback().equals("")){
            error=error+"Feedback;";
        }
        if(!error.equals("Wrong info:")){
            throw new ValidatorException(error);
        }
    }
}
