package application.entity;

import application.entity.enumeration.PaymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
public class Invoice extends BaseEntity<Integer>{

    @Column
    @NotNull
    private Integer offerId;

    @Column(unique = true)
    @NotNull
    private Integer orderId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column
    @NotNull
    private Integer customerId;


    @Column
//    @CreatedDate
    private LocalDateTime createDate;

    @Column
//    @LastModifiedDate
    private LocalDateTime lastUpdate;

    @PrePersist
    void createDate(){
        this.createDate=LocalDateTime.now();
        this.lastUpdate=LocalDateTime.now();
    }

    @PreUpdate
    void updateDate(){
        this.lastUpdate=LocalDateTime.now();
    }

}
