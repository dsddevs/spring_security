package dsd.jwt.apicontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ApiController {

    @GetMapping("/home/main")
    public String getHomePage() {
        return "Home Page";
    }

    @GetMapping("/home/contact")
    public String getContactPage() {
        return "Contact Page";
    }

}
