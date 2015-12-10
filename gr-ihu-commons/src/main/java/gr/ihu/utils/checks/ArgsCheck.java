package gr.ihu.utils.checks;

import java.util.Collection;

/**
 *
 * @author John
 */

public class ArgsCheck {
 
    public static void ensureNotNull(Object val, String name) {
        if(val == null) {
            String msg = (name != null ? name : "value") + " must not be empty";
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static void ensureNotEmpty(String val, String name) {
        ensureNotNull(val, name);
        if(isEmpty(val)) {
            String msg = (name != null ? name : "value") + " must not be empty";
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static void ensureNotWhite(String val, String name) {
        ensureNotEmpty(val, name);
        if(isWhite(val)) {
            String msg = (name != null ? name : "value") + " must not be white";
            throw new IllegalArgumentException(msg);
        }
    }
   
    public static boolean isEmpty(String val) {
        return val == null || val.length() == 0;
    }
    
    public static boolean isWhite(String val) {
        if(val == null) {
            return true;
        }
        int len = val.length();
        if(len == 0){
            return true;
        }   
        boolean white = true;
        int i = 0;
        while(white && i < len) {
           white = Character.isSpaceChar(val.charAt(i++));
        }
        return white;
    }
    
    public static <T> void ensureNotEmpty(Collection<T> val, String name) {
        ensureNotNull(val, name);
        if(val.isEmpty()) {
            String msg = (name != null ? name : "value") + " must not be empty";
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static void ensureNotNegative(double number, String name){
        ensureNotNull(number, name);
        if (number < 0) {
            String msg = (name != null ? name : "value") + " must not be negative";
            throw new IllegalArgumentException(msg);
        }
    }
    
}
