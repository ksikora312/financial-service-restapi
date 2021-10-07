package eu.kamilsikora.financial.api.mapper;

import eu.kamilsikora.financial.api.dto.auth.RegistrationDto;
import eu.kamilsikora.financial.api.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @AfterMapping
    protected void fillRestOfData(@MappingTarget User user) {
        user.setCreationDate(LocalDateTime.now());
        user.setEnabled(false);
    }

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(registrationDto.getPassword()))")
    public abstract User convertToEntity(RegistrationDto registrationDto);
}
