package application.repository;

import application.dto.DutyResponseChildren;
import application.dto.UpdateDuty;
import application.entity.Duty;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DutyRepository extends BaseEntityRepository<Duty, Integer> {

    @Query("update Duty d set "
            + "d.basePrice= case when :price is not null then :price else d.basePrice end, "
            + "d.description= case when :description is not null then :description else d.description end, "
            + "d.selectable= case when :selectable is not null then :selectable else d.selectable end "
            + "where d.id= :dutyId"
    )
    @Modifying
    @Transactional
    int updateDutyPriceOrDescriptionOrSelectable(
            @Param("dutyId") Integer dutyId,
            @Param("price") Integer price,
            @Param("description") String description,
            @Param("selectable") Boolean selectable);


    @Query("select d from Duty d order by case when d.parent.id is null then 1 else 2 end,d.parent.id")
    List<Duty> loadAllDutyWithChildren();

    @Query("select count(d)>0 from Duty d where d.title=:title and d.parent.id=:parentId ")
    Boolean containByUniqField(String title, Integer parentId);

//    @Query("select d from Duty d where d.id= :dutyId")
//    Optional<Duty> findById(Integer dutyId);


    Boolean existsByTitle(String dutyTitle);

    List<Duty> findAllBySelectableTrue();
}
