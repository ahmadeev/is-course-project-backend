package _service;

import jakarta.ejb.EJB;
import _repository.BaseRepository;

import java.util.List;

public abstract class BaseService<T, ID> {
    protected abstract BaseRepository<T, ID> getRepository();

    public T findById(ID id) {
        return getRepository().findById(id);
    }

    public List<T> findAll() {
        return getRepository().findAll();
    }

    public T create(T entity) {
        return getRepository().create(entity);
    }

    public List<T> createAll(List<T> entities) {
        return getRepository().createAll(entities);
    }

    public T update(T entity) {
        return getRepository().update(entity);
    }

    public void delete(ID id) {
        getRepository().delete(id);
    }
}
