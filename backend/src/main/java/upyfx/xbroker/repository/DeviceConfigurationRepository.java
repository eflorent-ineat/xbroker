package upyfx.xbroker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import upyfx.xbroker.model.DeviceConfiguration;

@Repository
public interface DeviceConfigurationRepository extends MongoRepository<DeviceConfiguration, String> {

}
