package gr.ihu.identity.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.Stateless;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author John
 */

@Stateless
public class PasswordProtector implements IPasswordProtector {

    @Override
    public String protect(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = password.getBytes();
            md.update(bytes);
            bytes = md.digest();
            return Base64.encodeBase64String(bytes);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }// end of protect() method

}
