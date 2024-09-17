package dsd.spring_security.controller;

import dsd.spring_security.service.IUserDataService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class UserController {

    private IUserDataService userDataService;

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<String> getUser(@AuthenticationPrincipal UserDetails details) {
        String userDetails = userDataService.getUserDetails(details);
        return ResponseEntity.ok(userDetails);
    }
}