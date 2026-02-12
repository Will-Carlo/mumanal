package com.mumanal.modules.security.domain.exception;

import com.mumanal.shared.domain.exception.DomainException;

public class NotBeASameFatherException extends DomainException {
    public NotBeASameFatherException() {
        super("A menu cannot be its own parent");
    }
}
