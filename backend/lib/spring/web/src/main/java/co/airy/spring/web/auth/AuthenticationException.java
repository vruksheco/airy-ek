package backend.lib.spring.web.src.main.java.co.airy.spring.web.auth;

public class AuthenticationException extends Exception {

    public AuthenticationException(String error, Exception cause) {
        super(error, cause);
    }
}
