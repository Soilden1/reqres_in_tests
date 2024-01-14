package dimacm14.models.identification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentificationDataResponseModel {
    String token;
    String error;
}
