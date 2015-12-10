package gr.ihu.rest.exception;

/**
 *
 * @author John
 */

public class MethodNotAllowedException extends RuntimeException {
    
    private static final long serialVersionUID = 2925513408293315000L;
   
    public MethodNotAllowedException() {
    }

    public MethodNotAllowedException(String msg) {
        super(msg);
    }
    
}
