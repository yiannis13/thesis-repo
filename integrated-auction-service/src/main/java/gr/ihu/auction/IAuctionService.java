package gr.ihu.auction;

import gr.ihu.auction.model.AuctionBidModel;
import gr.ihu.auction.model.AuctionModel;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author John
 */
@Local
public interface IAuctionService {

    AuctionModel openAuction(AuctionModel auction);

    AuctionBidModel bid(AuctionBidModel bid);
    
    Collection<AuctionModel> getAuctions();
}
