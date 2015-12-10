package gr.ihu.identity.email;

import com.sun.mail.util.MailSSLSocketFactory;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author John
 */
@Stateless
public class EmailService implements IEmailService {

    private final Session mailSession;

    public EmailService() throws GeneralSecurityException {
        final String username = "yiannis.ioannidis@hotmail.gr";
        final String password = "jioannidis13@";

        Properties props = new Properties();

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);

        props.put("mail.smtp.ssl.socketFactory", sf);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.live.com");
        props.put("mail.smtp.port", "25");
        props.put("__sender__", username);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        mailSession = session;
    }

    @Override
    public void sendEmail(String sendTo, String subject, String body) {
        MimeMessage msg = new MimeMessage(mailSession);
        try {
            msg.setSubject(subject);
            msg.setText(body);
            msg.setFrom(new InternetAddress(mailSession.getProperty("__sender__")));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
