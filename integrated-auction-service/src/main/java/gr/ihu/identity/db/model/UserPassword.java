package gr.ihu.identity.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author John
 */

@Entity
@XmlRootElement
@Table(name = "user_password", catalog = "auction_db", schema = "")
@NamedQueries({
    @NamedQuery(name = "UserPassword.findByUser", query = "SELECT u FROM UserPassword u JOIN u.user usr WHERE usr = :usr"),
    @NamedQuery(name = "UserPassword.findByUserId", query = "SELECT u FROM UserPassword u JOIN u.user usr WHERE usr.id = :id"),
    @NamedQuery(name = "UserPassword.findByUsername", query = "SELECT u FROM UserPassword u JOIN u.user usr WHERE usr.username = :username")})
    public class UserPassword implements Serializable {
    
    private static final long serialVersionUID = 8212819319560162580L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic(optional = false)
    @Column(name = "password", nullable = false, length = 45)
    private String password;
    
    @Column(name = "expire_at")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date validUntil;

    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private User user;
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }
    
    @XmlTransient
    public boolean isValid() {
        return !InternalUtilities.hasExpired(validUntil);
    }
}
