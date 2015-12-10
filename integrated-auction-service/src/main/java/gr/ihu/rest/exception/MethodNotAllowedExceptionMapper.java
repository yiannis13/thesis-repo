package gr.ihu.rest.exception;

import gr.ihu.rest.exception.MethodNotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author John
 */

@Provider
public class MethodNotAllowedExceptionMapper implements ExceptionMapper<MethodNotAllowedException> {

    @Override
    public Response toResponse(MethodNotAllowedException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(),
                405, "http://localhost:8080/integrated-auction-service");
        return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                .entity(errorMessage)
                .build();
    }
    
}
