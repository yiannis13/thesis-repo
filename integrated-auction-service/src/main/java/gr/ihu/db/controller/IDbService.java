package gr.ihu.db.controller;

import java.util.List;

/**
 *
 * @author John
 */

public interface IDbService<T> {

    void create(T entity);

    void edit(T entity);
    
    void remove(T entity);

    T find(Object id);

    List<T> findAll();

    List<T> findRange(int start, int length);

    int count();
    
}
