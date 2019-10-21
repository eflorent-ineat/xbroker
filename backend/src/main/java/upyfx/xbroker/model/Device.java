package upyfx.xbroker.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import upyfx.xbroker.jsonView.View;

import java.util.Date;
import java.util.Map;

@Document
public class Device implements Cloneable {

    @Id
    @Setter @Getter
    @JsonView(View.Summary.class)
    private String id;

    @DBRef
    @Indexed
    @Setter @Getter
    @JsonView(View.Summary.class)
    private User user;

    @Setter @Getter
    @JsonView(View.Summary.class)
    private String name;

    @Setter @Getter
    @JsonView(View.Summary.class)
    private String sysname;

    @Setter @Getter
    @JsonView(View.Summary.class)
    private String uname;

    @Setter @Getter
    @JsonView(View.Summary.class)
    private String slug;

    @Setter @Getter
    @JsonView(View.Summary.class)
    private String unique_id;

    @JsonView(View.Summary.class)
    private String[] tags;

    @JsonView(View.Summary.class)
    private Date lastSeen;

    @JsonView(View.Summary.class)
    private Date createdAt;

    @Setter @Getter
    private DeviceConfiguration configuration;

    @Setter
    @Getter
    private Map<String, Object> attributes;

//    attributes: Object,
//    location: Object,
//    shadow: Object,
//    application: { type: Schema.Types.ObjectId, ref: 'Applications' }, // store message, message retention override
//    project: { type: Schema.Types.ObjectId, ref: 'Projects' }, // store message, message retention override
//    store_messages: { type: Boolean, default: true },
//    certs_version: { type: Number, default: 0 }, // required
//    certificates: [{ type: Schema.Types.ObjectId, ref: 'Certificat

    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch(CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        return o;
    }

}
