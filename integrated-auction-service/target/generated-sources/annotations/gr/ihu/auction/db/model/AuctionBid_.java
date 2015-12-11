package gr.ihu.auction.db.model;

import gr.ihu.auction.db.model.Auction;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-11T17:49:17")
@StaticMetamodel(AuctionBid.class)
public class AuctionBid_ { 

    public static volatile SingularAttribute<AuctionBid, Double> amount;
    public static volatile SingularAttribute<AuctionBid, String> bidder;
    public static volatile SingularAttribute<AuctionBid, Integer> id;
    public static volatile SingularAttribute<AuctionBid, Date> when;
    public static volatile SingularAttribute<AuctionBid, Auction> auction;

}