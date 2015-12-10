package gr.ihu.auction.db.controller;

import gr.ihu.auction.db.model.Auction;
import gr.ihu.auction.db.model.AuctionBid;
import gr.ihu.db.controller.IDbService;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author John
 */
@Local
public interface IAuctionServiceLocal extends IDbService<Auction>{
    
   AuctionBid addBid(Object auctionId, String username, double amount);
   
   Collection<Auction> getAuctionsEndingAfter(Date date);
}
