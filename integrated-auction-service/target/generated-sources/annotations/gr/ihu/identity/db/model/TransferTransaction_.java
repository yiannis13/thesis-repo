package gr.ihu.identity.db.model;

import gr.ihu.identity.db.model.Account;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-11T17:49:16")
@StaticMetamodel(TransferTransaction.class)
public class TransferTransaction_ { 

    public static volatile SingularAttribute<TransferTransaction, Double> amount;
    public static volatile SingularAttribute<TransferTransaction, Account> sourceAccount;
    public static volatile SingularAttribute<TransferTransaction, Date> startTime;
    public static volatile SingularAttribute<TransferTransaction, Integer> id;
    public static volatile SingularAttribute<TransferTransaction, Date> endTime;
    public static volatile SingularAttribute<TransferTransaction, String> state;
    public static volatile SingularAttribute<TransferTransaction, Account> destinationAccount;

}