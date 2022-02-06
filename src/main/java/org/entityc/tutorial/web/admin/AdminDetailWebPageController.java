package org.entityc.tutorial.web.admin;

import org.entityc.tutorial.security.PersistentUserDetailsService;
import org.entityc.tutorial.security.SecurityService;
import org.entityc.tutorial.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.View;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import org.entityc.tutorial.model.User;
import org.entityc.tutorial.dto.ExerciseDto;
import org.entityc.tutorial.service.ExerciseService;
import java.util.stream.Collectors;
import org.entityc.tutorial.model.Exercise;
import java.text.ParseException;
import org.entityc.tutorial.util.ResourceUtils;
import org.entityc.tutorial.exception.ValidationException;
import org.entityc.tutorial.dto.ModuleDto;
import org.entityc.tutorial.service.ModuleService;
import org.entityc.tutorial.model.Module;
import org.entityc.tutorial.dto.SessionDto;
import org.entityc.tutorial.service.SessionService;
import org.entityc.tutorial.model.Session;
import org.entityc.tutorial.dto.StepDto;
import org.entityc.tutorial.service.StepService;
import org.entityc.tutorial.model.Step;
import org.entityc.tutorial.dto.TutorialDto;
import org.entityc.tutorial.service.TutorialService;
import org.entityc.tutorial.model.Tutorial;
import org.entityc.tutorial.dto.UserDto;
import org.entityc.tutorial.service.UserService;
import org.entityc.tutorial.exception.ForbiddenException;
import org.entityc.tutorial.model.Role;
import java.util.HashSet;

import java.util.Set;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

@Controller
public class AdminDetailWebPageController {

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


    /**
     * /admin/exercise/{id}/detail
     * Directs the endpoint to use the detail page admin_Exercise_detail for Exercise
     */
    @GetMapping(value = {"/admin/exercise/{id}/detail"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    public String getExerciseDetail(Model model, @PathVariable("id") UUID id) throws ServiceException {
        ExerciseDto dto = exerciseService.getExerciseDtoById(id);
        model.addAttribute("exerciseDto", dto);
        boolean canEdit = exerciseService.canEdit(id);
        model.addAttribute("canEditExercise", canEdit);
        if (canEdit) {
        }
        {
            List<StepDto> list = stepService.getStepDtoListByExercise(id, 0, 1000, true);
            model.addAttribute("stepsList", list);
            boolean canAdd = stepService.canCreate();
            model.addAttribute("canAddStep", canAdd);
        }
        Vector<Object> parentDtos = new Vector<>();
        UUID exerciseParentId = exerciseService.getParentId(id);
        parentDtos.insertElementAt(sessionService.getSessionDtoById(exerciseParentId),0);
        UUID sessionParentId = sessionService.getParentId(exerciseParentId);
        parentDtos.insertElementAt(moduleService.getModuleDtoById(sessionParentId),0);
        UUID moduleParentId = moduleService.getParentId(sessionParentId);
        parentDtos.insertElementAt(tutorialService.getTutorialDtoById(moduleParentId),0);
        model.addAttribute("tutorialDto", parentDtos.get(0));
        model.addAttribute("moduleDto", parentDtos.get(1));
        model.addAttribute("sessionDto", parentDtos.get(2));
        populateUser(model);
        return "admin_Exercise_detail";
    }

    /**
     * /admin/exercise/{id}/update
     * The detail page allows the user to edit certain fields. When the user hits a field Edit button, it will go
     * to this endpoint, where the field to update is contained in the 'field-ident' parameter and the value is in
     * the 'field-value' parameter.
     */
    @PostMapping(value = {"/admin/exercise/{id}/update"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ModelAndView updateFieldInExercise(HttpServletRequest request, @PathVariable("id") UUID id) throws ServiceException, ParseException {
        Exercise object = exerciseService.getExerciseById(id);
        String[] fieldValues = request.getParameterValues("edit-field-value");
        String fieldName = request.getParameter("edit-field-ident");
        if (fieldName == null) {
            throw new ValidationException("Field to update not provided.");
        }
        String fieldValue = fieldValues == null ? null : fieldValues[0];
        if (fieldName.equals("number")) {
            object.setNumber(Integer.valueOf(fieldValue));
        }
        else if (fieldName.equals("overview")) {
            object.setOverview(fieldValue);
        }
        exerciseService.updateExercise(object);
        request.setAttribute(
          View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/exercise/" + id.toString() + "/detail");
    }

    /**
     * DELETE /admin/exercise/{id}
     * The detail page allows the user to edit certain fields. When the user hits a field Edit button, it will go
     * to this endpoint, where the field to update is contained in the 'field-ident' parameter and the value is in
     * the 'field-value' parameter.
     */
    @PostMapping(value = {"/admin/exercise/{id}/delete"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ModelAndView deleteExercise(HttpServletRequest request, @PathVariable("id") UUID id) throws ServiceException {
        // check to make sure it exists
        UUID exerciseParentId = exerciseService.getParentId(id);
        Exercise object = exerciseService.getExerciseById(id);
        exerciseService.deleteExerciseById(id);
        return new ModelAndView("redirect:/admin/session/" + exerciseParentId + "/detail");
    }


    /**
     * /admin/module/{id}/detail
     * Directs the endpoint to use the detail page admin_Module_detail for Module
     */
    @GetMapping(value = {"/admin/module/{id}/detail"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    public String getModuleDetail(Model model, @PathVariable("id") UUID id) throws ServiceException {
        ModuleDto dto = moduleService.getModuleDtoById(id);
        model.addAttribute("moduleDto", dto);
        boolean canEdit = moduleService.canEdit(id);
        model.addAttribute("canEditModule", canEdit);
        if (canEdit) {
        }
        {
            List<SessionDto> list = sessionService.getSessionDtoListByModule(id, 0, 1000, true);
            model.addAttribute("sessionsList", list);
            boolean canAdd = sessionService.canCreate();
            model.addAttribute("canAddSession", canAdd);
        }
        Vector<Object> parentDtos = new Vector<>();
        UUID moduleParentId = moduleService.getParentId(id);
        parentDtos.insertElementAt(tutorialService.getTutorialDtoById(moduleParentId),0);
        model.addAttribute("tutorialDto", parentDtos.get(0));
        populateUser(model);
        return "admin_Module_detail";
    }

    /**
     * /admin/module/{id}/update
     * The detail page allows the user to edit certain fields. When the user hits a field Edit button, it will go
     * to this endpoint, where the field to update is contained in the 'field-ident' parameter and the value is in
     * the 'field-value' parameter.
     */
    @PostMapping(value = {"/admin/module/{id}/update"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ModelAndView updateFieldInModule(HttpServletRequest request, @PathVariable("id") UUID id) throws ServiceException, ParseException {
        Module object = moduleService.getModuleById(id);
        String[] fieldValues = request.getParameterValues("edit-field-value");
        String fieldName = request.getParameter("edit-field-ident");
        if (fieldName == null) {
            throw new ValidationException("Field to update not provided.");
        }
        String fieldValue = fieldValues == null ? null : fieldValues[0];
        if (fieldName.equals("number")) {
            object.setNumber(Integer.valueOf(fieldValue));
        }
        else if (fieldName.equals("title")) {
            object.setTitle(fieldValue);
        }
        else if (fieldName.equals("summary")) {
            object.setSummary(fieldValue);
        }
        else if (fieldName.equals("overview")) {
            object.setOverview(fieldValue);
        }
        moduleService.updateModule(object);
        request.setAttribute(
          View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/module/" + id.toString() + "/detail");
    }

    /**
     * DELETE /admin/module/{id}
     * The detail page allows the user to edit certain fields. When the user hits a field Edit button, it will go
     * to this endpoint, where the field to update is contained in the 'field-ident' parameter and the value is in
     * the 'field-value' parameter.
     */
    @PostMapping(value = {"/admin/module/{id}/delete"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ModelAndView deleteModule(HttpServletRequest request, @PathVariable("id") UUID id) throws ServiceException {
        // check to make sure it exists
        UUID moduleParentId = moduleService.getParentId(id);
        Module object = moduleService.getModuleById(id);
        moduleService.deleteModuleById(id);
        return new ModelAndView("redirect:/admin/tutorial/" + moduleParentId + "/detail");
    }


    /**
     * /admin/session/{id}/detail
     * Directs the endpoint to use the detail page admin_Session_detail for Session
     */
    @GetMapping(value = {"/admin/session/{id}/detail"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    public String getSessionDetail(Model model, @PathVariable("id") UUID id) throws ServiceException {
        SessionDto dto = sessionService.getSessionDtoById(id);
        model.addAttribute("sessionDto", dto);
        boolean canEdit = sessionService.canEdit(id);
        model.addAttribute("canEditSession", canEdit);
        if (canEdit) {
        }
        {
            List<ExerciseDto> list = exerciseService.getExerciseDtoListBySession(id, 0, 1000, true);
            model.addAttribute("exercisesList", list);
            boolean canAdd = exerciseService.canCreate();
            model.addAttribute("canAddExercise", canAdd);
        }
        Vector<Object> parentDtos = new Vector<>();
        UUID sessionParentId = sessionService.getParentId(id);
        parentDtos.insertElementAt(moduleService.getModuleDtoById(sessionParentId),0);
        UUID moduleParentId = moduleService.getParentId(sessionParentId);
        parentDtos.insertElementAt(tutorialService.getTutorialDtoById(moduleParentId),0);
        model.addAttribute("tutorialDto", parentDtos.get(0));
        model.addAttribute("moduleDto", parentDtos.get(1));
        populateUser(model);
        return "admin_Session_detail";
    }

    /**
     * /admin/session/{id}/update
     * The detail page allows the user to edit certain fields. When the user hits a field Edit button, it will go
     * to this endpoint, where the field to update is contained in the 'field-ident' parameter and the value is in
     * the 'field-value' parameter.
     */
    @PostMapping(value = {"/admin/session/{id}/update"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ModelAndView updateFieldInSession(HttpServletRequest request, @PathVariable("id") UUID id) throws ServiceException, ParseException {
        Session object = sessionService.getSessionById(id);
        String[] fieldValues = request.getParameterValues("edit-field-value");
        String fieldName = request.getParameter("edit-field-ident");
        if (fieldName == null) {
            throw new ValidationException("Field to update not provided.");
        }
        String fieldValue = fieldValues == null ? null : fieldValues[0];
        if (fieldName.equals("number")) {
            object.setNumber(Integer.valueOf(fieldValue));
        }
        else if (fieldName.equals("title")) {
            object.setTitle(fieldValue);
        }
        else if (fieldName.equals("objective")) {
            object.setObjective(fieldValue);
        }
        else if (fieldName.equals("discussion")) {
            object.setDiscussion(fieldValue);
        }
        sessionService.updateSession(object);
        request.setAttribute(
          View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/session/" + id.toString() + "/detail");
    }

    /**
     * DELETE /admin/session/{id}
     * The detail page allows the user to edit certain fields. When the user hits a field Edit button, it will go
     * to this endpoint, where the field to update is contained in the 'field-ident' parameter and the value is in
     * the 'field-value' parameter.
     */
    @PostMapping(value = {"/admin/session/{id}/delete"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ModelAndView deleteSession(HttpServletRequest request, @PathVariable("id") UUID id) throws ServiceException {
        // check to make sure it exists
        UUID sessionParentId = sessionService.getParentId(id);
        Session object = sessionService.getSessionById(id);
        sessionService.deleteSessionById(id);
        return new ModelAndView("redirect:/admin/module/" + sessionParentId + "/detail");
    }


    /**
     * /admin/step/{id}/detail
     * Directs the endpoint to use the detail page admin_Step_detail for Step
     */
    @GetMapping(value = {"/admin/step/{id}/detail"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    public String getStepDetail(Model model, @PathVariable("id") UUID id) throws ServiceException {
        StepDto dto = stepService.getStepDtoById(id);
        model.addAttribute("stepDto", dto);
        boolean canEdit = stepService.canEdit(id);
        model.addAttribute("canEditStep", canEdit);
        if (canEdit) {
        }
        Vector<Object> parentDtos = new Vector<>();
        UUID stepParentId = stepService.getParentId(id);
        parentDtos.insertElementAt(exerciseService.getExerciseDtoById(stepParentId),0);
        UUID exerciseParentId = exerciseService.getParentId(stepParentId);
        parentDtos.insertElementAt(sessionService.getSessionDtoById(exerciseParentId),0);
        UUID sessionParentId = sessionService.getParentId(exerciseParentId);
        parentDtos.insertElementAt(moduleService.getModuleDtoById(sessionParentId),0);
        UUID moduleParentId = moduleService.getParentId(sessionParentId);
        parentDtos.insertElementAt(tutorialService.getTutorialDtoById(moduleParentId),0);
        model.addAttribute("tutorialDto", parentDtos.get(0));
        model.addAttribute("moduleDto", parentDtos.get(1));
        model.addAttribute("sessionDto", parentDtos.get(2));
        model.addAttribute("exerciseDto", parentDtos.get(3));
        populateUser(model);
        return "admin_Step_detail";
    }

    /**
     * /admin/step/{id}/update
     * The detail page allows the user to edit certain fields. When the user hits a field Edit button, it will go
     * to this endpoint, where the field to update is contained in the 'field-ident' parameter and the value is in
     * the 'field-value' parameter.
     */
    @PostMapping(value = {"/admin/step/{id}/update"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ModelAndView updateFieldInStep(HttpServletRequest request, @PathVariable("id") UUID id) throws ServiceException, ParseException {
        Step object = stepService.getStepById(id);
        String[] fieldValues = request.getParameterValues("edit-field-value");
        String fieldName = request.getParameter("edit-field-ident");
        if (fieldName == null) {
            throw new ValidationException("Field to update not provided.");
        }
        String fieldValue = fieldValues == null ? null : fieldValues[0];
        if (fieldName.equals("number")) {
            object.setNumber(Integer.valueOf(fieldValue));
        }
        else if (fieldName.equals("instructions")) {
            object.setInstructions(fieldValue);
        }
        stepService.updateStep(object);
        request.setAttribute(
          View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/step/" + id.toString() + "/detail");
    }

    /**
     * DELETE /admin/step/{id}
     * The detail page allows the user to edit certain fields. When the user hits a field Edit button, it will go
     * to this endpoint, where the field to update is contained in the 'field-ident' parameter and the value is in
     * the 'field-value' parameter.
     */
    @PostMapping(value = {"/admin/step/{id}/delete"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ModelAndView deleteStep(HttpServletRequest request, @PathVariable("id") UUID id) throws ServiceException {
        // check to make sure it exists
        UUID stepParentId = stepService.getParentId(id);
        Step object = stepService.getStepById(id);
        stepService.deleteStepById(id);
        return new ModelAndView("redirect:/admin/exercise/" + stepParentId + "/detail");
    }


    /**
     * /admin/tutorial/{id}/detail
     * Directs the endpoint to use the detail page admin_Tutorial_detail for Tutorial
     */
    @GetMapping(value = {"/admin/tutorial/{id}/detail"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public String getTutorialDetail(Model model, @PathVariable("id") UUID id) throws ServiceException {
        TutorialDto dto = tutorialService.getTutorialDtoById(id);
        model.addAttribute("tutorialDto", dto);
        boolean canEdit = tutorialService.canEdit(id);
        model.addAttribute("canEditTutorial", canEdit);
        if (canEdit) {
        }
        {
            List<ModuleDto> list = moduleService.getModuleDtoListByTutorial(id, 0, 1000, true);
            model.addAttribute("modulesList", list);
            boolean canAdd = moduleService.canCreate();
            model.addAttribute("canAddModule", canAdd);
        }
        populateUser(model);
        return "admin_Tutorial_detail";
    }

    /**
     * /admin/tutorial/{id}/update
     * The detail page allows the user to edit certain fields. When the user hits a field Edit button, it will go
     * to this endpoint, where the field to update is contained in the 'field-ident' parameter and the value is in
     * the 'field-value' parameter.
     */
    @PostMapping(value = {"/admin/tutorial/{id}/update"})
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public ModelAndView updateFieldInTutorial(HttpServletRequest request, @PathVariable("id") UUID id) throws ServiceException, ParseException {
        Tutorial object = tutorialService.getTutorialById(id);
        String[] fieldValues = request.getParameterValues("edit-field-value");
        String fieldName = request.getParameter("edit-field-ident");
        if (fieldName == null) {
            throw new ValidationException("Field to update not provided.");
        }
        String fieldValue = fieldValues == null ? null : fieldValues[0];
        if (fieldName.equals("title")) {
            object.setTitle(fieldValue);
        }
        else if (fieldName.equals("summary")) {
            object.setSummary(fieldValue);
        }
        else if (fieldName.equals("overview")) {
            object.setOverview(fieldValue);
        }
        tutorialService.updateTutorial(object);
        request.setAttribute(
          View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/tutorial/" + id.toString() + "/detail");
    }

    /**
     * DELETE /admin/tutorial/{id}
     * The detail page allows the user to edit certain fields. When the user hits a field Edit button, it will go
     * to this endpoint, where the field to update is contained in the 'field-ident' parameter and the value is in
     * the 'field-value' parameter.
     */
    @PostMapping(value = {"/admin/tutorial/{id}/delete"})
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public ModelAndView deleteTutorial(HttpServletRequest request, @PathVariable("id") UUID id) throws ServiceException {
        // check to make sure it exists
        Tutorial object = tutorialService.getTutorialById(id);
        tutorialService.deleteTutorialById(id);
        return new ModelAndView("redirect:/admin/tutorial");
    }


    /**
     * /admin/user/{id}/detail
     * Directs the endpoint to use the detail page admin_User_detail for User
     */
    @GetMapping(value = {"/admin/user/{id}/detail"})
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public String getUserDetail(Model model, @PathVariable("id") UUID id) throws ServiceException {
        UserDto dto = userService.getUserDtoById(id);
        model.addAttribute("userDto", dto);
        boolean canEdit = userService.canEdit(id);
        model.addAttribute("canEditUser", canEdit);
        model.addAttribute("user_roles_text", dto.getRoles().stream().map(e -> e.getTitle()).collect(Collectors.joining(", ")));
        model.addAttribute("user_roles_enum_values", dto.getRoles().stream().map(e -> e.getNumberValue()).collect(Collectors.toSet()));
        if (canEdit) {
            boolean canUpdateRoles = userService.canUpdateAttribute(dto, "roles");
            model.addAttribute("canEditUser_roles", canUpdateRoles);
            boolean canUpdateEnabled = userService.canUpdateAttribute(dto, "enabled");
            model.addAttribute("canEditUser_enabled", canUpdateEnabled);
        }
        populateUser(model);
        return "admin_User_detail";
    }

    /**
     * /admin/user/{id}/update
     * The detail page allows the user to edit certain fields. When the user hits a field Edit button, it will go
     * to this endpoint, where the field to update is contained in the 'field-ident' parameter and the value is in
     * the 'field-value' parameter.
     */
    @PostMapping(value = {"/admin/user/{id}/update"})
    public ModelAndView updateFieldInUser(HttpServletRequest request, @PathVariable("id") UUID id) throws ServiceException, ParseException {
        User object = userService.getUserById(id);
        // add authorization checks here and throw exception if necessary
        if (!userService.canEdit(id)) {
            throw new ForbiddenException("Cannot edit attribute enabled.");
        }
        String[] fieldValues = request.getParameterValues("edit-field-value");
        String fieldName = request.getParameter("edit-field-ident");
        if (fieldName == null) {
            throw new ValidationException("Field to update not provided.");
        }
        String fieldValue = fieldValues == null ? null : fieldValues[0];
        if (fieldName.equals("roles")) {
            Set<Role> set = new HashSet<>();
            for(String value : fieldValues) {
                set.add(Role.numberValueOf(Integer.valueOf(value)));
            }
            object.setRoles(set);
        }
        else if (fieldName.equals("email")) {
            object.setEmail(fieldValue);
        }
        else if (fieldName.equals("firstName")) {
            object.setFirstName(fieldValue);
        }
        else if (fieldName.equals("lastName")) {
            object.setLastName(fieldValue);
        }
        else if (fieldName.equals("enabled")) {
            object.setEnabled(fieldValue != null && (fieldValue.equalsIgnoreCase("true") || fieldValue.equals("1")));
        }
        else if (fieldName.equals("createdOn")) {
            object.setCreatedOn(ResourceUtils.ParseDateString(fieldValue));
        }
        userService.updateUser(object);
        request.setAttribute(
          View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/user/" + id.toString() + "/detail");
    }

    /**
     * DELETE /admin/user/{id}
     * The detail page allows the user to edit certain fields. When the user hits a field Edit button, it will go
     * to this endpoint, where the field to update is contained in the 'field-ident' parameter and the value is in
     * the 'field-value' parameter.
     */
    @PostMapping(value = {"/admin/user/{id}/delete"})
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public ModelAndView deleteUser(HttpServletRequest request, @PathVariable("id") UUID id) throws ServiceException {
        // check to make sure it exists
        User object = userService.getUserById(id);
        userService.deleteUserById(id);
        return new ModelAndView("redirect:/admin/user");
    }

}
