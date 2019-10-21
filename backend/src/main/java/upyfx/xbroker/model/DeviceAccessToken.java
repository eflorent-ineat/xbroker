package upyfx.xbroker.model;


import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import upyfx.xbroker.jsonView.View;

import java.util.Date;


@Document
public class DeviceAccessToken {

    @Id
    @JsonView(View.Summary.class)
    private String id;

    @Indexed
    @JsonView(View.Summary.class)
    private String accessToken; //: { index: true, type: String, required: true, minlength: 32, default() { return randomstring.generate(32); } },

    @DBRef
    @Getter
    private Device device;

    @DBRef
    @Indexed
    private User user;

    private Boolean active = true;

    private Date createdAt;

    @JsonView(View.Summary.class)
    private Date lastSeen;

}
