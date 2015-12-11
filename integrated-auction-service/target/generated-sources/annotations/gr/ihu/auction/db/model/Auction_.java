package gr.ihu.auction.db.model;

import gr.ihu.auction.db.model.AuctionBid;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-11T17:49:17")
@StaticMetamodel(Auction.class)
public class Auction_ { 

    public static volatile SingularAttribute<Auction, String> owner;
    public static volatile SingularAttribute<Auction, Double> currentBid;
    public static volatile SingularAttribute<Auction, Double> minimumBid;
    public static volatile SingularAttribute<Auction, Double> initialPrice;
    public static volatile ListAttribute<Auction, AuctionBid> bids;
    public static volatile SingularAttribute<Auction, Date> startTime;
    public static volatile SingularAttribute<Auction, Integer> id;
    public static volatile SingularAttribute<Auction, Date> endTime;
    public static volatile SingularAttribute<Auction, String> state;
    public static volatile SingularAttribute<Auction, String> productDescription;

}