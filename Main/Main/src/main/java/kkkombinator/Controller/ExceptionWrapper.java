package kkkombinator.Controller;


import kkkombinator.Controller.Exceptions.CatNotFoundException;
import kkkombinator.Controller.Exceptions.UserNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionWrapper extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CatNotFoundException.class, UserNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound (
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "smth not found",
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}

