package gr.ihu.identity.db.model;

import gr.ihu.identity.db.model.Package;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-11T17:49:17")
@StaticMetamodel(PackageState.class)
public class PackageState_ { 

    public static volatile SingularAttribute<PackageState, Integer> id;
    public static volatile SingularAttribute<PackageState, Integer> state;
    public static volatile SingularAttribute<PackageState, Date> when;
    public static volatile SingularAttribute<PackageState, Package> pack;

}