package gr.ihu.identity.rest.service;

import gr.ihu.identity.model.ModelUser;
import gr.ihu.rest.exception.NotAllowedException;
import gr.ihu.identity.model.NewUser;
import gr.ihu.identity.security.ILoginManager;
import gr.ihu.identity.security.LoginResult;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author John
 */
@Stateless
@Path("/login")
public class LoginServiceREST {

    @Inject
    private ILoginManager loginManager;

    
    @POST
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
    public Response login(NewUser user) {
        String username = user.getUsername();
        String password = user.getPassword();
        LoginResult loginResult = loginManager.loginUser(username, password);
        if (loginResult.isError()) {
            throw new NotAllowedException(loginResult.getErrorMsg());
        }
        String token = loginResult.getToken();
        return Response.status(Response.Status.CREATED)
                .header("Authorization", token)
                .build();
    }

    @GET
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
    public Response isValidUserToken(@HeaderParam("Authorization") String token) {
        if (loginManager.isValidToken(token)) {
            return Response.ok().build();
        }
        throw new NotAllowedException("token not valid");
    }

    @GET
    @Path("/identity")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
    public Response getUserIdentity(@HeaderParam("Authorization") String token) {
        if (!loginManager.isValidToken(token)) {
            throw new NotAllowedException("token not valid");
        }
        ModelUser user = loginManager.findUserByToken(token);
        return Response.ok(user.getUsername()).build();
    }

}
