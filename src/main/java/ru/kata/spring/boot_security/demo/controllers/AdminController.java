package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserDetailsServiceImpl userDetailsService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserDetailsServiceImpl userDetailsService, RoleRepository roleRepository) {
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
    }

    @GetMapping()
    public String adminEndpoint(Model model) {
        List<User> users = userDetailsService.getUsers();
        model.addAttribute("users", users);
        return "admin";
    }
    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("users", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "create";
    }
//    @PostMapping("/create")
//    public String adminCreateEndpoint(@ModelAttribute User user, @RequestParam("roles[]") List<String> roles) {
//        Set<Role> userRoles = new HashSet<>();
//        for (String roleName : roles) {
//            Optional<Role> roleOptional = roleRepository.findByName(roleName);
//            if (roleOptional.isPresent()) {
//                userRoles.add(roleOptional.get());
//            }
//        }
//        user.setRoles(userRoles);
//
//        userDetailsService.saveUser(user);
//        return "redirect:/admin";
//    }
//    @PostMapping("/create")
//    public String adminCreateEndpoint(@ModelAttribute User user) {
//        Set<Role> userRoles = user.getRoles().stream()
//                .map(roleName -> roleRepository.findByName(roleName.getName()).orElse(null))
//                .filter(Objects::nonNull)
//                .collect(Collectors.toSet());
//        user.setRoles(userRoles);
//
//        userDetailsService.saveUser(user);
//        return "redirect:/admin";
//    }
//    @PostMapping("/create")
//    public String adminCreateEndpoint(@ModelAttribute User user, @RequestParam(name = "roleNames", required = false) List<String> roleNames) {
//        if (roleNames == null) {
//            roleNames = Collections.emptyList();
//        }
//        Set<Role> userRoles = roleNames.stream()
//                .map(roleName -> roleRepository.findByName(roleName).orElse(null))
//                .filter(Objects::nonNull)
//                .collect(Collectors.toSet());
//        user.setRoles(userRoles);
//
//        userDetailsService.saveUser(user);
//        return "redirect:/admin";
//    }
//@PostMapping("/create")
//public String adminCreateEndpoint(@ModelAttribute User user, @RequestParam("roleNames") List<String> roleNames) {
//    Set<Role> roles = roleRepository.findByName(roleNames);
//    user.setRoles(roles);
//
//    userDetailsService.saveUser(user);
//    return "redirect:/admin";
//}
@PostMapping("/create")
public String adminCreateEndpoint(@ModelAttribute User user,
                                  @RequestParam(value = "role1", required = false) String role1Name,
                                  @RequestParam(value = "role2", required = false) String role2Name) {
    Set<Role> roles = new HashSet<>();
    if (role1Name != null) {
        Role role1 = roleRepository.findByName(role1Name).get();
        roles.add(role1);
    }
    if (role2Name != null) {
        Role role2 = roleRepository.findByName(role2Name).get();
        roles.add(role2);
    }
    user.setRoles(roles);
    userDetailsService.saveUser(user);
    return "redirect:/admin";
}


    @PostMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userDetailsService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String updateUserForm(Model model, @PathVariable("id") Long id) {

        model.addAttribute("user", userDetailsService.getUserById(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "update-user";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("user") User user,
                         @RequestParam(value = "role1", required = false) String role1Name,
                         @RequestParam(value = "role2", required = false) String role2Name) {
        Set<Role> roles = new HashSet<>();
        if (role1Name != null) {
            Role role1 = roleRepository.findByName(role1Name).get();
            roles.add(role1);
        }
        if (role2Name != null) {
            Role role2 = roleRepository.findByName(role2Name).get();
            roles.add(role2);
        }
        user.setRoles(roles);
        userDetailsService.saveUser(user);
        return "redirect:/admin";
    }



}
