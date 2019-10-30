package Validator;


public interface Validator<E> {
    /**
     * @param entity
     * @throws ValidatorException
     */
    void validate(E entity) throws ValidatorException ;
}
