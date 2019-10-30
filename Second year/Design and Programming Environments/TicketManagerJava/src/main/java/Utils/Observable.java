package Utils;


public interface Observable<E extends EventUtils>  {
    void addObserver(Observer<E> e);
    void removeObserver(Observer<E> e);
    void notifyObservers(E t);
}
