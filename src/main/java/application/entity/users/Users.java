package application.entity.users;


import application.entity.BaseEntity;
import application.entity.Role;
import application.service.PasswordEncoder;
import application.service.impl.PasswordEncoderImpl;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public class Users extends BaseEntity<Integer> {


    @Column
    @Pattern(regexp = "^[a-zA-Z]{3,}$", message = "Name must contain only letters")
    private String firstName;

    @Column
    @Pattern(regexp = "^[a-zA-Z]{3,}$", message = "Name must contain only letters")
    private String lastName;

    @Embedded
    @Valid
    private Profile profile;

    @Column
    private LocalDateTime dateTimeSubmission;

    @Column
    @Builder.Default
    private Boolean isActive=true;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image_data")
    @NotNull(message = "Profile image cannot be null")
    @Size(max = 307200, message = "Profile image cannot exceed 300 KB")
    private Byte[] image;


    public void setImage(InputStream inputStream) {
        BufferedImage bImage;
        try {
            inputStream = new ByteArrayInputStream(inputStream.readAllBytes());
            bImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error reading the image file: " + e.getMessage(), e);
        }
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ImageIO.write(bImage, "jpg", bos);
            byte[] byteArray = bos.toByteArray();
            if (byteArray.length == 0) {
                throw new ValidationException("in getBytes method byteArray is empty");
            }
            this.image = new Byte[byteArray.length];
            for (int i = 0; i < byteArray.length; i++) {
                this.image[i] = byteArray[i];
            }
        } catch (IOException e) {
            throw new RuntimeException("Error in ImageIO write: " + e.getMessage(), e);
        }
    }

    @Override
    public String toString() {
        return id + "- " + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + profile + ", dateTimeSubmission=" + dateTimeSubmission;
    }

}
