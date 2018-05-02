package hospital.controller;

import hospital.constants.ApplicationConstants;
import hospital.dto.UserDto;
import hospital.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value="/admin/administratorMenu")
public class AdministratorController implements WebMvcConfigurer {
    private UserService userService;

    @Autowired
    public AdministratorController(UserService userService){
        this.userService = userService;
    }


    @GetMapping()
    public String userForm(Model model) {
        model.addAttribute(new UserDto());
        model.addAttribute("roles", ApplicationConstants.Roles.ROLES);
        return "administratorMenu";
    }

    @PostMapping(params = "createBtn")
    public String createUser(@RequestParam String selRole, @ModelAttribute @Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", ApplicationConstants.Roles.ROLES);
            return "administratorMenu";
        }
        userDto.setRole(selRole);
        userService.register(userDto);
        return "redirect:/admin/administratorMenu";
    }

    @PostMapping(params = "updateBtn")
    public String updateUser(@RequestParam String selRole, @ModelAttribute @Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", ApplicationConstants.Roles.ROLES);
            return "administratorMenu";
        }
        userDto.setRole(selRole);
        userService.update(userDto);
        return "redirect:/admin/administratorMenu";
    }

    @PostMapping(params = "deleteBtn")
    public String deleteUser(@ModelAttribute UserDto userDto) {
        userService.delete(userDto.getId());
        return "redirect:/admin/administratorMenu";
    }

    @PostMapping(value = "/showAll", params = "showAllBtn")
    public String showAll(Model model) {
        List<UserDto> userDtos = userService.findAll();
        model.addAttribute("userDtos", userDtos);
        return "userTable";
    }

    @PostMapping(value = "/logout", params="logoutBtn")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}