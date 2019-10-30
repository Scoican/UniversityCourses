package Validator;

import Domain.Project;

/**
 * Class that validates the arguments of a Project entity
 */
public class ProjectException implements Validator<Project>{

    @Override
    public void validate(Project entity) {
        String error="Date gresite:";
        if(entity.getID().equals("")){
            error=error+"ID;";
        }
        if(entity.getDescription().equals("")){
            error=error+"Description;";
        }
        if(entity.getCurrentWeek()<=0||entity.getCurrentWeek()>=15){
            error=error+"CurrentWeek;";
        }
        if(entity.getDeadLine()<=1||entity.getDeadLine()>=15){
            error=error+"Deadline;";
        }
        if(entity.getStartWeek()<=0||entity.getStartWeek()>=14) {
            error = error + "StartWeek;";
        }
        if(entity.getDeadLine()<=entity.getStartWeek()){
            error = error + "Deadline doesn't match with start week;";
        }
        if(!error.equals("Date gresite:")){
            throw new ValidatorException(error);
        }
    }
    /**
     * Method which checks if a string contains numbers
     * @param name-String
     * @return true if the string contains any number,false otherwise
     */
    private boolean containsNumbers(String name) {
        char[] chars = name.toCharArray();
        for (char c : chars) {
            if(!Character.isLetter(c)&&c!=' ') {
                return true;
            }
        }
        return false;
    }
}
