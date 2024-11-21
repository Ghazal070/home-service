package application.exception;


import application.dto.ErrorResponseDto;
import jakarta.servlet.Filter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class ValidationControllerExceptionHandler {

   @ExceptionHandler(value ={ValidationControllerException.class})
   public ResponseEntity<ErrorResponseDto> getValidationControllerException(ValidationControllerException exception){
       ErrorResponseDto errorResponseDto = ErrorResponseDto.builder().message(exception.getMessage())
               .dateError(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
               .status(exception.getHttpStatus().name())
               .build();
       return new ResponseEntity<>(errorResponseDto,exception.getHttpStatus());
   }
//todo @ExceptionHandler(value ={ValidationException.class})
//todo @ExceptionHandler()
   @ExceptionHandler(value = {RuntimeException.class})
    public ModelAndView getRunTimeException(RuntimeException exception){
       ModelAndView view = new ModelAndView("runtime_exception");
       view.addObject("Cause", exception.getCause().getMessage());
       view.addObject("Date",LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
       view.addObject("Message",exception.getMessage());
       return view;
   }

   @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponseDto> getException(Exception exception){
       ErrorResponseDto errorResponseDto = ErrorResponseDto.builder().message(exception.getMessage())
               .dateError(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
               .status(String.valueOf(HttpStatus.BAD_REQUEST))
               .build();
       return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
   }

}
