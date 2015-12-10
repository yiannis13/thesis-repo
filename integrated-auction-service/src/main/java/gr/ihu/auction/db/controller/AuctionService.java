package gr.ihu.auction.db.controller;

import gr.ihu.auction.db.model.Auction;
import gr.ihu.auction.db.model.AuctionBid;
import gr.ihu.db.controller.AbstractFacade;
import gr.ihu.db.controller.GenericRepository;
import gr.ihu.db.exception.EntityNotFoundException;
import gr.ihu.utils.checks.ArgsCheck;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author John
 */
@Stateless
public class AuctionService extends AbstractFacade<Auction> implements IAuctionServiceLocal {

    @PersistenceContext(unitName = "auction_pu")
    private EntityManager em;

    public AuctionService() {
        super(Auction.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
 
    
    @Override
    public AuctionBid addBid(Object auctionId, String username, double amount) {
        ArgsCheck.ensureNotNull(auctionId, "auctionId");
        ArgsCheck.ensureNotNull(username, "userId");
        ArgsCheck.ensureNotNegative(amount, "amount");

        AuctionBid bid = new AuctionBid();
        Auction auction = this.find(auctionId);
        if (auction == null) {
            throw new EntityNotFoundException("The auction with id " + auctionId + " not found.");
        }
        if (auction.getEndTime().before(new Date()) || amount < auction.getCurrentBid()
                || (amount - auction.getCurrentBid()) < auction.getMinimumBid()) {
            return null;
        }

        bid.setWhen(new Date());
        bid.setBidder(username);
        bid.setAmount(amount);
        bid.setAuction(auction);
        auction.setCurrentBid(amount);
        this.edit(auction);
        this.count();

        GenericRepository<AuctionBid> repo = new GenericRepository<>(em, AuctionBid.class);
        repo.create(bid);
        repo.count();
        return bid;
    }

    @Override
    public Collection<Auction> getAuctionsEndingAfter(Date date) {
        Collection<Auction> openAuctions = new ArrayList<>();

        List<Auction> allAuctions = this.findAll();
        for (Auction auction : allAuctions) {
            if (auction.getEndTime().after(date)) {
                openAuctions.add(auction);
            }
        }
        return openAuctions;
    }

}
