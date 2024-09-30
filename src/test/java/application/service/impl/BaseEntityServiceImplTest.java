package application.service.impl;

import application.entity.BaseEntity;
import application.entity.Duty;
import application.repository.BaseEntityRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.aspectj.lang.annotation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static application.entity.Order_.duty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BaseEntityServiceImplTest {

//    @Mock
    private Validator validator;
    @Mock
    private BaseEntityRepository<Duty, Integer> repository;

    @InjectMocks
    private BaseEntityServiceImpl<BaseEntityRepository<Duty, Integer>, Duty, Integer> underTest;

    @BeforeEach
    public void setUp(){
        validator= Validation.buildDefaultValidatorFactory().getValidator();
        underTest = new BaseEntityServiceImpl<>(validator,repository);
    }

    @Test
    void saveValidEntity() {
        Duty duty = Duty.builder().id(100).title("WashDishes").build();
        when(repository.save(duty)).thenReturn(duty);
        Duty savedDuty = underTest.save(duty);
        assertEquals(duty, savedDuty);
        verify(repository).save(duty);
    }
    @Test
    void dontSaveNotValidEntity(){
        Duty duty = Duty.builder().id(100).build();
        when(repository.save(duty)).thenReturn(duty);


    }
}