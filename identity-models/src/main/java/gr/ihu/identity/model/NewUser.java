package gr.ihu.identity.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author John
 */

@XmlRootElement(name = "user")
public class NewUser extends ModelUser {
    
    private static final long serialVersionUID = 2933507037899330451L;
    
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
