package gr.ihu.identity.db.model;

import gr.ihu.identity.db.model.UserInfo;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-11T17:49:17")
@StaticMetamodel(Address.class)
public class Address_ { 

    public static volatile SingularAttribute<Address, String> zip;
    public static volatile SingularAttribute<Address, String> country;
    public static volatile SingularAttribute<Address, UserInfo> userInfo;
    public static volatile SingularAttribute<Address, String> city;
    public static volatile SingularAttribute<Address, String> street;
    public static volatile SingularAttribute<Address, Integer> id;

}