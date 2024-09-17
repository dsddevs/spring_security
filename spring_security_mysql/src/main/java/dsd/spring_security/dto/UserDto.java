package dsd.spring_security.dto;

import dsd.spring_security.model.RoleType;

public record UserDto(String username, String password, RoleType roleType){

}
