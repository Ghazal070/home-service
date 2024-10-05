package application.serviceTest.BaseEntityService;


import application.entity.Duty;
import application.repository.BaseEntityRepository;
import application.service.impl.BaseEntityServiceImpl;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BaseEntityServiceImplTest {

    private Validator validator;
    @Mock
    private BaseEntityRepository<Duty, Integer> repository;

    @InjectMocks
    private BaseEntityServiceImpl<BaseEntityRepository<Duty, Integer>, Duty, Integer> underTest;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        underTest = new BaseEntityServiceImpl<>(validator, repository);
    }

    @Test
    public void testSaveValidEntity() {
        Duty duty = Duty.builder().id(100).title("WashDishes").build();
        //when(repository.save(duty)).thenReturn(duty);
        given(repository.save(duty)).willReturn(duty);

        Duty savedDuty = underTest.save(duty);

        assertEquals(duty, savedDuty);
        verify(repository).save(duty);
    }

    @Test
    public void testDontSaveNotValidEntity() {
        Duty duty = Duty.builder().id(100).title(null).build();

        assertThatThrownBy(() -> underTest.save(duty))
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessageContaining("Validation failed: Title must not be null");
    }

    @Test
    public void testUpdateValidEntity() {
        Duty duty = Duty.builder().id(100).title("Wash Dishes").basePrice(1_000).build();
        given(repository.save(duty)).willReturn(duty);
        assertEquals(duty, underTest.save(duty));
        verify(repository).save(duty);
    }

    @Test
    public void testDontUpdateNotValidEntity() {
        Duty duty = Duty.builder().id(100).basePrice(2_000).build();

        assertThatThrownBy(() -> underTest.save(duty))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Validation failed: Title must not be null");
    }

    @Test
    public void testDeleteByIdValidId() {
        given(repository.existsById(1)).willReturn(true);

        underTest.deleteById(1);
        verify(repository).deleteById(1);
    }

    @Test
    public void testDeleteByIdDontExist() {
        given(repository.existsById(1)).willReturn(false);

        assertThatThrownBy(() -> underTest.deleteById(1))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("This id does not exist");
    }
    @Test
    public void testExistsByIdNullId() {
        assertThatThrownBy(() -> underTest.existsById(null))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Id must not be null");
    }

    @Test
    public void testExistsByIdValidId() {
        given(repository.existsById(1)).willReturn(true);

        assertTrue(underTest.existsById(1));

        verify(repository).existsById(1);

    }

    @Test
    public void testExistsByIdNonExistingId() {
        given(repository.existsById(1)).willReturn(false);

        assertFalse(underTest.existsById(1));

        verify(repository).existsById(1);

    }

    @Test
    public void testFindByIdExistId() {
        Optional<Duty> duty = Optional.ofNullable(Duty.builder().id(100).title("Wash Dishes").basePrice(1_000).build());
        given(repository.findById(100)).willReturn(duty);
        Optional<Duty> underTestById = underTest.findById(100);

        assertTrue(duty.isPresent());
        assertEquals(underTestById, duty);

        verify(repository).findById(100);
    }

    @Test
    public void testFindByNotExistId() {
        Optional<Duty> duty = Optional.ofNullable(Duty.builder().id(100).title("Wash Dishes").basePrice(1_000).build());
        given(repository.findById(100)).willReturn(null);
        assertNull(underTest.findById(100));
        verify(repository).findById(100);
    }

    @Test
    public void testFindAll() {
        List<Duty> duties = new ArrayList<>();
        Duty dutyFirst = Duty.builder().id(100).title("Washing_1").build();
        Duty dutySecond = Duty.builder().id(101).title("Washing_2").build();
        duties.add(dutyFirst);
        duties.add(dutySecond);

        given(repository.findAll()).willReturn(duties);

        List<Duty> underTestAll = underTest.findAll();
        assertEquals(duties, underTestAll);
        assertEquals(2, underTestAll.size());
        assertTrue(underTestAll.contains(dutyFirst));
        assertTrue(underTestAll.contains(dutySecond));

        verify(repository).findAll();
    }

    @Test
    public void testValidateValidEntity() {
        Duty duty = Duty.builder().id(100).title("Washing_1").build();
        assertDoesNotThrow(() -> underTest.validate(duty));
    }
    @Test
    public void testValidateInvalidEntity() {
        Duty invalidDuty = Duty.builder().id(101).title(null).build();
        assertThatThrownBy(() -> underTest.validate(invalidDuty))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Title must not be null");
    }
}