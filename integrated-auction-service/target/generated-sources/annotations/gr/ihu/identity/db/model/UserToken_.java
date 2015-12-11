package gr.ihu.identity.db.model;

import gr.ihu.identity.db.model.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-11T17:49:17")
@StaticMetamodel(UserToken.class)
public class UserToken_ { 

    public static volatile SingularAttribute<UserToken, Date> validUntil;
    public static volatile SingularAttribute<UserToken, Integer> id;
    public static volatile SingularAttribute<UserToken, Integer> type;
    public static volatile SingularAttribute<UserToken, String> value;
    public static volatile SingularAttribute<UserToken, User> user;

}