package gr.ihu.identity.db.controller;

import gr.ihu.db.controller.AbstractFacade;
import gr.ihu.db.exception.EntityNotFoundException;
import gr.ihu.identity.db.model.PackageState;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author John
 */
@Stateless
public class PostalPackageService extends AbstractFacade<gr.ihu.identity.db.model.Package> implements IPostalPackageServiceLocal {

    @PersistenceContext(unitName = "auction_pu")
    private EntityManager em;

    public PostalPackageService() {
        super(gr.ihu.identity.db.model.Package.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public PackageState getLastState(Object trackId) {
        gr.ihu.identity.db.model.Package pack = getPackageOrThrowNotFound(trackId);
        return pack.getLastState();
    }

    private gr.ihu.identity.db.model.Package getPackageOrThrowNotFound(Object trackId) {
        gr.ihu.identity.db.model.Package pack = this.find(trackId);
        if (pack == null) {
            throw new EntityNotFoundException("Package with track id " + trackId + " not found");
        }
        return pack;
    }

    @Override
    public void setCurrentState(Object trackId, int state) {
        gr.ihu.identity.db.model.Package pack = getPackageOrThrowNotFound(trackId); // find the package first
        PackageState ps = new PackageState(); // create a state object 
        Date now = new Date();       
        // set the state 
        ps.setWhen(now);
        ps.setPack(pack);
        ps.setState(state);
        // Persist the state
        em.persist(ps);
        // edit the package object
        pack.getPackageStates().add(ps);
        edit(pack);
    }
}
