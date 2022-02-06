package org.entityc.tutorial.web.admin;

import org.entityc.tutorial.security.PersistentUserDetailsService;
import org.entityc.tutorial.security.SecurityService;
import org.entityc.tutorial.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.View;
import org.springframework.http.HttpStatus;

import org.entityc.tutorial.model.Exercise;
import org.entityc.tutorial.dto.ExerciseDto;
import org.entityc.tutorial.service.ExerciseService;
import java.text.ParseException;
import org.entityc.tutorial.util.ResourceUtils;
import org.entityc.tutorial.model.Module;
import org.entityc.tutorial.dto.ModuleDto;
import org.entityc.tutorial.service.ModuleService;
import org.entityc.tutorial.model.Session;
import org.entityc.tutorial.dto.SessionDto;
import org.entityc.tutorial.service.SessionService;
import org.entityc.tutorial.model.Step;
import org.entityc.tutorial.dto.StepDto;
import org.entityc.tutorial.service.StepService;
import org.entityc.tutorial.model.Tutorial;
import org.entityc.tutorial.dto.TutorialDto;
import org.entityc.tutorial.service.TutorialService;
import org.entityc.tutorial.model.User;
import org.entityc.tutorial.dto.UserDto;
import org.entityc.tutorial.service.UserService;
import org.entityc.tutorial.model.Role;
import java.util.HashSet;
import java.util.Set;

import java.util.UUID;

@Controller
public class AdminNewWebPageController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PersistentUserDetailsService userDetailsService;

    @Autowired private ExerciseService exerciseService;
    @Autowired private ModuleService moduleService;
    @Autowired private SessionService sessionService;
    @Autowired private StepService stepService;
    @Autowired private TutorialService tutorialService;
    @Autowired private UserService userService;

    private void populateUser(Model model) {
        User user = userDetailsService.findByEmail(securityService.findLoggedInUsername());
        model.addAttribute("loggedInUser", user);
   }



    @PostMapping(value = {"/admin/exercise/add/{parent_id}"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public String addExercise(Model model, @PathVariable("parent_id") UUID parentId) throws ServiceException {
         model.addAttribute("parentId", parentId);
         populateUser(model);

         return "admin_Exercise_new";
    }
    @PostMapping(value = {"/admin/exercise/create/{parentId}"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ModelAndView createExercise(HttpServletRequest request, @PathVariable("parentId") UUID parentId) throws ServiceException, ParseException {
        Exercise object = new Exercise();
        object.setSessionId(parentId);

        String paramNumber = request.getParameter("number");
        object.setNumber(paramNumber != null ? Integer.valueOf(paramNumber) : 0);
        object = exerciseService.createExercise(object);
        request.setAttribute(
          View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/exercise/" + object.getId().toString() + "/detail");
    }


    @PostMapping(value = {"/admin/module/add/{parent_id}"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public String addModule(Model model, @PathVariable("parent_id") UUID parentId) throws ServiceException {
         model.addAttribute("parentId", parentId);
         populateUser(model);

         return "admin_Module_new";
    }
    @PostMapping(value = {"/admin/module/create/{parentId}"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ModelAndView createModule(HttpServletRequest request, @PathVariable("parentId") UUID parentId) throws ServiceException, ParseException {
        Module object = new Module();
        object.setTutorialId(parentId);

        String paramNumber = request.getParameter("number");
        object.setNumber(paramNumber != null ? Integer.valueOf(paramNumber) : 0);

        String paramTitle = request.getParameter("title");
        object.setTitle(paramTitle != null ? paramTitle : "");
        object = moduleService.createModule(object);
        request.setAttribute(
          View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/module/" + object.getId().toString() + "/detail");
    }


    @PostMapping(value = {"/admin/session/add/{parent_id}"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public String addSession(Model model, @PathVariable("parent_id") UUID parentId) throws ServiceException {
         model.addAttribute("parentId", parentId);
         populateUser(model);

         return "admin_Session_new";
    }
    @PostMapping(value = {"/admin/session/create/{parentId}"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ModelAndView createSession(HttpServletRequest request, @PathVariable("parentId") UUID parentId) throws ServiceException, ParseException {
        Session object = new Session();
        object.setModuleId(parentId);

        String paramNumber = request.getParameter("number");
        object.setNumber(paramNumber != null ? Integer.valueOf(paramNumber) : 0);

        String paramTitle = request.getParameter("title");
        object.setTitle(paramTitle != null ? paramTitle : "");
        object = sessionService.createSession(object);
        request.setAttribute(
          View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/session/" + object.getId().toString() + "/detail");
    }


    @PostMapping(value = {"/admin/step/add/{parent_id}"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public String addStep(Model model, @PathVariable("parent_id") UUID parentId) throws ServiceException {
         model.addAttribute("parentId", parentId);
         populateUser(model);

         return "admin_Step_new";
    }
    @PostMapping(value = {"/admin/step/create/{parentId}"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ModelAndView createStep(HttpServletRequest request, @PathVariable("parentId") UUID parentId) throws ServiceException, ParseException {
        Step object = new Step();
        object.setExerciseId(parentId);

        String paramNumber = request.getParameter("number");
        object.setNumber(paramNumber != null ? Integer.valueOf(paramNumber) : 0);
        object = stepService.createStep(object);
        request.setAttribute(
          View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/step/" + object.getId().toString() + "/detail");
    }


    @PostMapping(value = {"/admin/tutorial/add"})
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public String addTutorial(Model model) throws ServiceException {
         populateUser(model);

         return "admin_Tutorial_new";
    }
    @PostMapping(value = {"/admin/tutorial/create"})
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public ModelAndView createTutorial(HttpServletRequest request) throws ServiceException, ParseException {
        Tutorial object = new Tutorial();

        String paramTitle = request.getParameter("title");
        object.setTitle(paramTitle != null ? paramTitle : "");

        String paramIdentifier = request.getParameter("identifier");
        object.setIdentifier(paramIdentifier != null ? paramIdentifier : "");
        object = tutorialService.createTutorial(object);
        request.setAttribute(
          View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/tutorial/" + object.getId().toString() + "/detail");
    }


    @PostMapping(value = {"/admin/user/add"})
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public String addUser(Model model) throws ServiceException {
         populateUser(model);

         return "admin_User_new";
    }
    @PostMapping(value = {"/admin/user/create"})
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public ModelAndView createUser(HttpServletRequest request) throws ServiceException, ParseException {
        User object = new User();

        String[] paramRoles = request.getParameterValues("roles");
        Set<Role> set = new HashSet<>();
        for(String value : paramRoles) {
            set.add(Role.numberValueOf(Integer.valueOf(value)));
        }
        object.setRoles(paramRoles != null ? set : null);

        String paramEmail = request.getParameter("email");
        object.setEmail(paramEmail != null ? paramEmail : "");

        String paramFirstName = request.getParameter("firstName");
        object.setFirstName(paramFirstName != null ? paramFirstName : "");

        String paramLastName = request.getParameter("lastName");
        object.setLastName(paramLastName != null ? paramLastName : "");

        String paramEnabled = request.getParameter("enabled");
        object.setEnabled(paramEnabled != null && (paramEnabled.equalsIgnoreCase("true") || paramEnabled.equals("1")));

        String paramEncodedPassword = request.getParameter("encodedPassword");
        object.setEncodedPassword(paramEncodedPassword != null ? paramEncodedPassword : "");
        object = userService.createUser(object);
        request.setAttribute(
          View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/user/" + object.getId().toString() + "/detail");
    }
}
