package entity.users;


import entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public class User extends BaseEntity<Integer> {

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Embedded
    private Profile profile;

    @Column
    private ZonedDateTime dateTimeSubmission;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private Byte[] image;


//    @SneakyThrows
//    public void loadImage(String imagePath) {
////        Path path = Paths.get(imagePath);
////        byte[] imageBytes = Files.readAllBytes(path);
////        Byte[] bytes = new Byte[imageBytes.length];
////        for (int i = 0; i < imageBytes.length; i++) {
////            this.image[i] = imageBytes[i];
////        }
//        BufferedImage image = ImageIO.read(new File(imagePath));
//        ByteArrayOutputStream outStreamObj = new ByteArrayOutputStream();
//        ImageIO.write(image, "jpg", outStreamObj);
//        byte[] byteArray = outStreamObj.toByteArray();
//
//    }
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
