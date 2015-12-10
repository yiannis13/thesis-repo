package gr.ihu.rest.exception;

/**
 *
 * @author John
 */

public class NotAllowedException extends RuntimeException {
    
    private static final long serialVersionUID = 9160708362352832146L;
  
    public NotAllowedException() {
    }
   
    public NotAllowedException(String msg) {
        super(msg);
    }
    
}
