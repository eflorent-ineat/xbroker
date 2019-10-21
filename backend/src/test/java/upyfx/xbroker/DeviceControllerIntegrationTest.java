package upyfx.xbroker;


import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import upyfx.xbroker.model.Device;
import upyfx.xbroker.model.DeviceConfiguration;
import upyfx.xbroker.model.User;
import upyfx.xbroker.repository.DeviceConfigurationRepository;
import upyfx.xbroker.repository.DeviceRepository;
import upyfx.xbroker.repository.UserRepository;
import upyfx.xbroker.util.TextUtil;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = XBrokerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("/api/v2/devices")
public class DeviceControllerIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceConfigurationRepository deviceConfigurationRepository;

    @Autowired
    public MockMvc mvc;

    private User user;

    @PostConstruct
    public void setUp() {
        Optional<User> u  = userRepository.findByEmail("user");
        if (!u.isPresent()) {
            user = new User();
            user.setUsername("user");
            user.setEmail("user");
            user.setFullName("Spring test");
            userRepository.save(user);
        } else {
            user = u.get();
        }

        System.out.println("setup OK");
    }

    @Test
    @WithUserDetails
    @DisplayName("GET /api/v2/devices")
    public void testGetUserDevices() throws Exception {
        Device device = new Device();
        device.setName("test");
        device.setUser(user);
        deviceRepository.save(device);
        List<Device> allDevices = Arrays.asList(device);
        mvc.perform(get("/api/v2/devices").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").isArray());
    }
/*
    @Test
    @WithUserDetails
    @DisplayName("PUT /api/v2/devices")
    public void testPutUserDevice() throws Exception {
        String name ="a dummy name";
        String jsonString = new JSONObject()
                .put("name", name)
                .put("uname", "uname")
                .put("sysname", "sysname")
                .put("unique_id","unique_id" )
        .toString();
        String slug = TextUtil.toSlug(name);

        mvc.perform(put("/api/v2/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(name))
                .andExpect(jsonPath("slug").value(slug))
                .andExpect(jsonPath("uname").value("uname"))
                .andExpect(jsonPath("sysname").value("sysname"))
                .andExpect(jsonPath("unique_id").value("unique_id"));
    }

    @Test
    @WithUserDetails
    @DisplayName("POST /api/v2/devices")
    public void testPostUserDevice() throws Exception {

        Device device = new Device();
        device.setName("test");
        device.setUser(user);
        DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
        HashMap m = new HashMap<String, Object>();
        m.put("test","test");
        deviceConfiguration.setData(m);
        deviceConfigurationRepository.save(deviceConfiguration);
        device.setConfiguration(deviceConfiguration);
        device=deviceRepository.save(device);

        String jsonString = new JSONObject()
                .put("name", "name2")
                .put("uname", "uname")
                .put("sysname", "sysname")
                .put("unique_id","unique_id" )
                .toString();

        mvc.perform(post("/api/v2/devices/" + device.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("name2"))
                .andExpect(jsonPath("slug").value("name2"))
                .andExpect(jsonPath("uname").value("uname"))
                .andExpect(jsonPath("sysname").value("sysname"))
                .andExpect(jsonPath("unique_id").value("unique_id"));
    }

*/

    @Test
    @WithUserDetails
    @DisplayName("GET /api/v2/devices/:id")
    public void testGetUserDevice() throws Exception {
        Device device = new Device();
        device.setName("test");
        device.setUser(user);
        device=deviceRepository.save(device);

        mvc.perform(get("/api/v2/devices/" + device.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(device.getName()));
    }

    @Test
    @WithUserDetails
    @DisplayName("PUT /api/v2/devices/:id/configuration")
    public void testPutUserDeviceConfiguration() throws Exception {
        Device device = new Device();
        device.setName("test");
        device.setUser(user);
        DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
        HashMap m = new HashMap<String, Object>();
        m.put("test","test");
        deviceConfiguration.setData(m);
        deviceConfigurationRepository.save(deviceConfiguration);
        device.setConfiguration(deviceConfiguration);
        device=deviceRepository.save(device);
        JSONObject d = new JSONObject().put("param1", "value1");
        String jsonString = new JSONObject()
                .put("data", d)
                .toString();

        mvc.perform(put("/api/v2/devices/" + device.getId() + "/configuration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(d.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("param1").value("value1"));
    }

    @Test
    @WithUserDetails
    @DisplayName("Get /api/v2/devices/:id/configuration")
    public void testGetUserDeviceConfiguration() throws Exception {
        Device device = new Device();
        device.setName("test");
        device.setUser(user);
        DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
        HashMap m = new HashMap<String, Object>();
        m.put("test","test");
        deviceConfiguration.setData(m);
        deviceConfigurationRepository.save(deviceConfiguration);
        device.setConfiguration(deviceConfiguration);
        device=deviceRepository.save(device);

        mvc.perform(get("/api/v2/devices/" + device.getId() + "/configuration")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("test").value("test"));
    }


    @Test
    @WithUserDetails
    @DisplayName("GET /api/v2/devices/:id/attributes")
    public void testGetUserDeviceAttributes() throws Exception {
        Device device = new Device();
        device.setName("test");
        device.setUser(user);
        DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
        HashMap m = new HashMap<String, Object>();
        m.put("test","test");
        device.setAttributes(m);
        device=deviceRepository.save(device);

        mvc.perform(get("/api/v2/devices/" + device.getId() + "/attributes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("test").value("test"));
    }

    @Test
    @WithUserDetails
    @DisplayName("POST /api/v2/devices/{id}/duplicate/{name}")
    public void testGetUserDuplicateDevice() throws Exception {
        Device device = new Device();
        device.setName("test");
        device.setUser(user);
        DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
        HashMap m = new HashMap<String, Object>();
        m.put("test","test");
        device.setAttributes(m);
        device=deviceRepository.save(device);

        String name = "new name";
        mvc.perform(post("/api/v2/devices/" + device.getId() + "/duplicate/" + name)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("new name"));
        // TODO complete test
    }


}
