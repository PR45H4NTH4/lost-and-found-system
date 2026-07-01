package com.uni.lostandfound.controller;

import com.uni.lostandfound.dto.UserRegistrationDto;
import com.uni.lostandfound.entity.User;
import com.uni.lostandfound.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PostMapping("/user/add")
    public String addUser(@ModelAttribute("user") UserRegistrationDto userDto, @RequestParam("role") String role) {
        User existingUser = userService.findByEmail(userDto.getEmail());
        if (existingUser != null) {
            return "redirect:/profile?error=emailexists";
        }
        
        if ("ADMIN".equals(role)) {
            userService.saveAdmin(userDto);
        } else {
            userService.saveUser(userDto);
        }
        
        return "redirect:/profile?success=useradded";
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(auth.getName());
        
        if (currentUser.getId().equals(id)) {
            return "redirect:/profile?error=cannotdeleteself";
        }
        
        userService.deleteUser(id);
        return "redirect:/profile?success=userdeleted";
    }

    @PostMapping("/user/reset-password")
    public String resetPassword(@RequestParam("userId") Long id, @RequestParam("newPassword") String newPassword) {
        userService.resetPassword(id, newPassword);
        return "redirect:/profile?success=passwordreset";
    }
}
