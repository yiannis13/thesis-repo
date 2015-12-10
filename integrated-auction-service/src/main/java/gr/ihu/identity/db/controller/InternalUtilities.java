package gr.ihu.identity.db.controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author John
 */

class InternalUtilities {
    
    static <T> T getFirstOrDefault(TypedQuery<T> query) {
        List<T> result = query.getResultList();
        if(result == null || result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }
    
    static <T> T getFirstOrDefault(EntityManager em, Class<T> type, String query, String parameterName, Object val) {       
        TypedQuery<T> tq = em.createNamedQuery(query, type)
                .setParameter(parameterName, val);
        return InternalUtilities.getFirstOrDefault(tq);
    }
    
    static <T> List<T> getResults(EntityManager em, Class<T> type, String query, String parameterName, Object val) {       
        TypedQuery<T> tq = em.createNamedQuery(query, type)
                .setParameter(parameterName, val);
        return tq.getResultList();
    }
}
