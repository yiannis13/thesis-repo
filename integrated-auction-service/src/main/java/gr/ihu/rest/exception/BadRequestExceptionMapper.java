package gr.ihu.rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author John
 */

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException>{

    @Override
    public Response toResponse(BadRequestException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(),
                400, "http://localhost:8080/integrated-auction-service");
        return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
    }
    
}//end of class
