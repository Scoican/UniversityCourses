package Repository;

import ModelDomain.IHasID;

public interface IRepository<ID,T extends IHasID<ID>> {
    int size();
    void save(T entity);
    void delete(ID id);
    void update(ID id, T entity);
    T findOne(ID id);
    Iterable<T> findAll();
}
