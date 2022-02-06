package org.entityc.tutorial.web.admin;

import org.entityc.tutorial.service.UserService;
import java.util.Set;
import org.entityc.tutorial.model.Role;
import org.entityc.tutorial.service.TutorialService;
import org.entityc.tutorial.exception.ServiceException;
import org.entityc.tutorial.model.User;
import org.entityc.tutorial.security.PersistentUserDetailsService;
import org.entityc.tutorial.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
public class AdminHomeWebPageController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PersistentUserDetailsService userDetailsService;

    @Autowired private UserService userService;
    @Autowired private TutorialService tutorialService;

    @GetMapping(value = {"/admin"})
    public String home(Model model) throws ServiceException {
        User user = userDetailsService.findByEmail(securityService.findLoggedInUsername());
        model.addAttribute("loggedInUser", user);
        return "admin_home";
    }

    @GetMapping(value = {"/admin/user"})
    public String userHome(Model model) throws ServiceException {
        User user = userDetailsService.findByEmail(securityService.findLoggedInUsername());
        Set<Role> roles = user.getRoles();
        if (roles.contains(Role.ADMINISTRATOR)) {
            model.addAttribute("userList", userService.getUserDtoList(0,100, true));
        }
        model.addAttribute("canAddUser", userService.canCreate());
        model.addAttribute("loggedInUser", user);
        return "admin_User_home";
    }
    @GetMapping(value = {"/admin/tutorial"})
    public String tutorialHome(Model model) throws ServiceException {
        User user = userDetailsService.findByEmail(securityService.findLoggedInUsername());
        Set<Role> roles = user.getRoles();
        if (roles.contains(Role.INSTRUCTOR)) {
            model.addAttribute("tutorialList", tutorialService.getTutorialDtoList(0,100, true));
        }
        model.addAttribute("canAddTutorial", tutorialService.canCreate());
        model.addAttribute("loggedInUser", user);
        return "admin_Tutorial_home";
    }
}
