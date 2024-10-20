package application.serviceTest.AdminService;

import application.dto.DutyCreationDto;
import application.entity.Duty;
import application.entity.enumeration.ExpertStatus;
import application.entity.users.Expert;
import application.service.UserSpecification;
import application.service.impl.AdminServiceImpl;
import jakarta.validation.ValidationException;
import application.repository.AdminRepository;
import application.service.DutyService;
import application.service.ExpertService;
import application.service.PasswordEncodeService;
import application.util.AuthHolder;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    private PasswordEncodeService passwordEncodeService;
    @Mock
    private UserSpecification userSpecification;

    @InjectMocks
    private AdminServiceImpl underTest;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        underTest = new AdminServiceImpl(validator, repository, authHolder,
                passwordEncodeService, dutyService, expertService, userSpecification);
    }

    @Test
    public void testCreateDutyWithoutParentSuccessfully() {
        DutyCreationDto dutyCreationDto = DutyCreationDto.builder()
                .title("Wash Dishes").basePrice(1_000).build();
        given(dutyService.existsByTitle(dutyCreationDto.getTitle())).willReturn(false);
        given(dutyService.save(any(Duty.class))).willReturn(new Duty());

        underTest.createDuty(dutyCreationDto);

        verify(dutyService).existsByTitle(dutyCreationDto.getTitle());
        verify(dutyService).save(any(Duty.class));
    }

    @Test
    public void testCreateDutyWithoutParentDuplicateTitle() {
        DutyCreationDto dutyCreationDto = DutyCreationDto.builder()
                .title("Wash Dishes").basePrice(1_000).build();
        given(dutyService.existsByTitle(dutyCreationDto.getTitle())).willReturn(true);

        assertThatThrownBy(() -> underTest.createDuty(dutyCreationDto))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Title exists for this parent null.");

        verify(dutyService).existsByTitle(dutyCreationDto.getTitle());
    }

    @Test
    public void testCreateDutyWithParentSuccessfully() {
        DutyCreationDto dutyCreationDto = DutyCreationDto.builder().parentId(100)
                .title("Wash Dishes").basePrice(1_000).build();
        Optional<Duty> parent = Optional.of(Duty.builder().id(100).build());
        given(dutyService.findById(dutyCreationDto.getParentId())).willReturn(parent);
        given(dutyService.containByUniqField(dutyCreationDto.getTitle(), dutyCreationDto.getParentId())).willReturn(false);
        given(dutyService.save(any(Duty.class))).willReturn(new Duty());

        underTest.createDuty(dutyCreationDto);

        verify(dutyService).findById(dutyCreationDto.getParentId());
        verify(dutyService).containByUniqField(dutyCreationDto.getTitle(), dutyCreationDto.getParentId());
        verify(dutyService).save(any(Duty.class));
    }
    @Test
    public void testCreateDutyWithParentDuplicateTitle() {
        DutyCreationDto dutyCreationDto = DutyCreationDto.builder().parentId(100)
                .title("Wash Dishes").basePrice(1_000).build();
        Optional<Duty> parent = Optional.of(Duty.builder().id(100).build());
        given(dutyService.findById(dutyCreationDto.getParentId())).willReturn(parent);
        given(dutyService.containByUniqField(dutyCreationDto.getTitle(), dutyCreationDto.getParentId())).willReturn(true);

        assertThatThrownBy(() -> underTest.createDuty(dutyCreationDto))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Title for duty already exists for this parent duty.");

        verify(dutyService).findById(dutyCreationDto.getParentId());
        verify(dutyService).containByUniqField(dutyCreationDto.getTitle(), dutyCreationDto.getParentId());
    }

    @Test
    public void testCreateDutyWithParentNotFoundParent() {
        DutyCreationDto dutyCreationDto = DutyCreationDto.builder().parentId(100)
                .title("Wash Dishes").basePrice(1_000).build();
        given(dutyService.findById(dutyCreationDto.getParentId())).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.createDuty(dutyCreationDto))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("This parent duty does not exist.");

        verify(dutyService).findById(dutyCreationDto.getParentId());

    }


    @Test
    public void testUpdateExpertNewStatusExistExpert() {
        Optional<Expert> expert = Optional.ofNullable(Expert.builder().id(100).expertStatus(ExpertStatus.New).build());
        given(expertService.findById(100)).willReturn(expert);
        given(expertService.update(expert.get())).willReturn(expert.get());

        Boolean actual = underTest.updateExpertStatus(expert.get().getId());

        verify(expertService).findById(expert.get().getId());
        verify(expertService).update(expert.get());
        assertTrue(actual);
    }

    @Test
    public void testUpdateExpertNotNewStatusExistExpert() {
        Optional<Expert> expert = Optional.ofNullable(Expert.builder().id(100).expertStatus(ExpertStatus.AcceptWaiting).build());
        given(expertService.findById(100)).willReturn(expert);

        assertThatThrownBy(() -> underTest.updateExpertStatus(expert.get().getId()))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("ExpertStatus does not New");
        verify(expertService).findById(expert.get().getId());
    }

    @Test
    public void testUpdateExpertNotExistExpert() {
        Optional<Expert> expert = Optional.ofNullable(Expert.builder().id(100).expertStatus(ExpertStatus.New).build());
        given(expertService.findById(100)).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateExpertStatus(expert.get().getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");
        verify(expertService).findById(expert.get().getId());
    }


    @Test
    public void testAddDutyToExpertSuccessfully() {
        Optional<Expert> expert = Optional.ofNullable(
                Expert.builder().id(100).expertStatus(ExpertStatus.Accepted).duties(new HashSet<>()).build()
        );
        Duty duty = Duty.builder().id(101).title("Wash Dishes").selectable(true).build();
        given(expertService.findById(100)).willReturn(expert);
        given(dutyService.findById(101)).willReturn(Optional.ofNullable(duty));
        given(expertService.havePermissionExpertToServices(expert.get().getId()))
                .willReturn(true);
        given(expertService.update(expert.get())).willReturn(expert.get());

        Boolean addDutyToExpert = underTest.addDutyToExpert(expert.get().getId(), duty.getId());


        verify(expertService).findById(100);
        verify(dutyService).findById(101);
        verify(expertService).havePermissionExpertToServices(expert.get().getId());
        verify(expertService).update(expert.get());
        assertTrue(addDutyToExpert);

    }

    @Test
    public void testAddDutyToExpertNotFoundDutyOrExpert() {
        given(expertService.findById(100)).willReturn(Optional.empty());
        given(dutyService.findById(101)).willReturn(Optional.empty());

        assertThatThrownBy(()->underTest.addDutyToExpert(100,101))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Expert or duty is null");

        verify(expertService).findById(100);
        verify(dutyService).findById(101);
    }

    @Test
    public void testAddDutyToExpertNotSelectableDuty() {
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
    public void testAddDutyToExpertNotAcceptExpert() {
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
    public void testRemoveDutyFromExpertSuccessfully() {
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
    public void testRemoveDutyFromExpertNotFoundExpert() {
        Optional<Duty> duty = Optional.ofNullable(Duty.builder().id(101).title("Wash Dishes").selectable(true).build());
        given(expertService.findById(100)).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.removeDutyFromExpert(100, duty.get().getId()))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Expert not found with ID: " + 100);

        verify(expertService).findById(100);
        verify(dutyService, never()).findById(duty.get().getId());
    }

    @Test
    public void testRemoveDutyFromExpertNotFoundDuty() {
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
    public void testRemoveDutyFromExpertNotExistDutyInSetExpert() {
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