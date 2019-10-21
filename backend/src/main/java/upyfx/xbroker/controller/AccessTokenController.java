package upyfx.xbroker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import upyfx.xbroker.model.DeviceAccessToken;
import upyfx.xbroker.repository.AccessTokenRepository;
import upyfx.xbroker.repository.DeviceRepository;

import java.util.Optional;

@RestController
public class AccessTokenController {

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    private DeviceRepository deviceRepository;


    @GetMapping("/api/v2/devices/{id}/credentials")
    @PreAuthorize("hasRole('USER')")
    public DeviceAccessToken getDeviceAccessToken(@PathVariable String deviceId) {
        Optional<DeviceAccessToken> token = accessTokenRepository.findByDeviceId(deviceId);
        // const application = sanitize(device.application, Applications.apiReadFields());
        // const project = sanitize(device.project, Applications.apiReadFields());
        // const accessTokens = await Credentials.find({ device });
        if (token.isPresent()) {
            return token.get();
        }
        return null;
    }


    // GET '/api/v2/devices/:id/credentials'
    // DELETE '/api/v2/devices/:id/credentials'
    // POST '/api/v2/devices/:id/credentials'

}