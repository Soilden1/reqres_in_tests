package dimacm14.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserListResponseModel {
    String page;
    @JsonProperty("per_page")
    String perPage;
    String total;
    @JsonProperty("total_pages")
    String totalPages;
    UserDataResponseModel[] data;
    SupportResponseModel support;
}
