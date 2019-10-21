package upyfx.xbroker.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import upyfx.xbroker.jsonView.View;
import java.util.Date;
import java.util.Map;

@Data
@Document
public class User  {

    @Id
    @JsonView(View.Summary.class)
    @Getter
    public String id;

    @JsonView(View.Summary.class)
    String fullName;

    @JsonView(View.Summary.class)
    String username;

    String password;

    String email;

    Boolean emailVerified;

    Date createdAt;

    Map<String, Object> options;

    String[] roles;

    boolean enabled = true;

    private AuthProvider provider;

    private String providerId;

    @JsonView(View.Summary.class)
    private String imageUrl;

    @Setter
    @JsonView(View.Summary.class)
    Boolean darkTheme;

    @Setter
    @JsonView(View.Summary.class)
    Boolean advanced;

}