package com.mumanal.shared.domain.exception;

public class ResourceNotFoundException extends DomainException {
    public ResourceNotFoundException(String resourceName, String field, Object value) {
        super(String.format("%s with %s '%s' not found", resourceName, field, value));
    }
}
