package gr.ihu.rest.exception;

/**
 *
 * @author John
 */

public class ResourceNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = -1969737148699947947L;
    
    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
    
}//end of class
