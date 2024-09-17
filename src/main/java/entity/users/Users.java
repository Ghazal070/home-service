package entity.users;


import entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public class Users extends BaseEntity<Integer> {

    @Column
    @Pattern(regexp = "^[a-zA-Z]{3,}$",message = "Name must contain only letters")
    private String firstName;

    @Column
    @Pattern(regexp = "^[a-zA-Z]{3,}$",message = "Name must contain only letters")
    private String lastName;

    @Embedded
    @Valid
    private Profile profile;

    @Column
    private ZonedDateTime dateTimeSubmission;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private Byte[] image;


    public void getBytes(String pathImage) {
        BufferedImage bImage = null;
        try {
            bImage = ImageIO.read(new File(pathImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, "png", bos );
        } catch (IOException e) {
            throw new RuntimeException("error in imageIO write");
        }
        byte[] byteArray = bos.toByteArray();
        this.image = new Byte[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            this.image[i] = byteArray[i];
        }
    }

    @Override
    public String toString() {
        return id + "- " + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + profile + ", dateTimeSubmission=" + dateTimeSubmission;
    }
}
