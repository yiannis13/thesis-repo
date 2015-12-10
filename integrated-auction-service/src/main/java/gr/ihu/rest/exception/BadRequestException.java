package gr.ihu.rest.exception;

/**
 *
 * @author John
 */

public class BadRequestException extends RuntimeException {
    
    private static final long serialVersionUID = 6432716998183857205L;

    public BadRequestException() {
    }

    public BadRequestException(String msg) {
        super(msg);
    }
    
}//end of class
