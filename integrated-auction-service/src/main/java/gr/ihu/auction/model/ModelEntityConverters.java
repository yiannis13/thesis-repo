package gr.ihu.auction.model;

import gr.ihu.auction.db.model.Auction;
import gr.ihu.auction.db.model.AuctionBid;

/**
 *
 * @author John
 */
public class ModelEntityConverters {
    
    public static Auction modelToEntity(AuctionModel mAuction) {
        Auction eAuction = new Auction();
        eAuction.setId(mAuction.getId());
        eAuction.setCurrentBid(mAuction.getCurrentBid());
        eAuction.setInitialPrice(mAuction.getInitialPrice());
        eAuction.setMinimumBid(mAuction.getMinimunBid());
        eAuction.setStartTime(mAuction.getStartTime());
        eAuction.setEndTime(mAuction.getEndTime());
        eAuction.setOwner(mAuction.getOwner());
        eAuction.setProductDescription(mAuction.getProductDescription());
        eAuction.setState(mAuction.getState());
        
        return eAuction;
    }
    
    public static AuctionModel entityToModel(Auction eAuction) {
        AuctionModel mAuction = new AuctionModel();
        mAuction.setId(eAuction.getId());
        mAuction.setCurrentBid(eAuction.getCurrentBid());
        mAuction.setInitialPrice(eAuction.getInitialPrice());
        mAuction.setMinimunBid(eAuction.getMinimumBid());
        mAuction.setStartTime(eAuction.getStartTime());
        mAuction.setEndTime(eAuction.getEndTime());
        mAuction.setOwner(eAuction.getOwner());
        mAuction.setProductDescription(eAuction.getProductDescription());
        mAuction.setState(eAuction.getState());
        
        return mAuction;
    }
    
    public static AuctionBidModel entityToModel(AuctionBid bid){
        AuctionBidModel bidModel = new AuctionBidModel();
        bidModel.setId(bid.getId());
        bidModel.setBidder(bid.getBidder());
        bidModel.setAmount(bid.getAmount());
        bidModel.setAuctionId(bid.getAuction().getId());
        bidModel.setWhen(bid.getWhen());
        
        return bidModel;
    }
    
}
