package com.uni.lostandfound.controller;

import com.uni.lostandfound.entity.Item;
import com.uni.lostandfound.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/")
    public String home(Model model) {
        return findPaginated(1, "date", "desc", null, null, model);
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam(value = "sortField", defaultValue = "date") String sortField,
                                @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "statusFilter", required = false) String statusFilter,
                                Model model) {
        int pageSize = 6;

        Page<Item> page = itemService.findPaginatedAndFiltered(pageNo, pageSize, sortField, sortDir, keyword, statusFilter);
        List<Item> listItems = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages() == 0 ? 1 : page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        
        model.addAttribute("keyword", keyword);
        model.addAttribute("statusFilter", statusFilter);
        
        model.addAttribute("listItems", listItems);
        
        return "index";
    }

    @GetMapping("/item/new")
    public String showNewItemForm(Model model) {
        Item item = new Item();
        item.setDate(LocalDate.now());
        model.addAttribute("item", item);
        return "item-form";
    }

    @PostMapping("/item/save")
    public String saveItem(@Valid @ModelAttribute("item") Item item,
                           BindingResult result,
                           @RequestParam("image") MultipartFile multipartFile,
                           Model model) {
        if (result.hasErrors()) {
            return "item-form";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();

        try {
            itemService.saveItem(item, multipartFile, currentPrincipalName);
        } catch (Exception e) {
            model.addAttribute("error", "Error saving item: " + e.getMessage());
            return "item-form";
        }

        return "redirect:/";
    }

    @GetMapping("/item/{id}")
    public String viewItem(@PathVariable("id") Long id, Model model) {
        Item item = itemService.getItemById(id);
        model.addAttribute("item", item);
        

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isOwner = auth.getName().equals(item.getUser().getEmail());
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        
        model.addAttribute("canEdit", isOwner || isAdmin);
        
        return "item-detail";
    }

    @PostMapping("/item/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        itemService.deleteItemById(id, auth.getName());
        return "redirect:/";
    }

    @PostMapping("/item/{id}/resolve")
    public String resolveItem(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        itemService.resolveItemById(id, auth.getName());
        return "redirect:/item/" + id;
    }
}
