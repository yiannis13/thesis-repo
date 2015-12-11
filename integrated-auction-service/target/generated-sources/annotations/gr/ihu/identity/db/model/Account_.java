package gr.ihu.identity.db.model;

import gr.ihu.identity.db.model.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-11T17:49:17")
@StaticMetamodel(Account.class)
public class Account_ { 

    public static volatile SingularAttribute<Account, User> owner;
    public static volatile SingularAttribute<Account, Double> amount;
    public static volatile SingularAttribute<Account, Integer> id;

}