package gr.ihu.auction.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author John
 */
@XmlRootElement
public class AuctionBidResult {
    
    private final boolean error;
    private final String message;
    private final AuctionBidModel model;
    
    private AuctionBidResult (boolean error, String message){
        this.error = error;
        this.message = message;
        this.model = null;
    }
    
    private AuctionBidResult(AuctionBidModel model){
        this.error = false;
        this.model = model;
        this.message = null;
    }
    
    
    public static AuctionBidResult errorResult(String message){
        return new AuctionBidResult(true, message);
    }
    
    public static AuctionBidResult successResult (AuctionBidModel model) {
        return new AuctionBidResult(model);
    }

    
    public boolean isError() {
        return error;
    }

    public String getErrorMsg() {
        return message;
    }

    public AuctionBidModel getModel() {
        return model;
    }
    
    
    
}
