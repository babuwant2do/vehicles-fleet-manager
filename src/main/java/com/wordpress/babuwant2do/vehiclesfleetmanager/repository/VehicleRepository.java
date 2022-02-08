package com.wordpress.babuwant2do.vehiclesfleetmanager.repository;

import com.wordpress.babuwant2do.vehiclesfleetmanager.domain.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {
    Optional<Vehicle> findByVin(String vin);

    @Query("{$or: [{'VIN': ?0 }, {'license_plate_number': ?1}]}")
    List<Vehicle> findByVinOrLicensePlateNumber(@Param("vin") String vin, @Param("licensePlateNumber") String licensePlateNumber);

    boolean existsByVin(String vin);

    void deleteByVin(String vin);
}
