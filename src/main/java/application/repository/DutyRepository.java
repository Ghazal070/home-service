package application.repository;

import application.dto.DutyResponseChildren;
import application.dto.UpdateDuty;
import application.entity.Duty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DutyRepository extends BaseEntityRepository<Duty,Integer>{

    @Query("update Duty d set "
            +"d.basePrice= case when :price is not null then :price else d.basePrice end, "
            +"d.description= case when :description is not null then :description else d.description end, "
            +"d.selectable= case when :selectable is not null then :selectable else d.selectable end "
            +"where d.id= :dutyId"
    )
    int updateDutyPriceOrDescriptionOrSelectable(Integer dutyId,Integer price,String description,Boolean selectable);

   @Query("select d from Duty d order by case when d.parent.id is null then 1 else 2 end,d.parent.id")
    List<DutyResponseChildren> loadAllDutyWithChildren();

    @Query("select count(d)>0 from Duty d where d.title=:title and d.parent.id=:parentId ")
    Boolean containByUniqField(String title, Integer parentId);
}
