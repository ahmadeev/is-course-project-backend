package _repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public abstract class BaseRepository<T, ID> {
    @PersistenceContext
    protected EntityManager em;

    private final Class<T> entityClass;

    public BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T findById(ID id) {
        return em.find(entityClass, id);
    }

    public List<T> findAll() {
        CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

    public T create(T entity) {
        em.persist(entity);
        return entity;
    }

    public List<T> createAll(List<T> entities) {
        for (T entity : entities) {
            em.persist(entity);
        }
        return entities;
    }

    public T update(T entity) {
        return em.merge(entity);
    }

    public void delete(ID id) {
        T entity = findById(id);
        if (entity != null) {
            em.remove(entity);
        }
    }
}
