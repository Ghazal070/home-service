package entity.users;


import entity.BaseEntity;
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
    @Column(name = "image_data")
    //@NotNull(message = "Profile image cannot be null")
    //@Size(max = 307200, message = "Profile image cannot exceed 300 KB")
    private Byte[] image;


    public void getBytes(String pathImage) {
        BufferedImage bImage = null;
        try {
            bImage = ImageIO.read(new File(pathImage));
            if(bImage==null)
                throw new ValidationException("in getBytes method bImage is null");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
//            String formatName = ImageIO.getImageReaders(bImage).next().getFormatName();
//            if (!formatName.equalsIgnoreCase("jpg"))
//                throw new ValidationException("format image must be jpg");
            ImageIO.write(bImage, "jpg", bos );
        } catch (IOException e) {
            throw new RuntimeException("error in imageIO write");
        }
        byte[] byteArray = bos.toByteArray();
        if(bos==null || bos.size()==0)
            throw new ValidationException("in getBytes method bos is null");
        this.image = new Byte[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            this.image[i] = byteArray[i];
        }
    }

    @Override
    public String toString() {
        return id + "- " + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + profile + ", dateTimeSubmission=" + dateTimeSubmission +" ,image=" + image;
    }
}
