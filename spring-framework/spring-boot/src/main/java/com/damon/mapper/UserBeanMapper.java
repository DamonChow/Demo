package com.damon.mapper;

import com.damon.model.User;
import com.damon.model.UserDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-01-09 10:59
 */
@Mapper(componentModel = "spring")
public interface UserBeanMapper {

    @Mappings({@Mapping(source = "id", target = "userId")})
    UserDTO fromUser(User user);

    @InheritInverseConfiguration
    User toUser(UserDTO userDTO);
}
