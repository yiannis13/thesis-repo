package gr.ihu.rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author John
 */

@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {

    @Override
    public Response toResponse(ResourceNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(),
                404, "http://localhost:8080/integrated-auction-service");
        return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorMessage)
                    .build();   
        
    }
    
}//end of class
