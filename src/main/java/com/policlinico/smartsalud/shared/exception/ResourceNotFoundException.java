// shared/exception/ResourceNotFoundException.java
package com.policlinico.smartsalud.shared.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}