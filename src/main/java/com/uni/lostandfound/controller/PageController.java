package com.uni.lostandfound.controller;

import com.uni.lostandfound.repository.ItemRepository;
import com.uni.lostandfound.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/privacy")
    public String privacy() {
        return "privacy";
    }

    @GetMapping("/terms")
    public String terms() {
        return "terms";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/profile")
    public String profile(org.springframework.ui.Model model) {
        org.springframework.security.core.Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        com.uni.lostandfound.entity.User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("user", user);
        
        if (user.getRole().name().equals("ROLE_ADMIN")) {
            java.util.List<com.uni.lostandfound.entity.User> allUsersList = userRepository.findAll();
            java.util.List<com.uni.lostandfound.entity.User> activeUsers = new java.util.ArrayList<>();
            java.util.List<com.uni.lostandfound.entity.User> archivedUsers = new java.util.ArrayList<>();
            for (com.uni.lostandfound.entity.User u : allUsersList) {
                if (u.isDeleted()) archivedUsers.add(u);
                else activeUsers.add(u);
            }
            model.addAttribute("activeUsers", activeUsers);
            model.addAttribute("archivedUsers", archivedUsers);
            model.addAttribute("totalUsers", userRepository.count());
            model.addAttribute("totalLost", itemRepository.countByStatus(com.uni.lostandfound.entity.ItemStatus.LOST));
            model.addAttribute("totalFound", itemRepository.countByStatus(com.uni.lostandfound.entity.ItemStatus.FOUND));
            model.addAttribute("totalResolved", itemRepository.countByStatus(com.uni.lostandfound.entity.ItemStatus.RESOLVED));
        } else {

            model.addAttribute("userItems", itemRepository.findByUserOrderByDateDesc(user));
        }
        
        return "profile";
    }

    @PostMapping("/profile/upload-image")
    public String uploadProfileImage(@RequestParam("image") MultipartFile imageFile) {
        org.springframework.security.core.Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        com.uni.lostandfound.entity.User user = userRepository.findByEmail(email).orElse(null);
        
        if (user != null && imageFile != null && !imageFile.isEmpty()) {
            try {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFilename = imageFile.getOriginalFilename();
                String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
                String uniqueFilename = "profile_" + UUID.randomUUID().toString() + extension;

                Path filePath = uploadPath.resolve(uniqueFilename);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                user.setProfileImage("/" + uploadDir + uniqueFilename);
                userRepository.save(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/profile";
    }
}
