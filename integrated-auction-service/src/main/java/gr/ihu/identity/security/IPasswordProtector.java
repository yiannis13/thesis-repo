package gr.ihu.identity.security;

import javax.ejb.Local;

/**
 *
 * @author John
 */

@Local
public interface IPasswordProtector {
    String protect(String password);
}
