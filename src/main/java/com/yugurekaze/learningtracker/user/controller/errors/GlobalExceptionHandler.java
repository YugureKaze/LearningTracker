package com.yugurekaze.learningtracker.user.controller.errors;


import com.yugurekaze.learningtracker.user.exception.BusinessException;
import com.yugurekaze.learningtracker.user.exception.exceptionImpls.UserNotFoundException;
import com.yugurekaze.learningtracker.user.exception.exceptionImpls.WrongEmailException;
import com.yugurekaze.learningtracker.user.exception.exceptionImpls.WrongPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ProblemDetail> handleValidationException(
            Exception ex, Locale locale) {

        BindingResult result = ex instanceof BindException be
                ? be.getBindingResult()
                : ((MethodArgumentNotValidException) ex).getBindingResult();

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageSource.getMessage("errors.400.title", null, locale)
        );

        problemDetail.setProperty(
                "errors",
                result.getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList()
        );

        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ProblemDetail> handleBusinessException(
            BusinessException ex, Locale locale) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                ex.getStatus(),
                messageSource.getMessage(
                        ex.getMessageKey(),
                        ex.getArgs(),
                        locale
                )
        );

        return ResponseEntity.status(ex.getStatus()).body(problemDetail);
    }
}
