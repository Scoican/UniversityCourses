package Validator;

import Domain.Student;

/**
 * Class that validates the arguments of a Student entity
 */
public class StudentException implements Validator<Student> {
    @Override
    public void validate(Student entity) {
        String error="Date gresite:";
        if(entity.getID().equals("")){
            error=error+"ID;";
        }
        if(!entity.getEmail().contains("@gmail.com") &&!entity.getEmail().contains("@yahoo.com")&&!entity.getEmail().contains("@cs.ubbcluj.ro")){
            error=error+"Email;";
        }
        if(entity.getName().equals("")|| containsNumbers(entity.getName())){
            error=error+"Name;";
        }
        if(entity.getTeacher().equals("")|| containsNumbers(entity.getName())){
            error=error+"Teacher;";
        }
        if(entity.getGroup().equals("")|| !entity.getGroup().matches("-?\\d+(\\.\\d+)?")){
            error=error+"Group;";
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
