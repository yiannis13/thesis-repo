package gr.ihu.identity.db.model;

import java.util.Date;

/**
 *
 * @author John
 */

class InternalUtilities {
    
    static boolean hasExpired(Date date) {
        if(date == null) {
            return false;
        }
        Date now = new Date();
        return date.before(now);
    } 
}
