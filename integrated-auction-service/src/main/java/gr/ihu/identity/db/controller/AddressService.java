package gr.ihu.identity.db.controller;

import gr.ihu.db.controller.AbstractFacade;
import gr.ihu.identity.db.model.Address;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author John
 */

@Stateless
public class AddressService extends AbstractFacade<Address> implements IAddressServiceLocal {
    
    @PersistenceContext(unitName = "auction_pu")
    private EntityManager em;

    public AddressService() {
        super(Address.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
  
}//end of Bean class
