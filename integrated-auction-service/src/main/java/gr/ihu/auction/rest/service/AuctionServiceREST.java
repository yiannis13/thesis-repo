package gr.ihu.auction.rest.service;

import gr.ihu.auction.IAuctionService;
import gr.ihu.auction.model.AuctionBidModel;
import gr.ihu.auction.model.AuctionModel;
import gr.ihu.identity.client.ILoginManager;
import gr.ihu.identity.client.LoginManager;
import gr.ihu.rest.exception.NotAllowedException;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

/**
 *
 * @author John
 */
@Stateless
@Path("/auction")
public class AuctionServiceREST {

    @Inject
    private IAuctionService auctionService;

    
    @POST
    @Path("/open")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
    public Response openAuction(AuctionModel auction, @HeaderParam("Authorization") String token) {
        ILoginManager clientLoginManager = checkValidity(token);
        if (!clientLoginManager.findUserByToken(token).getUsername().equalsIgnoreCase(auction.getOwner())) {
            throw new NotAllowedException("Credentials does not match");
        }
        AuctionModel auct = auctionService.openAuction(auction);
        return Response.status(Response.Status.OK)
                .entity(auct)
                .build();
    }

    private ILoginManager checkValidity(String token) {
        ILoginManager clientLoginManager = new LoginManager();
        boolean valid = clientLoginManager.isValidToken(token);
        if (!valid) {
            throw new NotAllowedException("Not allowed operation");
        }
        return clientLoginManager;
    }

    
    @POST
    @Path("/bid")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
    public Response bid(AuctionBidModel bid, @HeaderParam("Authorization") String token){       
        ILoginManager clientLoginManager = checkValidity(token);
        if (!clientLoginManager.findUserByToken(token).getUsername().equalsIgnoreCase(bid.getBidder())) {
            throw new NotAllowedException("Credentials does not match");
        }       
        AuctionBidModel bid1 = auctionService.bid(bid);
        return Response.status(Response.Status.OK)
                .entity(bid1)
                .build();
    }
    
    
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getAuctions(@HeaderParam("Authorization") String token) {
        checkValidity(token);       
        Collection<AuctionModel> auctions = auctionService.getAuctions();       
        
        return Response.status(Response.Status.OK)
                .entity(new GenericEntity<Collection<AuctionModel>>(auctions) {})
                .build();
    }
}


