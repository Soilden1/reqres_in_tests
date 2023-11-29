package dimacm14.models;

import lombok.Data;

@Data
public class RegistrationResponseModel {
    String id;
    String token;
    String error;
}
