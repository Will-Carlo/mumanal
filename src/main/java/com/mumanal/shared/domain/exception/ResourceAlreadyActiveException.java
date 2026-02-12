package com.mumanal.shared.domain.exception;

public class ResourceAlreadyActiveException extends DomainException {
    public ResourceAlreadyActiveException(String resourceName, Object id) {
        super(String.format("%s with id '%s' is already active/enabled", resourceName, id));
    }
}
