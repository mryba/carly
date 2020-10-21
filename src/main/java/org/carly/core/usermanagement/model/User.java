package org.carly.core.usermanagement.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.carly.core.shared.security.model.CarlyGrantedAuthority;
import org.carly.core.vehiclemanagement.model.Car;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Document(collection = "users")
public class User {
    private ObjectId id;
    private List<CarlyGrantedAuthority> role;
    private ObjectId companyId;
    private String code;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Address address;
    private List<Address> addressHistory;
    private List<Car> cars;
    private Gender gender;
    private String password;
    private LocalDate createdAt;
    private boolean enabled;
}