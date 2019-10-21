package upyfx.xbroker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import upyfx.xbroker.model.Device;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends PagingAndSortingRepository<Device, String> {

    @Query("{'userId' : ?#{ principal?.id } }")
    Page<Device> findAllByUserId(Pageable page);

    @Query("{ 'userId' : ?#{ principal?.id }, id: ?0 }")
    Optional<Device> findById(String id);



}
