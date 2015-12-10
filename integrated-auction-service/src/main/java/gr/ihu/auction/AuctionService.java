package gr.ihu.auction;

import gr.ihu.auction.db.controller.IAuctionServiceLocal;
import gr.ihu.auction.db.model.Auction;
import gr.ihu.auction.db.model.AuctionBid;
import gr.ihu.auction.model.AuctionBidModel;
import gr.ihu.auction.model.AuctionModel;
import gr.ihu.auction.model.ModelEntityConverters;
import gr.ihu.utils.checks.ArgsCheck;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author John
 */
@Stateless
public class AuctionService implements IAuctionService {

    @Inject
    private IAuctionServiceLocal auctionDbService;

    
    @Override
    public AuctionModel openAuction(AuctionModel mAuction) {
        checkAuctionModel(mAuction);
        //set default values
        mAuction.setCurrentBid(mAuction.getInitialPrice());
        Date now = new Date();
        mAuction.setStartTime(now);    
        mAuction.setState(AuctionModel.State.RUNNING.toString());

        auctionDbService.create(ModelEntityConverters.modelToEntity(mAuction));
        return mAuction;
    }

    private void checkAuctionModel(AuctionModel mAuction) {
        ArgsCheck.ensureNotNull(mAuction, "auction");
        ArgsCheck.ensureNotNegative(mAuction.getInitialPrice(), "Initial Price");
        ArgsCheck.ensureNotNegative(mAuction.getMinimunBid(), "minimium bid");
        Date now = new Date();
        Date date;
        if (mAuction.getEndTime() == null) {
            date = new Date(now.getTime() + (long)1000*60*60*48); //Auction ends after 2 days, as a default value
            mAuction.setEndTime(date);   
        }
        if (mAuction.getOwner() == null) {
            throw new IllegalArgumentException("Auction owner must not be null");
        }
    }

    @Override
    public AuctionBidModel bid(AuctionBidModel mBid) {
        //checks
        ArgsCheck.ensureNotNull(mBid, "bid");
        double amount = mBid.getAmount();
        ArgsCheck.ensureNotNegative(amount, "amount");
        String bidderUsername = mBid.getBidder();
        if (bidderUsername == null) {
            throw new IllegalArgumentException("Bidder must not be null");
        }
        
        Integer auctionId = mBid.getAuctionId();

        AuctionBid auctionBid = auctionDbService.addBid(auctionId, bidderUsername, amount);
        return ModelEntityConverters.entityToModel(auctionBid);
    }

    @Override
    public Collection<AuctionModel> getAuctions() {
        Date now = new Date();
        Collection<Auction> auctionsEndingAfter = auctionDbService.getAuctionsEndingAfter(now);
        Collection<AuctionModel> auctions = new ArrayList<>(auctionsEndingAfter.size());

        for (Auction auct : auctionsEndingAfter) {
            AuctionModel modelAuction = ModelEntityConverters.entityToModel(auct);
            auctions.add(modelAuction);
        }
        return auctions;
    }

}
