package gr.ihu.identity.model;

import gr.ihu.identity.db.model.Account;
import gr.ihu.identity.db.model.Address;
import gr.ihu.identity.db.model.PackageState;
import gr.ihu.identity.db.model.TransferTransaction;
import gr.ihu.identity.db.model.User;
import gr.ihu.identity.db.model.UserInfo;
import gr.ihu.utils.checks.ArgsCheck;

/**
 *
 * @author John
 */

public class ModelEntityConverters {
    
    public static User modelToEntity(ModelUser muser) {
        ArgsCheck.ensureNotNull(muser, "user");
        User euser = new User();
        euser.setId(muser.getId());
        euser.setUsername(muser.getUsername());
        euser.setEmail(muser.getEmail());
             
        UserInfo info = new UserInfo();
        info.setLastName(muser.getLastName());
        info.setFirstName(muser.getFirstName());
        info.setGender(muser.getGender());
        info.setBirthDate(muser.getBirthDate());
        euser.setUserInfo(info);
        info.setUser(euser);
       
        Address address = new Address();
        address.setCity(muser.getCity());
        address.setCountry(muser.getCountry());
        address.setStreet(muser.getStreet());
        address.setZip(muser.getZip());
        info.setAddress(address);
        address.setUserInfo(info);
        return euser;
    }
   
    public static ModelUser entityToModel(User user) {
        ArgsCheck.ensureNotNull(user, "user");
        ModelUser muser = new ModelUser();
        muser.setId(user.getId());
        muser.setUsername(user.getUsername());
        muser.setEmail(user.getEmail());       
        
        UserInfo info = user.getUserInfo();
        if(info == null) {
            return muser;
        }
        muser.setLastName(info.getLastName());
        muser.setFirstName(info.getFirstName());
        muser.setGender(info.getGender());
        muser.setBirthDate(info.getBirthDate());
        
        Address address = info.getAddress();
        if(address == null) {
            return muser;
        }
        muser.setCity(address.getCity());
        muser.setCountry(address.getCountry());
        muser.setStreet(address.getStreet());
        muser.setZip(address.getZip());
        return muser;
    }
    
    public static ModelPackage entityToModel(gr.ihu.identity.db.model.Package pack) {
        ModelPackage mp = new ModelPackage();
        mp.setPayload(pack.getPayload());
        mp.setReceiver(pack.getReceiver().getUsername());
        mp.setSender(pack.getSender().getUsername());
        mp.setState(entityToModel(pack.getLastState()));
        return mp;
    }
    
    public static ModelPackageState entityToModel(PackageState state) {
        ModelPackageState mps = new ModelPackageState();       
        mps.setWhen(state.getWhen());
        mps.setState(ModelPackageState.PackageStateType.fromInt(state.getState()));
        return mps;
    }
    
    public static ModelAccount entityToModel(Account account){
        ModelAccount mAccount = new ModelAccount();
        mAccount.setUsername(account.getOwner().getUsername());
        mAccount.setAmount(account.getAmount());
        return mAccount;
    }
    
    public static TransferTransactionModel entityToModel(TransferTransaction entity) {
       TransferTransactionModel model = new TransferTransactionModel();
       model.setId(entity.getId());
       model.setAmount(entity.getAmount());
       model.setDestinationAccount(entityToModel(entity.getDestinationAccount()));
       model.setSourceAccount(entityToModel(entity.getSourceAccount()));
       model.setStartTime(entity.getStartTime());
       model.setEndTime(entity.getEndTime());
       model.setState(entity.getState());
       return model;
    }
    
}
