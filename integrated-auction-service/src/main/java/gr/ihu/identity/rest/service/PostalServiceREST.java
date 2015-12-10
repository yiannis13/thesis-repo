package gr.ihu.identity.rest.service;

import gr.ihu.identity.IBankService;
import gr.ihu.identity.IPostalService;
import gr.ihu.identity.model.ModelPackage;
import gr.ihu.identity.model.ModelPackageState;
import gr.ihu.rest.exception.NotAllowedException;
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

/**
 *
 * @author John
 */

@Stateless
@Path("/postal")
public class PostalServiceREST {

    @Inject
    private ILoginManager loginManager;
    @Inject
    private IPostalService postalService;
    
    
    @POST
    @Path("/send")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json", "text/plain"})
    public Response send(ModelPackage mpack, @Context UriInfo uriInfo, @HeaderParam("Authorization") String token) {
        if (!loginManager.isValidToken(token)) {
            throw new NotAllowedException("Not allowed operation");
        }       
        Integer trackNumber = postalService.createPackage(mpack);       
        URI uri = uriInfo.getAbsolutePathBuilder().path(trackNumber.toString()).build();      
        return Response.created(uri).build();
    }
    
    
    @GET
    @Path("/{id}/check")
    @Produces({"application/xml", "application/json"})
    public Response check(@PathParam("id") Integer trackId, @HeaderParam("Authorization") String token) {
        if (!loginManager.isValidToken(token)) {
            throw new NotAllowedException("Not allowed operation");
        }
        ModelPackage mpack = postalService.findPackageByTrackNumber(trackId);
        ModelPackageState state = mpack.getState();

        return Response.status(Response.Status.OK)
                        .entity(state)
                        .build();
    }

}
