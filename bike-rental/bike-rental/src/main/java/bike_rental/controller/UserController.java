package bike_rental.controller;

import bike_rental.model.RegularUser;
import bike_rental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// @Controller tells Spring Boot this class handles web requests
// @RequestMapping("/users") means ALL URLs in this class start with /users
// So @GetMapping("/list") becomes /users/list
//    @PostMapping("/register") becomes /users/register
// and so on

@Controller
@RequestMapping("/users")
public class UserController {

    // Spring automatically provides a UserService object here
    // UserController never creates UserService manually
    @Autowired
    private UserService userService;

    // ─────────────────────────────────────────────
    // READ: Show the user list page
    // URL: GET /users/list
    // Also handles search — if ?search=kamal is in the URL it filters results
    // @RequestParam(required=false) means the search parameter is optional
    // If no search is provided it is null and getAllUsers(null) returns everyone
    // Model is like a bag — you put things in it and Thymeleaf can read them in HTML
    // ─────────────────────────────────────────────

    @GetMapping("/list")
    public String listUsers(@RequestParam(required = false) String search, Model model) {
        List<RegularUser> users = userService.getAllUsers(search);
        model.addAttribute("users", users);   // Thymeleaf reads this as ${users}
        model.addAttribute("search", search); // Thymeleaf reads this as ${search}
        return "users/list";                  // loads templates/users/list.html
    }

    // ─────────────────────────────────────────────
    // CREATE: Show the empty registration form
    // URL: GET /users/register
    // Creates a blank RegularUser object and puts it in the model
    // Thymeleaf binds this empty object to th:object="${user}" in the form
    // Without this empty object Thymeleaf cannot bind the form fields
    // ─────────────────────────────────────────────

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new RegularUser()); // empty object for the form
        return "users/register";                        // loads templates/users/register.html
    }

    // ─────────────────────────────────────────────
    // CREATE: Process the submitted registration form
    // URL: POST /users/register
    // @ModelAttribute automatically takes all form fields and fills a RegularUser object
    // Spring matches form field names to RegularUser setter names automatically
    // For example: input name="name" → calls user.setName()
    //              input name="userId" → calls user.setUserId()
    // ─────────────────────────────────────────────

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegularUser user, Model model) {
        String result = userService.createUser(user);

        // If service returned "success" — redirect to list page
        // "redirect:" tells the browser to make a new GET request to /users/list
        if ("success".equals(result)) {
            return "redirect:/users/list";
        }

        // If service returned an error message — show the form again with the error
        // Put the user object back so the form re-fills with what they typed
        model.addAttribute("user", user);
        model.addAttribute("error", result); // Thymeleaf reads as ${error}
        return "users/register";
    }

    // ─────────────────────────────────────────────
    // UPDATE: Show the edit form pre-filled with existing user data
    // URL: GET /users/edit?id=U001
    // @RequestParam String id reads the ?id= from the URL
    // Finds the user with that ID and puts them in the model
    // Thymeleaf pre-fills the form fields with their current values
    // ─────────────────────────────────────────────

    @GetMapping("/edit")
    public String showEditForm(@RequestParam String id, Model model) {
        RegularUser user = userService.getUserById(id);

        // If user not found (maybe deleted already) — send back to list
        if (user == null) {
            return "redirect:/users/list";
        }

        model.addAttribute("user", user); // pre-fills the edit form
        return "users/edit";              // loads templates/users/edit.html
    }

    // ─────────────────────────────────────────────
    // UPDATE: Process the submitted edit form
    // URL: POST /users/edit
    // Works the same as registerUser but calls updateUser instead of createUser
    // The hidden userId field in the form ensures we know WHICH user to update
    // ─────────────────────────────────────────────

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute RegularUser user, Model model) {
        String result = userService.updateUser(user);

        if ("success".equals(result)) {
            return "redirect:/users/list";
        }

        model.addAttribute("user", user);
        model.addAttribute("error", result);
        return "users/edit";
    }

    // ─────────────────────────────────────────────
    // DELETE: Remove a user permanently
    // URL: POST /users/delete
    // Receives the user ID from the hidden form field in list.html
    // Calls deleteUser then redirects back to the list
    // Uses POST not GET because GET requests should never delete data
    // ─────────────────────────────────────────────

    @PostMapping("/delete")
    public String deleteUser(@RequestParam String id) {
        userService.deleteUser(id);
        return "redirect:/users/list"; // go back to list after deleting
    }
}