package com.wordpress.babuwant2do.vehiclesfleetmanager.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashMap;

@Data
public abstract class VehicleBase {

    @NotEmpty
    @Size(min = 3, max = 10)
    @Field("name")
    protected String name;

    @NotEmpty
    @Indexed(unique=true)
    @Size(min = 3, max = 10)
    @Field("VIN")
    @EqualsAndHashCode.Include
    protected String vin;

    @NotEmpty
    @Indexed(unique=true)
    @Field("license_plate_number")
    @Size(min = 3, max = 10)
    @EqualsAndHashCode.Include
    protected String licensePlateNumber;

    @Field("properties")
    protected HashMap<String, String> properties;
}
