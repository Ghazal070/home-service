package application.serviceTest.DutyService;

import application.dto.DutyResponseChildrenDto;
import application.dto.UpdateDutyDto;
import application.entity.Duty;
import application.repository.DutyRepository;
import application.service.impl.DutyServiceImpl;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DutyServiceImplTest {

    @Mock
    private DutyRepository repository;
    private Validator validator;

    @InjectMocks
    private DutyServiceImpl underTest;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.underTest = new DutyServiceImpl(validator, repository);
    }

    @Test
    public void testUpdateDutyPriceOrDescriptionSuccessFully() {
        UpdateDutyDto updateDutyDto = UpdateDutyDto.builder().dutyId(100).price(1_000).build();
        Duty duty = Duty.builder().id(100).build();
        given(repository.findById(updateDutyDto.getDutyId())).willReturn(Optional.of(duty));
        given(repository.updateDutyPriceOrDescriptionOrSelectable(
                        updateDutyDto.getDutyId(), updateDutyDto.getPrice()
                        , updateDutyDto.getDescription(), updateDutyDto.getSelectable()
                )
        ).willReturn(1);

        Boolean actual = underTest.updateDutyPriceOrDescription(updateDutyDto);

        assertTrue(actual);

    }

    @Test
    public void testUpdateDutyPriceOrDescriptionUpdateDutyIsNull() {
        UpdateDutyDto updateDutyDto = null;

        assertThatThrownBy(() -> underTest.updateDutyPriceOrDescription(updateDutyDto))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Update duty is null");
    }

    @Test
    public void testUpdateDutyPriceOrDescriptionNotFoundDuty() {
        UpdateDutyDto updateDutyDto = UpdateDutyDto.builder().dutyId(100).price(1_000).build();
        given(repository.findById(updateDutyDto.getDutyId())).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateDutyPriceOrDescription(updateDutyDto))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Duty with ID " + updateDutyDto.getDutyId() + " not found");
    }

    @Test
    public void testLoadAllDutyWithChildrenSuccessFully() {
        Duty parentDuty = Duty.builder().id(100).title("parent").parent(null).build();
        Duty childDuty = Duty.builder().id(101).parent(parentDuty).title("child").build();
        List<Duty> duties = List.of(parentDuty, childDuty);
        given(repository.loadAllDutyWithChildren()).willReturn(duties);

        List<DutyResponseChildrenDto> actual = underTest.loadAllDutyWithChildren();

        assertThat(actual).hasSize(1);
        assertThat(actual.get(0)).isNotNull();
        assertThat(actual.get(0).getId()).isEqualTo(100);
        assertThat(actual.get(0).getTitle()).isEqualTo("parent");
        assertThat(actual.get(0).getParentTitle()).isEqualTo(null);
        assertThat(actual.get(0)).hasFieldOrProperty("subDuty");
        assertThat(actual.get(0).getSubDuty()).hasSize(1);
        assertThat(actual.get(0).getSubDuty()).anySatisfy(child -> {
                    assertThat(child.getId()).isEqualTo(101);
                    assertThat(child.getTitle()).isEqualTo("child");
                }
        );
    }

    @Test
    public void testLoadAllDutyWithChildrenFailed() {
        List<Duty> duties = new ArrayList<>();
        given(repository.loadAllDutyWithChildren()).willReturn(duties);

        List<DutyResponseChildrenDto> actual = underTest.loadAllDutyWithChildren();

        assertThat(actual).isEmpty();
    }
    @Test
    public void testContainByUniqField() {
        String title = "Wash Dishes";
        Integer parentId =100;
        given(repository.containByUniqField(title,parentId)).willReturn(true);

        Boolean actual = underTest.containByUniqField(title, parentId);

        assertTrue(actual);
    }

    @Test
    public void testNotContainByUniqField() {
        String title = "Wash Dishes";
        Integer parentId =100;
        given(repository.containByUniqField(title,parentId)).willReturn(false);

        Boolean actual = underTest.containByUniqField(title, parentId);

        assertFalse(actual);
    }

    @Test
    public void testExistsByTitle() {
        String title = "Wash Dishes";
        given(repository.existsByTitle(title)).willReturn(true);

        Boolean actual = underTest.existsByTitle(title);

        assertTrue(actual);
    }

    @Test
    public void testNotExistsByTitle() {
        String title = "Wash Dishes";
        given(repository.existsByTitle(title)).willReturn(false);

        Boolean actual = underTest.existsByTitle(title);

        assertFalse(actual);
    }

    @Test
    public void testGetSelectableDutiesTrueSuccessfully() {
        Duty parentDuty = Duty.builder().id(100).title("parent").selectable(false).build();
        Duty childDuty = Duty.builder().id(101).parent(parentDuty).selectable(true).build();
        List<Duty> duties = List.of(childDuty);
        given(repository.findAllBySelectableTrue()).willReturn(duties);

        List<Duty> actual = underTest.getSelectableDuties();

        assertNotNull(actual);
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0)).isNotNull();
        assertThat(actual.get(0).getSelectable()).isTrue();
        assertThat(actual.get(0).getId()).isEqualTo(101);
    }

    @Test
    public void testGetSelectableDutiesTrueFailed() {
        List<Duty> duties = new ArrayList<>();
        given(repository.findAllBySelectableTrue()).willReturn(duties);

        List<Duty> actual = underTest.getSelectableDuties();

        assertThat(actual).isEmpty();
    }
}