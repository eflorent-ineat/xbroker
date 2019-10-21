package upyfx.xbroker.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import upyfx.xbroker.jsonView.View;

import java.util.Map;

public class DeviceConfiguration implements Cloneable {
    @Id
    @Setter
    @Getter
    @JsonView(View.Summary.class)
    private String id;



    @Setter
    @Getter
    @JsonView(View.Summary.class)
    private Map<String, Object> data;


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
