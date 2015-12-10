package gr.ihu.identity.rest.service;

import gr.ihu.identity.IUserManager;
import gr.ihu.identity.db.controller.IAddressServiceLocal;
import gr.ihu.identity.db.controller.IUserServiceLocal;
import gr.ihu.identity.db.controller.IUserInfoServiceLocal;
import gr.ihu.identity.db.controller.IUserPasswordServiceLocal;
import gr.ihu.rest.exception.BadRequestException;
import gr.ihu.rest.exception.ResourceNotFoundException;
import gr.ihu.rest.exception.NotAllowedException;
import gr.ihu.identity.db.model.Address;
import gr.ihu.identity.db.model.User;
import gr.ihu.identity.db.model.UserInfo;
import gr.ihu.identity.db.model.UserPassword;
import gr.ihu.identity.model.ModelEntityConverters;
import gr.ihu.identity.model.ModelUser;
import gr.ihu.identity.model.NewUser;
import gr.ihu.identity.security.ILoginManager;
import gr.ihu.identity.security.IPasswordProtector;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
@Path("/users")
public class UserServiceREST {

    @Inject
    private IUserServiceLocal userService;
    @Inject
    private IUserInfoServiceLocal userInfoservice;
    @Inject
    private IAddressServiceLocal addressService;
    @Inject
    private IUserPasswordServiceLocal userPasswordService;
    @Inject
    private IPasswordProtector passProtector;
    
    @Inject
    private IUserManager userManager;
    @Inject
    private ILoginManager loginManager;
    
    
    @GET
    @Produces({"application/xml", "application/json"})
    public List<ModelUser> findAll(@HeaderParam("Authorization") String token) {
        if (!loginManager.isValidToken(token)) {
            throw new NotAllowedException("Access to the specified resource has been forbidden");
        }
        Collection<User> users = userService.findAll();        
        List<ModelUser> modelUsers = new ArrayList<>(users.size());
        for(User u : users) {
            ModelUser muser = ModelEntityConverters.entityToModel(u);
            modelUsers.add(muser);
        }
        return modelUsers;
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public Response create(NewUser user, @Context UriInfo uriInfo, @HeaderParam("Authorization") String token) {
        if (!loginManager.isValidToken(token)) {
            throw new NotAllowedException("Access to the specified resource has been forbidden");
        }
        User euser = ModelEntityConverters.modelToEntity(user);
        userManager.create(euser, user.getPassword());      
        String newId = String.valueOf(euser.getId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
        //create a new instance of Response, using the Response Builder
        return Response.created(uri)
                .entity(ModelEntityConverters.entityToModel(euser))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({"application/xml", "application/json"})
    public Response edit(@PathParam("id") Integer id, NewUser muser, @HeaderParam("Authorization") String token) {
        if (!loginManager.isValidToken(token)) {
            throw new NotAllowedException("Access to the specifid resource has been forbidden");
        }       
        User userOld = userService.find(id);
        if (userOld == null) {
            throw new ResourceNotFoundException("The user with id: " + id + " is not found");
        }             
        
        User euser = ModelEntityConverters.modelToEntity(muser);
        euser.setId(id);
        
        // Update user fields
        if (euser.getEmail() == null) {
            euser.setEmail(userOld.getEmail());
        } else {
            userOld.setEmail(euser.getEmail());
        }
        if (euser.getUsername() == null) {
            euser.setUsername(userOld.getUsername());
        } else {
            userOld.setUsername(euser.getUsername());
        }
        
        // Update user info
        UserInfo oldInfo = userOld.getUserInfo();
        UserInfo newInfo = euser.getUserInfo();
        //If user info is null, it means the call should not update user info
        //BUT, firstName & lastName cannot be null
        if (newInfo == null) {
            throw new BadRequestException("The user info must not be null");
        } else {
            newInfo.setId(oldInfo.getId());
            if (newInfo.getFirstName() == null) {
                newInfo.setFirstName(oldInfo.getFirstName());
            } else {
                oldInfo.setFirstName(newInfo.getFirstName());
            }
            if (newInfo.getLastName() == null) {
                newInfo.setLastName(oldInfo.getLastName());
            } else {
                oldInfo.setLastName(newInfo.getLastName());
            }
            if ((Integer) newInfo.getGender() == null) {
                newInfo.setGender(oldInfo.getGender());
            } else {
                oldInfo.setGender(newInfo.getGender());
            }
            if (newInfo.getBirthDate() == null) {
                newInfo.setBirthDate(oldInfo.getBirthDate());
            } else {
                oldInfo.setBirthDate(newInfo.getBirthDate());
            }
        }//end else if
        
        //Update address 
        Address oldAddress = userOld.getUserInfo().getAddress();
        Address newAddress = euser.getUserInfo().getAddress();
        if (newAddress == null) {
            throw new BadRequestException("Address must not be null");
        } else {
            newAddress.setId(oldAddress.getId());
            if (newAddress.getStreet() == null) {
                newAddress.setStreet(oldAddress.getStreet());
            } else {
                oldAddress.setStreet(newAddress.getStreet());
            }
            if (newAddress.getCity() == null) {
                newAddress.setCity(oldAddress.getCity());
            } else {
                oldAddress.setCity(newAddress.getCity());
            }
            if (newAddress.getZip() == null) {
                newAddress.setZip(oldAddress.getZip());
            } else {
                oldAddress.setZip(newAddress.getZip());
            }
            if (newAddress.getCountry() == null) {
                newAddress.setCountry(oldAddress.getCountry());
            } else {
                oldAddress.setCountry(newAddress.getCountry());
            }
        }// end else if
        
        //Update password
        UserPassword userPasswordOld = userOld.getPassword();
        UserPassword userPasswordNew = new UserPassword();
        String password = muser.getPassword();
        if (password == null){  
            userPasswordNew.setPassword(userPasswordOld.getPassword());
            userPasswordNew.setId(userPasswordOld.getId());
            userPasswordNew.setValidUntil(userPasswordOld.getValidUntil());
            userPasswordNew.setUser(euser);
            euser.setPassword(userPasswordNew);
        } else {
            userPasswordNew.setPassword(passProtector.protect(password));
            userPasswordNew.setId(userPasswordOld.getId());
            Date now = new Date();
            long time = now.getTime() + (long)1000*60*60*720; 
            Date validUntil = new Date(time);
            userPasswordNew.setValidUntil(validUntil);
            userPasswordNew.setUser(euser);
            euser.setPassword(userPasswordNew);
        }
               
        addressService.edit(euser.getUserInfo().getAddress());
        userInfoservice.edit(euser.getUserInfo());
        userPasswordService.edit(userPasswordNew);
        userService.edit(euser);
        return Response.status(Response.Status.OK)
                .entity(muser)
                .build();
    }// end of edit() method
    

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") Integer id, @HeaderParam("Authorization") String token) {
        if (!loginManager.isValidToken(token)) {
            throw new NotAllowedException("Access to the specified resource has been forbidden");
        }
        User user = userService.find(id);
        if (user == null) {
            throw new ResourceNotFoundException("The user with id: " + id + " is not found");
        }
        userService.remove(user);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{id}")
    @Produces({"application/xml", "application/json"})
    public Response find(@PathParam("id") Integer id, @HeaderParam("Authorization") String token) {
        if (!loginManager.isValidToken(token)) {
            throw new NotAllowedException("Access to the specified resource has been forbidden");
        }       
        User user = userService.find(id);
        if (user == null) {
            throw new ResourceNotFoundException("The user with id: " + id + " is not found");
        }
        ModelUser muser = ModelEntityConverters.entityToModel(user);
        return Response.status(Response.Status.OK)
                .entity(muser)
                .build();
    }

    @GET
    @Path("{from}/{length}")
    @Produces({"application/xml", "application/json"})
    public List<ModelUser> findRange(@PathParam("from") Integer from, @PathParam("length") Integer length,
            @HeaderParam("Authorization") String token) {
        if (!loginManager.isValidToken(token)) {
            throw new NotAllowedException("Access to the specified resource has been forbidden");
        }       
        List<User> users = userService.findRange(from, length);
        List<ModelUser> modelUsers = new ArrayList<>(users.size());
        for(User u : users){
           ModelUser muser = ModelEntityConverters.entityToModel(u);
           modelUsers.add(muser);
        }
        return modelUsers;
    }
    

    @GET
    @Path("/count")
    @Produces("text/plain")
    public String countREST(@HeaderParam("Authorization") String token) {
        if (!loginManager.isValidToken(token)) {
            throw new NotAllowedException("Access to the specified resource has been forbidden");
        }
        int count = userService.count();
        return "The number of records are: " + String.valueOf(count);
    }

}//end of class
