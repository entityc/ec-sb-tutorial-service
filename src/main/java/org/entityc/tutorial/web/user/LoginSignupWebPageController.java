
package org.entityc.tutorial.web.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.entityc.tutorial.model.User;
import org.entityc.tutorial.repository.UserRepository;
import org.entityc.tutorial.service.UserService;
import org.entityc.tutorial.model.Role;
import org.entityc.tutorial.exception.ServiceException;
import org.entityc.tutorial.security.PersistentUserDetailsService;
import org.entityc.tutorial.security.SecurityService;
import org.entityc.tutorial.security.UserSignupDto;
import org.entityc.tutorial.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
public class LoginSignupWebPageController {

    @Autowired
    private PersistentUserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    SecurityService securityService;

    @Autowired
    JwtUtils jwtUtils;

    protected final Log logger = LogFactory.getLog(this.getClass());

    @GetMapping(value = "/login")
    public String login(Model model, String error, String logout, HttpServletResponse res) {
        model.addAttribute("userNameFieldName", "Email");
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null) {
            Cookie cookie = new Cookie("token", "invalid");
            cookie.setPath("/");
            cookie.setMaxAge(Integer.MAX_VALUE);
            res.addCookie(cookie);
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletResponse res) {

        if (!userService.canLogin(username)) {
            return "redirect:/login";
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        Cookie cookie = new Cookie("token", jwt);

        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);

        res.addCookie(cookie);

        User user = userDetailsService.findByEmail(securityService.findLoggedInUsername());
        Set<Role> roles = user.getRoles();
        boolean canGoToAdminSide = roles.contains(Role.ADMINISTRATOR) || roles.contains(Role.INSTRUCTOR);

        return canGoToAdminSide ? "redirect:/admin" : "redirect:/";
    }

    @ModelAttribute("user")
    public UserSignupDto userSignupDto() {
        return new UserSignupDto();
    }

    @GetMapping(value = "/signup")
    public String signup(Model model) {
        return "signup";
    }

    @PostMapping(value = "/signup")
    public String registerUserAccount(@ModelAttribute("user") @Valid UserSignupDto userDto,
                                      BindingResult result, HttpServletResponse res) throws ServiceException {

        User existing = userDetailsService.findByEmail(userDto.getUsername());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            return "signup";
        }

        save(userDto);
        return login(userDto.getUsername(), userDto.getPassword(), res);
    }

    public User save(UserSignupDto userSignupDto) throws ServiceException {
        User user = new User();
        user.setFirstName(userSignupDto.getFirstName());
        user.setLastName(userSignupDto.getLastName());
        user.setEmail(userSignupDto.getUsername());
        user.setEncodedPassword(passwordEncoder.encode(userSignupDto.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.STUDENT);
        user.setRoles(roles);
        User createdUser = userService.createUser(user);
        userDetailsService.updateUser(createdUser);
        return createdUser;
    }
}
