package com.skeproject.controller;

import com.skeproject.entity.User;
import com.skeproject.service.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private SecurityUserDetailsService userDetailsManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String index(HttpServletRequest request, HttpSession session) {
        return "index";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));

        return "redirect:/";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = {
            MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public String addUser(@RequestParam Map<String, String> body, RedirectAttributes redirectAttributes) {
        User user = new User();
        user.setUsername(body.get("username"));
        user.setPassword(body.get("password"));
        user.setRoles("ROLE_USER");
        user.setAccountNonLocked(true);
        userDetailsManager.createUser(user);
        redirectAttributes.addFlashAttribute("message_success", "Konto założone poprawnie!");
        return "redirect:/";
    }

    private String getErrorMessage(HttpServletRequest request, String key) {
        Exception exception = (Exception) request.getSession().getAttribute(key);
        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = "Niepoprawna nazwa użytkownika lub hasło!";
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        } else {
            error = "Niepoprawna nazwa użytkownika lub hasło!";
        }
        return error;
    }
}