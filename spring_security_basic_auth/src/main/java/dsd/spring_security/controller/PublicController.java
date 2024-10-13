package dsd.spring_security.controller;

import dsd.spring_security.dto.UserDto;
import dsd.spring_security.model.entity.RoleEntity;
import dsd.spring_security.service.IUserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/public")
public class PublicController {

    private final IUserDataService iUserDataService;

    @RequestMapping()
    public ResponseEntity<String> getPublic() {
        return ResponseEntity.status(HttpStatus.OK).body("Ok: Public page!");

    }

    @PostMapping("/account")
    public ResponseEntity<String> createAccount(@RequestBody UserDto userDto) {
        try {
            String username = userDto.username();
            String password = userDto.password();
            iUserDataService.createUserDetails(username, password, RoleEntity.setRole(userDto.roleType()));
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: User is not created! - user service - ${e.getMessage()}");
        }
    }
}