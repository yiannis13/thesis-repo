package gr.ihu.identity.db.model;

import gr.ihu.identity.db.model.Address;
import gr.ihu.identity.db.model.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-11T17:49:17")
@StaticMetamodel(UserInfo.class)
public class UserInfo_ { 

    public static volatile SingularAttribute<UserInfo, String> firstName;
    public static volatile SingularAttribute<UserInfo, String> lastName;
    public static volatile SingularAttribute<UserInfo, Address> address;
    public static volatile SingularAttribute<UserInfo, Integer> gender;
    public static volatile SingularAttribute<UserInfo, Integer> id;
    public static volatile SingularAttribute<UserInfo, Date> birthDate;
    public static volatile SingularAttribute<UserInfo, User> user;

}