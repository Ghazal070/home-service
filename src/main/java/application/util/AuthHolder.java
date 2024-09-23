package application.util;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthHolder {
    public Integer tokenId=null;
    public String tokenName=null;

    public void reset(){
        tokenId = null;
        tokenName = null;
    }
}
