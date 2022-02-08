package com.wordpress.babuwant2do.vehiclesfleetmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "vehicle")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Vehicle extends VehicleBase{
    @Id
    private String id;

    @Version
    @JsonIgnore
    @Field("version")
    private Long version;

}
