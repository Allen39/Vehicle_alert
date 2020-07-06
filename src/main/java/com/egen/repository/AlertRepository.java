package com.egen.repository;

import com.egen.entity.Alert;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AlertRepository extends CrudRepository<Alert, String> {

    Iterable<Alert> findByVin(String vin);

    Iterable<Alert> findByPriority(String priority);

    @Query("select a from Alert a where a.timestamp >= :creationDateTime")
    List<Alert> getAlertsInLastTwoHour(@Param("creationDateTime") Date creationDateTime);
}
