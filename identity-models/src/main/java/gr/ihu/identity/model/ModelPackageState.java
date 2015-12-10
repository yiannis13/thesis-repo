package gr.ihu.identity.model;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author John
 */

@XmlRootElement(name = "state")
public class ModelPackageState implements Serializable {
    
    private static final long serialVersionUID = -8393609841745834338L;
    
    private PackageStateType state;
    private Date when;
    

    public PackageStateType getState() {
        return state;
    }

    public void setState(PackageStateType state) {
        this.state = state;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }

    
    public static enum PackageStateType {
        REQUEST_RECEIVED(0),
        PACKAGE_RECEIVED(1),
        PENDING(2),
        COMPLETED(3);
        
        private final int intState;
        
        PackageStateType(int state) {
            this.intState = state;
        }
        
        public int getIntState() {
            return intState;
        }

        
        public static PackageStateType fromInt(int state) {
            for(PackageStateType pst : PackageStateType.values()) {
                if(pst.getIntState() == state) {
                    return pst;
                }
            }
            return null;
        }
    }//end of PackageStateType 
}
