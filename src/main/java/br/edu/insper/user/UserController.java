package br.edu.insper.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/user", params = "email")
    public User getUserByEmail(@RequestParam(name = "email") String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping(value = "/user", params = "authId")
    public User getUserByAuthId(@RequestParam(name = "authId") String authId) {
        return userService.getUserByAuthId(authId);
    }

    @PostMapping("/user")
    public User saveUser(@AuthenticationPrincipal Jwt jwt) {
        User user = new User();
        String authId = jwt.getClaimAsString("sub");
        String email = jwt.getClaimAsString("https://stocks-insper.com/email");

        if (email.isEmpty() || authId.isEmpty()) {
            throw new RuntimeException("Unauthorized");
        }
        
        User userFind = userService.getUserByAuthId(authId);
        if (userFind != null) {
            return userFind;
        }

        user.setAuthId(authId);
        user.setEmail(email);
        userService.saveUser(user);

        return user;
    }
}
