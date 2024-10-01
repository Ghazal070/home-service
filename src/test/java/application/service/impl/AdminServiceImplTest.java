package application.service.impl;

import application.dto.DutyCreation;
import application.entity.Duty;
import application.entity.enumeration.ExpertStatus;
import application.entity.users.Expert;
import jakarta.validation.ValidationException;
import application.repository.AdminRepository;
import application.service.DutyService;
import application.service.ExpertService;
import application.service.PasswordEncode;
import application.util.AuthHolder;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    private DutyService dutyService;
    @Mock
    private ExpertService expertService;
    @Mock
    private AdminRepository repository;

    private Validator validator;
    @Mock
    private AuthHolder authHolder;
    @Mock
    private PasswordEncode passwordEncode;

    @InjectMocks
    private AdminServiceImpl underTest;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        underTest = new AdminServiceImpl(validator, repository, authHolder,
                passwordEncode, dutyService, expertService);
    }

    @Test
    void createDutyWithoutParentSuccessfully() {
        DutyCreation dutyCreation = DutyCreation.builder()
                .title("Wash Dishes").basePrice(1_000).build();
        given(dutyService.existsByTitle(dutyCreation.getTitle())).willReturn(false);
        given(dutyService.save(any(Duty.class))).willReturn(new Duty());

        Duty underTestDuty = underTest.createDuty(dutyCreation);

        verify(dutyService).existsByTitle(dutyCreation.getTitle());
        verify(dutyService).save(any(Duty.class));
        assertNotNull(underTestDuty);

    }

    @Test
    void createDutyWithoutParentDuplicateTitle() {
        DutyCreation dutyCreation = DutyCreation.builder()
                .title("Wash Dishes").basePrice(1_000).build();
        given(dutyService.existsByTitle(dutyCreation.getTitle())).willReturn(true);

        assertThatThrownBy(() -> underTest.createDuty(dutyCreation))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Title exists for this parent null.");

        verify(dutyService).existsByTitle(dutyCreation.getTitle());
    }

    @Test
    void createDutyWithParentSuccessfully() {
        DutyCreation dutyCreation = DutyCreation.builder().parentId(100)
                .title("Wash Dishes").basePrice(1_000).build();
        Optional<Duty> parent = Optional.of(Duty.builder().id(100).build());
        given(dutyService.findById(dutyCreation.getParentId())).willReturn(parent);
        given(dutyService.containByUniqField(dutyCreation.getTitle(), dutyCreation.getParentId())).willReturn(false);
        given(dutyService.save(any(Duty.class))).willReturn(new Duty());

        Duty underTestDuty = underTest.createDuty(dutyCreation);

        verify(dutyService).findById(dutyCreation.getParentId());
        verify(dutyService).containByUniqField(dutyCreation.getTitle(), dutyCreation.getParentId());
        verify(dutyService).save(any(Duty.class));
        assertNotNull(underTestDuty);
    }

    @Test
    void createDutyWithParentDuplicateTitle() {
        DutyCreation dutyCreation = DutyCreation.builder().parentId(100)
                .title("Wash Dishes").basePrice(1_000).build();
        Optional<Duty> parent = Optional.of(Duty.builder().id(100).build());
        given(dutyService.findById(dutyCreation.getParentId())).willReturn(parent);
        given(dutyService.containByUniqField(dutyCreation.getTitle(), dutyCreation.getParentId())).willReturn(true);

        assertThatThrownBy(() -> underTest.createDuty(dutyCreation))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Title for duty already exists for this parent duty.");

        verify(dutyService).findById(dutyCreation.getParentId());
        verify(dutyService).containByUniqField(dutyCreation.getTitle(), dutyCreation.getParentId());
    }

    @Test
    void createDutyWithParentNotFoundParent() {
        DutyCreation dutyCreation = DutyCreation.builder().parentId(100)
                .title("Wash Dishes").basePrice(1_000).build();
        given(dutyService.findById(dutyCreation.getParentId())).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.createDuty(dutyCreation))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("This parent duty does not exist.");

        verify(dutyService).findById(dutyCreation.getParentId());

    }


    @Test
    void updateExpertNewStatusExistExpert() {
        Optional<Expert> expert = Optional.ofNullable(Expert.builder().id(100).expertStatus(ExpertStatus.New).build());
        given(expertService.findById(100)).willReturn(expert);
        given(expertService.update(expert.get())).willReturn(expert.get());

        Boolean actual = underTest.updateExpertStatus(expert.get().getId());

        verify(expertService).findById(expert.get().getId());
        verify(expertService).update(expert.get());
        assertTrue(actual);
    }

    @Test
    void updateExpertNotNewStatusExistExpert() {
        Optional<Expert> expert = Optional.ofNullable(Expert.builder().id(100).expertStatus(ExpertStatus.AcceptWaiting).build());
        given(expertService.findById(100)).willReturn(expert);

        assertThatThrownBy(() -> underTest.updateExpertStatus(expert.get().getId()))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("ExpertStatus does not New");
        verify(expertService).findById(expert.get().getId());
    }

    @Test
    void updateExpertNotExistExpert() {
        Optional<Expert> expert = Optional.ofNullable(Expert.builder().id(100).expertStatus(ExpertStatus.New).build());
        given(expertService.findById(100)).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateExpertStatus(expert.get().getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");
        verify(expertService).findById(expert.get().getId());
    }


    @Test
    void addDutyToExpertSuccessfully() {
        Optional<Expert> expert = Optional.ofNullable(
                Expert.builder().id(100).expertStatus(ExpertStatus.Accepted).duties(new HashSet<>()).build()
        );
        Duty duty = Duty.builder().id(101).title("Wash Dishes").selectable(true).build();
        given(expertService.findById(100)).willReturn(expert);
        given(dutyService.findById(101)).willReturn(Optional.ofNullable(duty));
        given(expertService.havePermissionExpertToServices(expert.get()))
                .willReturn(true);
        given(expertService.update(expert.get())).willReturn(expert.get());

        Boolean addDutyToExpert = underTest.addDutyToExpert(expert.get().getId(), duty.getId());


        verify(expertService).findById(100);
        verify(dutyService).findById(101);
        verify(expertService).havePermissionExpertToServices(expert.get());
        verify(expertService).update(expert.get());
        assertTrue(addDutyToExpert);

    }

    @Test
    void addDutyToExpertNotFoundDutyOrExpert() {
        given(expertService.findById(100)).willReturn(Optional.empty());
        given(dutyService.findById(101)).willReturn(Optional.empty());

        assertThatThrownBy(()->underTest.addDutyToExpert(100,101))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Expert or duty is null");

        verify(expertService).findById(100);
        verify(dutyService).findById(101);
    }

    @Test
    void addDutyToExpertNotSelectableDuty() {
        Optional<Expert> expert = Optional.ofNullable(
                Expert.builder().id(100).expertStatus(ExpertStatus.Accepted).duties(new HashSet<>()).build()
        );
        Duty duty = Duty.builder().id(101).title("Wash Dishes").selectable(false).build();
        given(expertService.findById(100)).willReturn(expert);
        given(dutyService.findById(101)).willReturn(Optional.ofNullable(duty));

        assertThatThrownBy(()->underTest.addDutyToExpert(expert.get().getId(),duty.getId()))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Duty selectable is false");


        verify(expertService).findById(100);
        verify(dutyService).findById(101);
    }

    @Test
    void addDutyToExpertNotAcceptExpert() {
        Optional<Expert> expert = Optional.ofNullable(
                Expert.builder().id(100).expertStatus(ExpertStatus.AcceptWaiting).duties(new HashSet<>()).build()
        );
        Optional<Duty> duty = Optional.ofNullable(Duty.builder().id(101).title("Wash Dishes").selectable(true).build());
        given(expertService.findById(100)).willReturn(expert);
        given(dutyService.findById(101)).willReturn(duty);

        assertThatThrownBy(()->underTest.addDutyToExpert(100,101))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Expert isn't Accept status");

        verify(expertService).findById(100);
        verify(dutyService).findById(101);
    }



    @Test
    void removeDutyFromExpertSuccessfully() {
        Optional<Expert> expert = Optional.ofNullable(
                Expert.builder().id(100).expertStatus(ExpertStatus.Accepted).duties(new HashSet<>()).build()
        );
        Optional<Duty> duty = Optional.ofNullable(Duty.builder().id(101).title("Wash Dishes").selectable(true).build());
        given(expertService.findById(100)).willReturn(expert);
        given(dutyService.findById(101)).willReturn(duty);
        given(expertService.update(expert.get())).willReturn(expert.get());
        expert.get().getDuties().add(duty.get());

        Boolean removeDutyToExpert = underTest.removeDutyFromExpert(expert.get().getId(), duty.get().getId());


        verify(expertService).findById(100);
        verify(dutyService).findById(101);
        verify(expertService).update(expert.get());
        assertTrue(removeDutyToExpert);
    }

    @Test
    void removeDutyFromExpertNotFoundExpert() {
        Optional<Duty> duty = Optional.ofNullable(Duty.builder().id(101).title("Wash Dishes").selectable(true).build());
        given(expertService.findById(100)).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.removeDutyFromExpert(100, duty.get().getId()))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Expert not found with ID: " + 100);

        verify(expertService).findById(100);
        verify(dutyService, never()).findById(duty.get().getId());
    }

    @Test
    void removeDutyFromExpertNotFoundDuty() {
        Optional<Expert> expert = Optional.ofNullable(
                Expert.builder().id(100).expertStatus(ExpertStatus.Accepted).duties(new HashSet<>()).build()
        );
        given(expertService.findById(100)).willReturn(expert);
        given(dutyService.findById(101)).willReturn(Optional.empty());

        assertThatThrownBy(()->underTest.removeDutyFromExpert(expert.get().getId(),101))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Duty not found with ID: " + 101);

        verify(expertService).findById(100);
        verify(dutyService,never()).findById(expert.get().getId());
    }

    @Test
    void removeDutyFromExpertNotExistDutyInSetExpert() {
        Optional<Expert> expert = Optional.ofNullable(
                Expert.builder().id(100).expertStatus(ExpertStatus.Accepted).duties(new HashSet<>()).build()
        );
        Optional<Duty> duty = Optional.ofNullable(Duty.builder().id(101).title("Wash Dishes").selectable(true).build());
        given(expertService.findById(100)).willReturn(expert);
        given(dutyService.findById(101)).willReturn(duty);

        assertThatThrownBy(()->underTest.removeDutyFromExpert(100,101))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Duty set is empty for expert with ID: 100");

        verify(expertService).findById(100);
        verify(dutyService).findById(101);

    }
}