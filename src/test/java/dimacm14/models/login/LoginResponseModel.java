package dimacm14.models.login;

import lombok.Data;

@Data
public class LoginResponseModel {
    String token;
    String error;
}
