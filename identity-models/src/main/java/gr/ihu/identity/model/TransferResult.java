package gr.ihu.identity.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author John
 */
@XmlRootElement
public class TransferResult {

    private final boolean error;
    private final String message;
    private final TransferTransactionModel model;

    private TransferResult(boolean error, String message) {
        this.error = error;
        this.message = message;
        this.model = null;
    }

    private TransferResult(TransferTransactionModel model) {
        this.error = false;
        this.message = null;
        this.model = model;
    }

    
    public static TransferResult errorResult(String message) {
        return new TransferResult(true, message);
    }

    public static TransferResult successResult(TransferTransactionModel model) {
        return new TransferResult(model);
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public TransferTransactionModel getModel() {
        return model;
    }
}
