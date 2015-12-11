package gr.ihu.identity.rest.service;

import gr.ihu.identity.IBankService;
import gr.ihu.identity.client.PostalService;
import gr.ihu.identity.client.BankService;
import gr.ihu.identity.model.TransferResult;
import gr.ihu.identity.db.model.Account;
import gr.ihu.identity.model.ModelAccount;
import gr.ihu.identity.model.ModelEntityConverters;
import gr.ihu.identity.model.PaymentOrder;
import gr.ihu.rest.exception.BadRequestException;
import gr.ihu.rest.exception.NotAllowedException;
import gr.ihu.rest.exception.ResourceNotFoundException;
import gr.ihu.identity.security.ILoginManager;
import java.net.URI;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import gr.ihu.identity.db.controller.IBankAccountServiceLocal;
import gr.ihu.identity.db.model.TransferTransaction;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author John
 */
@Stateless
@Path("/bank")
public class BankServiceREST {

    @Inject
    private ILoginManager loginManager;
    @Inject
    private IBankService bankService;
    @Inject IBankAccountServiceLocal bankAccountService;

    

    @POST
    @Path("/transfer")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
    public Response transferWithConfirm(PaymentOrder payment, @HeaderParam("Authorization") String token) {
        return transfer(payment, true, token);
    }

    @POST
    @Path("/transferWithoutConfirm")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
    public Response transfer(PaymentOrder payment, @HeaderParam("Authorization") String token) {
        return transfer(payment, false, token);
    }

    private Response transfer(PaymentOrder payment, boolean confirm, String token) {
        if (!loginManager.isValidToken(token)) {
            throw new NotAllowedException("Not allowed operation");
        }
        TransferResult result = bankService.transfer(payment, confirm);
        if (result.isError()) {
            throw new BadRequestException(result.getMessage());
        }
        return Response.status(Response.Status.OK)
                .entity(result.getModel())
                .build();
    }

    @GET
    @Path("{bankTransferId}/{packageId}/check")
    @Produces({"application/json", "application/xml", "text/plain"})
    public Response checkTransaction(@PathParam("bankTransferId") Integer transferId,
            @PathParam("packageId") Integer packageId,
            @HeaderParam("Authorization") String token) {
        if (!loginManager.isValidToken(token)) {
            throw new NotAllowedException("Not allowed operation");
        }
        
        BankService clientBank; 
        PostalService clientPostal ;
        try {
            clientBank = new BankService();
            clientPostal = new PostalService();
            clientBank.confirmTransfer(transferId);
            boolean packageDelivered = clientPostal.isPackageDelivered(packageId);
            if (packageDelivered) {
                clientBank.confirmTransfer(transferId);
            }                      
        } catch (URISyntaxException ex) {
            Logger.getLogger(BankServiceREST.class.getName()).log(Level.SEVERE, null, ex);
        }
   
        return Response.ok().build();
    }

    //this service will be used by Auction Server, internally as a REST client
    @GET
    @Path("/{id}/confirm")
    @Produces({"application/xml", "application/json"})
    public Response confirmTransefer(@PathParam("id") Integer transferId, @HeaderParam("Authorization") String token) {
        
        TransferTransaction transaction = bankAccountService.findTransaction(transferId);
        if (transaction.getState().equalsIgnoreCase("COMPLETED")) {
            return Response.notModified("Transfer is already COMPLETED").build();
        }
        bankService.confirmTransfer(transferId);       
        return Response.ok().build();
    }

    @GET
    @Path("{username}/checkAccount")
    @Produces({"application/json", "application/xml", "text/plain"})
    public Response check(@PathParam("username") String username, @HeaderParam("Authorization") String token) {
        if (!loginManager.isValidToken(token)) {
            throw new NotAllowedException("Not allowed operation");
        }
        Account account = bankService.check(username);
        if (account == null) {
            throw new ResourceNotFoundException("Account not Found");
        }
        return Response.status(Response.Status.OK)
                .entity(ModelEntityConverters.entityToModel(account))
                .build();
    }

    @POST
    @Path("/create")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
    public Response createAccount(ModelAccount account, @Context UriInfo uriInfo,
            @HeaderParam("Authorization") String token) {
        if (!loginManager.isValidToken(token)) {
            throw new NotAllowedException("Not allowed operation");
        }
        Integer accountId = bankService.createAccount(account);
        URI uri = uriInfo.getAbsolutePathBuilder().path(accountId.toString()).build();
        return Response.created(uri).build();
    }

    @POST
    @Path("/deposit")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
    public Response deposit(ModelAccount account, @HeaderParam("Authorization") String token) {
        if (!loginManager.isValidToken(token)) {
            throw new NotAllowedException("Not allowed operation");
        }
        bankService.deposit(account);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @POST
    @Path("/withdraw")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
    public Response withdraw(ModelAccount account, @HeaderParam("Authorization") String token) {
        if (!loginManager.isValidToken(token)) {
            throw new NotAllowedException("Not allowed operation");
        }
        bankService.withdraw(account);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
