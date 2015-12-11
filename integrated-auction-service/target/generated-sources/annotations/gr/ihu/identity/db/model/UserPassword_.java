package gr.ihu.identity.db.model;

import gr.ihu.identity.db.model.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-11T17:49:17")
@StaticMetamodel(UserPassword.class)
public class UserPassword_ { 

    public static volatile SingularAttribute<UserPassword, String> password;
    public static volatile SingularAttribute<UserPassword, Date> validUntil;
    public static volatile SingularAttribute<UserPassword, Integer> id;
    public static volatile SingularAttribute<UserPassword, User> user;

}