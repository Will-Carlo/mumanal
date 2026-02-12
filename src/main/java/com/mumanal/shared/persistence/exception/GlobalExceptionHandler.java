package com.mumanal.shared.persistence.exception;

import com.mumanal.shared.domain.dto.ApiErrorResponse;
import com.mumanal.shared.domain.dto.ValidationError;
import com.mumanal.shared.domain.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import javax.naming.AuthenticationException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 404 Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse("resource_not_found", ex.getMessage()));
    }

    // 409 Conflict/Duplicate
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicate(ResourceAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse("resource_already_exists", ex.getMessage()));
    }

    // 400 Validations (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<ValidationError> details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest()
                .body(new ApiErrorResponse("validation_error", "Invalid input data", details));
    }

    // 400 Parameters (@RequestParam)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingParams(MissingServletRequestParameterException ex) {
        String paramName = ex.getParameterName();
        String paramType = ex.getParameterType();

        String message = String.format("The required parameter '%s' (type  %s) is not present in the request.", paramName, paramType);

        return ResponseEntity.badRequest()
                .body(new ApiErrorResponse("missing_parameter", message));
    }

    // 400 Format errors
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleJsonErrors(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiErrorResponse("malformed_json", "The JSON format is invalid. Check data types (e.g., booleans, dates)."));
    }

    // 409 Status conflicts
    @ExceptionHandler(ResourceAlreadyActiveException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceAlreadyActive(ResourceAlreadyActiveException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse("resource_already_active", ex.getMessage()));
    }

    // 404 Non-existent routes
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoResourceFound(NoResourceFoundException ex) {
        String message = "The requested path does not exist or the static resource cannot be found.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse("route_not_found", message));
    }

    // 405 Incorrect method
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ApiErrorResponse(
                        "method_not_allowed",
                        String.format("The '%s' method is not allowed for this path. Supported methods: %s",
                                ex.getMethod(),
                                ex.getSupportedHttpMethods())
                ));
    }

    // 401 Login (Incorrect credentials issued from the AuthController)
    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class, AuthenticationException.class})
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiErrorResponse(
                        "authentication_failed",
                        "Incorrect username or password"
                ));
    }

    // 403 (Forbidden) Roles/Authority
    @ExceptionHandler({AccessDeniedException.class, AuthorizationDeniedException.class})
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(Exception ex) {
        String message = "You do not have sufficient permissions to access this resource.";
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiErrorResponse("access_denied", message));
    }

    // Generic Business Rules (Bad Request)
    // Catch any custom exception that inherits from DomainException
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiErrorResponse> handleDomainError(DomainException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiErrorResponse("business_rule_violation", ex.getMessage()));
    }

    // 400 Bad Request (input/files)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequest(BadRequestException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiErrorResponse("bad_request", ex.getMessage()));
    }

    // 500 Storage Error (Cloudinary / IO)
    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ApiErrorResponse> handleFileStorage(FileStorageException ex) {
        // ex.printStackTrace() or Logger
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiErrorResponse("storage_error", "Could not upload file. Service unavailable or configuration error."));
    }

    // 413 Payload Too Large
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiErrorResponse> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(new ApiErrorResponse(
                        "file_too_large",
                        "The uploaded file exceeds the maximum allowed size (10MB)."
                ));
    }

    // 500 Unexpected Errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(Exception ex) {
        ex.printStackTrace(); // Use Logger in production
        return ResponseEntity.internalServerError()
                .body(new ApiErrorResponse("internal_server_error", "An unexpected error occurred"));
    }

    //
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "Database error: Data integrity violation.";

        if (ex.getCause() != null && ex.getCause().getCause() != null) {
            message += " Details: " + ex.getCause().getCause().getMessage();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse("data_integrity_violation", message));
    }
}
