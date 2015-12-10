package gr.ihu.db.controller;

import javax.persistence.EntityManager;

/**
 *
 * @author John
 */

public class GenericRepository<T> extends AbstractFacade<T> {

    private final EntityManager em;

    public GenericRepository(EntityManager em, Class<T> entityClass) {
        super(entityClass);
        this.em = em;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
