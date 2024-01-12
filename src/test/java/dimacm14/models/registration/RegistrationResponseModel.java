package dimacm14.models.registration;

import lombok.Data;

@Data
public class RegistrationResponseModel {
    String id;
    String token;
    String error;
}
