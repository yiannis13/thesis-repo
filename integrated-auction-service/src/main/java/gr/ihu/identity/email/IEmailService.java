package gr.ihu.identity.email;

import javax.ejb.Local;

/**
 *
 * @author John
 */

@Local
public interface IEmailService {
    void sendEmail(String sendTo, String subject, String body);
}
