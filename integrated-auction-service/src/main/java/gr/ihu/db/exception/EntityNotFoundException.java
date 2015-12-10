package gr.ihu.db.exception;

/**
 *
 * @author John
 */
public class EntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -1969737148699947947L;
    
    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String msg) {
        super(msg);
    } 
}
