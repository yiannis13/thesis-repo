package gr.ihu.identity.db.model;

import gr.ihu.identity.db.model.PackageState;
import gr.ihu.identity.db.model.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-11T17:49:17")
@StaticMetamodel(Package.class)
public class Package_ { 

    public static volatile SingularAttribute<Package, User> receiver;
    public static volatile SingularAttribute<Package, User> sender;
    public static volatile SingularAttribute<Package, String> payload;
    public static volatile SingularAttribute<Package, Integer> id;
    public static volatile ListAttribute<Package, PackageState> packageStates;

}