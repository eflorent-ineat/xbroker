package upyfx.xbroker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import upyfx.xbroker.model.DeviceAccessToken;

import java.util.Optional;

@Repository
public interface AccessTokenRepository extends MongoRepository<DeviceAccessToken, String> {

    Optional<DeviceAccessToken> findByDeviceId(String id);

    Optional<DeviceAccessToken> findById(String id);


}
