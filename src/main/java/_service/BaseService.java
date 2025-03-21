package _service;

import jakarta.ejb.EJB;
import _repository.BaseRepository;
import jakarta.ejb.TransactionAttribute;
import jakarta.transaction.Transactional;

import java.util.List;

public abstract class BaseService<T, ID> {
    protected abstract BaseRepository<T, ID> getRepository();

    @TransactionAttribute
    public T findById(ID id) {
        return getRepository().findById(id);
    }

    @TransactionAttribute
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @TransactionAttribute
    public T create(T entity) {
        return getRepository().create(entity);
    }

    @TransactionAttribute
    public List<T> createAll(List<T> entities) {
        return getRepository().createAll(entities);
    }

    @TransactionAttribute
    public T update(T entity) {
        return getRepository().update(entity);
    }

    @TransactionAttribute
    public void delete(ID id) {
        getRepository().delete(id);
    }
}
