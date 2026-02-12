package com.mumanal.shared.domain.exception;

public class ResourceAlreadyExistsException extends DomainException {
    public ResourceAlreadyExistsException(String resourceName, String field, Object value) {
        super(String.format("%s with %s '%s' already exists", resourceName, field, value));
    }
}
