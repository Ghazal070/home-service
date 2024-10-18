package application.dto;


import application.entity.BaseEntity;
import application.entity.Duty;
import application.entity.enumeration.ExpertStatus;
import application.entity.users.Profile;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UsersSearchResponse implements Serializable {

    private String firstName;

    private String lastName;

    private String email;

    private LocalDateTime dateTimeSubmission;

    private Boolean isActive=true;
    private ExpertStatus expertStatus;

    private Integer score;

    private Set<Duty> duties = new HashSet<>();

    @Override
    public String toString() {
        return "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' +
                ", dateTimeSubmission=" + dateTimeSubmission + ", isActive=" + isActive + ", expertStatus=" + expertStatus +
                ", score=" + score + ", duties=" + duties;
    }
}
