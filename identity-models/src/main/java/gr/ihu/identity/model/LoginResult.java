package gr.ihu.identity.model;

import gr.ihu.utils.checks.ArgsCheck;

/**
 *
 * @author John
 */

public class LoginResult {
    private final boolean error;
    private final String token;
    private final String errorMsg;

    private LoginResult(boolean error, String token, String errorMsg) {
        if(!error) {
            ArgsCheck.ensureNotWhite(token, "token");
        }
        this.error = error;
        this.token = token;
        this.errorMsg = errorMsg;
    }

    public static LoginResult newErrorResult(String msg) {
        return new LoginResult(true, null, msg);
    }
    
    public static LoginResult newSuccessResult(String token) {
        return new LoginResult(false, token, null);
    }
    
    public boolean isError() {
        return error;
    }

    public String getToken() {
        return token;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
