package gr.ihu.rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author John
 */

@Provider
public class NotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException> {

    @Override
    public Response toResponse(NotAllowedException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(),
                401, "http://localhost:8080/integrated-auction-service");
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(errorMessage)
                .build();
    }
   
}
