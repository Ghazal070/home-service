package application.util;


import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class AuthHolder {
    public Integer tokenId=null;
    public String tokenName=null;

    public void reset(){
        tokenId = null;
        tokenName = null;
    }
}
