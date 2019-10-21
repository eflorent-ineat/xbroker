package upyfx.xbroker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import upyfx.xbroker.model.Device;
import upyfx.xbroker.model.DeviceConfiguration;
import upyfx.xbroker.model.User;
import upyfx.xbroker.repository.DeviceConfigurationRepository;
import upyfx.xbroker.repository.DeviceRepository;
import upyfx.xbroker.repository.UserRepository;
import upyfx.xbroker.security.CurrentUser;
import upyfx.xbroker.security.UserPrincipal;
import upyfx.xbroker.util.TextUtil;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceConfigurationRepository deviceConfigurationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/v2/devices")
    @PreAuthorize("hasRole('USER')")
    public Page<Device> getUserDevices(Pageable pageable) {
        return deviceRepository.findAllByUserId(pageable);

    }


    @PostMapping("/api/v2/devices")
    @PreAuthorize("hasRole('USER')")
    public Device createDevice(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody Device device) throws Exception {
       User user =  userRepository.findById(String.valueOf(userPrincipal.getId()))
                .orElseThrow(() -> new Exception("User not found" + userPrincipal.getId()));
       String name = device.getName();
       if (name == null) {
           device.setName("new device");
       }
        device.setSlug(TextUtil.toSlug(device.getName()));
        device.setUser(user);

        DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
        deviceConfigurationRepository.save(deviceConfiguration);
        device.setConfiguration(deviceConfiguration);

        deviceRepository.save(device);
        return device;
    }

    @GetMapping("/api/v2/devices/{id}")
    @PreAuthorize("hasRole('USER')")
    public Device getDevice(@PathVariable String id) {
        Optional<Device> device = deviceRepository.findById(id);
        // const application = sanitize(device.application, Applications.apiReadFields());
        // const project = sanitize(device.project, Applications.apiReadFields());
        // const accessTokens = await Credentials.find({ device });
        if (device.isPresent()) {
            return device.get();
        }
        return null;
    }

    @PutMapping("/api/v2/devices/{id}")
    @PreAuthorize("hasRole('USER')")
    public Device postDevice(@PathVariable String id, @Valid @RequestBody Device deviceDTO) {
        Optional<Device> result = deviceRepository.findById(id);
        if (result.isPresent()) {
            Device device = result.get();
            if (deviceDTO.getName() !=null) {
                device.setName(deviceDTO.getName());
                device.setSlug(TextUtil.toSlug(deviceDTO.getName()));
            }
            if (deviceDTO.getSysname() !=null) {
                device.setSysname(deviceDTO.getSysname());
            }
            if (deviceDTO.getSysname() !=null) {
                device.setUname(deviceDTO.getUname());
            }
            if (deviceDTO.getUnique_id() !=null) {
                device.setUnique_id(deviceDTO.getUnique_id());
            }
            return device;
        }
        return null;
    }


    @DeleteMapping("/api/v2/devices/{id}")
    @PreAuthorize("hasRole('USER')")
    public void deleteDevice(@CurrentUser UserPrincipal userPrincipal, @PathVariable String id) {
        Optional<Device> device = deviceRepository.findById(id);
        if (device.isPresent()) {
            deviceRepository.delete(device.get());
            return;
        }
    }


    @GetMapping("/api/v2/devices/{id}/configuration")
    @PreAuthorize("hasRole('USER')")
    public Map getDeviceConfiguration(@CurrentUser UserPrincipal userPrincipal, @PathVariable String id) throws Exception {
        Optional<Device> device = deviceRepository.findById(id);
        if (device.isPresent()) {
            return device.get().getConfiguration().getData();
        }
        throw new Exception("no config setup for device");
    }

    @PutMapping("/api/v2/devices/{id}/configuration")
    @PreAuthorize("hasRole('USER')")
    public Map putDeviceConfiguration(@PathVariable String id, @Valid @RequestBody HashMap configDTO) throws Exception {
        Optional<Device> device = deviceRepository.findById(id);
        if (device.isPresent()) {
            DeviceConfiguration config = device.get().getConfiguration();
            config.setData(configDTO);
            return config.getData();
        }
        throw new Exception("no config setup for device");
    }


    @GetMapping("/api/v2/devices/{id}/attributes")
    @PreAuthorize("hasRole('USER')")
    public Map getDeviceAttribute(@PathVariable String id) throws Exception {
        Optional<Device> device = deviceRepository.findById(id);
        if (device.isPresent()) {
            return device.get().getAttributes();
        }
        throw new Exception("no config setup for device");
    }

    @PostMapping("/api/v2/devices/{id}/duplicate/{name}")
    @PreAuthorize("hasRole('USER')")
    public Device duplicate(@PathVariable String id, @PathVariable String name) throws Exception {
        Optional<Device> opt = deviceRepository.findById(id);
        if (opt.isPresent()) {
            Device newDevice = (Device) opt.get().clone();
            newDevice.setId(null);
            newDevice.setUnique_id(null);
            newDevice.setName(name);
            DeviceConfiguration oldConfig = opt.get().getConfiguration();
            if (oldConfig != null) {
                newDevice.setConfiguration((DeviceConfiguration) oldConfig.clone());
            }
            newDevice.setSlug(TextUtil.toSlug(newDevice.getName()));
            deviceRepository.save(newDevice);
            return newDevice;
        }
        throw new Exception("attempt to duplicate non existing device");

    }

}

