package gr.ihu.identity.db.model;

import gr.ihu.identity.db.model.UserInfo;
import gr.ihu.identity.db.model.UserPassword;
import gr.ihu.identity.db.model.UserToken;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-11T17:49:17")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, UserInfo> userInfo;
    public static volatile SingularAttribute<User, UserPassword> password;
    public static volatile SingularAttribute<User, UserToken> loginToken;
    public static volatile SingularAttribute<User, Integer> id;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> username;

}