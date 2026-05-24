// shared/exception/BadCredentialsException.java
package com.policlinico.smartsalud.shared.exception;

public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException(String message) {
        super(message);
    }
}