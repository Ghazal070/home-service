package application.entity.users;


import application.entity.BaseEntity;
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



    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image_data")
    @NotNull(message = "Profile image cannot be null")
    @Size(max = 307200, message = "Profile image cannot exceed 300 KB")
    private Byte[] image;


    public void setImage(InputStream inputStream) {
//        if (!isJpgStream(inputStream)) {
//            throw new ValidationException("Input stream does not contain a valid JPG image");
//        }
        BufferedImage bImage;
        try {
            inputStream = new ByteArrayInputStream(inputStream.readAllBytes());
            bImage = ImageIO.read(inputStream);
            if (bImage == null) {
                throw new ValidationException("in getBytes method bImage is null");
            }
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
    private boolean isJpgStream(InputStream inputStream) {
            byte[] header = new byte[2];
        try {
            if (inputStream.read(header) != 2) {
                return false;
            }
            return header[0] == (byte) 0xFF && header[1] == (byte) 0xD8;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return id + "- " + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + profile + ", dateTimeSubmission=" + dateTimeSubmission;
    }
    //todoMe getter setter in interface for my self after project
}
