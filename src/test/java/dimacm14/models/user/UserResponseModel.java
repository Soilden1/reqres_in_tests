package dimacm14.models.user;

import lombok.Data;

@Data
public class UserResponseModel {
    UserDataResponseModel data;
    SupportResponseModel support;
}
