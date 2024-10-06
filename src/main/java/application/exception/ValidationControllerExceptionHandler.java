package application.exception;


import application.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

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

   @ExceptionHandler(value = {RuntimeException.class})
    public ModelAndView getRunTimeException(RuntimeException exception){
       ModelAndView view = new ModelAndView("runtime_exception");
       view.addObject("cause", exception.getCause().getMessage());
       view.addObject("date",LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
       view.addObject("message",exception.getMessage());
       return view;
   }

   @ExceptionHandler(value = {ValidationException.class})

}
