package application.dto;


import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminReportRequestUserDto {

    private Integer userId;
    private String role;
    private LocalDateTime resisterDate;

    @Builder.Default
    private Integer countOfRequest=0;

    @Builder.Default
    private Integer countOfDoneOrder=0;

}