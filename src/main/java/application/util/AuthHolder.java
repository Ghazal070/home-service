package application.util;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AuthHolder {
    public Integer tokenId=null;
    public String tokenName=null;

    public void reset(){
        tokenId = null;
        tokenName = null;
    }
}
