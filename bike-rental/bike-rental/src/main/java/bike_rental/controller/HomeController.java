package bike_rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Handles the root URL — when someone visits http://localhost:8080/
    @GetMapping("/")
    public String home() {
        return "home"; // loads templates/home.html
    }
}