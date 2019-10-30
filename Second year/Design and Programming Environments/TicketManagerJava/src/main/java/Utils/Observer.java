package Utils;

public interface Observer<E extends EventUtils> {
    void update(E e);
}
