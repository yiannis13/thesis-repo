package gr.ihu.identity;

import gr.ihu.identity.db.controller.IPostalPackageServiceLocal;
import gr.ihu.identity.db.controller.IUserServiceLocal;
import gr.ihu.db.exception.EntityNotFoundException;
import gr.ihu.identity.db.model.PackageState;
import gr.ihu.identity.db.model.User;
import gr.ihu.identity.email.IEmailService;
import gr.ihu.identity.model.ModelEntityConverters;
import gr.ihu.identity.model.ModelPackage;
import gr.ihu.identity.model.ModelPackageState;
import gr.ihu.utils.checks.ArgsCheck;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;

/**
 *
 * @author John
 */
@Stateless
public class PostalService implements IPostalService {

    @Inject
    private IPostalPackageServiceLocal postalPackageSevice;
    @Inject
    private IUserServiceLocal userService;
    @Inject
    private IEmailService emailService;

    @Resource
    private ManagedExecutorService executorService;

    
    @Override
    public int createPackage(ModelPackage pack) {
        // 1 - check inputs
        // 2 - convert from model to db entity
        // 3 - store package entity
        // 4 - create (store) package State
        // 5 - send email to receiver and sender
        // 6 - send back the track id

        // 1
        ArgsCheck.ensureNotNull(pack, "package");

        String senderUsername = pack.getSender();
        ArgsCheck.ensureNotWhite(senderUsername, "senderUsername");

        String receiverUsername = pack.getReceiver();
        ArgsCheck.ensureNotWhite(receiverUsername, "receiverUsername");

        User sender = userService.findByUsername(senderUsername);
        if (sender == null) {
            throw new EntityNotFoundException("sender with username " + senderUsername + " not found");
        }
        User receiver = userService.findByUsername(receiverUsername);
        if (receiver == null) {
            throw new EntityNotFoundException("receiver with username " + receiverUsername + " not found");
        }
        
        //Put some default values in the ModelPackage ,if necessary
        pack = packageDefaultValues(pack);

        // 2
        gr.ihu.identity.db.model.Package epack = new gr.ihu.identity.db.model.Package();
        epack.setPayload(pack.getPayload());
        epack.setReceiver(receiver);
        epack.setSender(sender);

        // 3
        postalPackageSevice.create(epack);

        // 4
        postalPackageSevice.count(); // this may be too slow!! we do it to get track number (package id)
        postalPackageSevice.setCurrentState(epack.getId(), pack.getState().getState().getIntState());

        // 5
        final String subject = "IDS Postal Service Request";
        emailService.sendEmail(sender.getEmail(), subject, "Posting service requested from you ...");
        emailService.sendEmail(receiver.getEmail(), subject, "Posting service to you ...");

        //Start the postal job 
        PostingJob job = new PostingJob(postalPackageSevice, epack);
        executorService.submit(job);

        // 6
        return epack.getId();
    }

    private ModelPackage packageDefaultValues(ModelPackage pack) {
        ModelPackageState mps = new ModelPackageState();
        if (pack.getPayload() == null) {
            pack.setPayload("Unknown Payload");
        }
        mps.setWhen(new Date());
        mps.setState(ModelPackageState.PackageStateType.REQUEST_RECEIVED);
        pack.setState(mps);
        if (pack.getState() == null) {
            mps.setWhen(new Date());
            mps.setState(ModelPackageState.PackageStateType.REQUEST_RECEIVED);
            pack.setState(mps);
        }
        return pack;
    }

    @Override
    public ModelPackage findPackageByTrackNumber(int trackId) {
        gr.ihu.identity.db.model.Package epack = postalPackageSevice.find(trackId);
        if (epack == null) {
            return null;
        }
        return ModelEntityConverters.entityToModel(epack);
    }

    @Override
    public Collection<ModelPackageState> findStatesByPackageTrackNumber(int trackId) {
        gr.ihu.identity.db.model.Package epack = getPackageOrThrowNotFound(trackId);
        List<PackageState> states = epack.getPackageStates();

        List<ModelPackageState> modelStates = new ArrayList<>();
        for (PackageState ps : states) {
            modelStates.add(ModelEntityConverters.entityToModel(ps));
        }
        return modelStates;
    }

    private gr.ihu.identity.db.model.Package getPackageOrThrowNotFound(Object trackId) {
        gr.ihu.identity.db.model.Package pack = postalPackageSevice.find(trackId);
        if (pack == null) {
            throw new EntityNotFoundException("Package with track id " + trackId + " not found");
        }
        return pack;
    }
}
