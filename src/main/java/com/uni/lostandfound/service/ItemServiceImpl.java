package com.uni.lostandfound.service;

import com.uni.lostandfound.entity.Item;
import com.uni.lostandfound.entity.ItemStatus;
import com.uni.lostandfound.entity.User;
import com.uni.lostandfound.repository.ItemRepository;
import com.uni.lostandfound.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    @Override
    public Page<Item> findPaginatedAndFiltered(int pageNo, int pageSize, String sortField, String sortDir, String keyword, String statusFilter) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        ItemStatus status = null;
        if (statusFilter != null && !statusFilter.isEmpty()) {
            try {
                status = ItemStatus.valueOf(statusFilter.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Ignore invalid status
            }
        }

        if (keyword != null && !keyword.isEmpty() && status != null) {
            return itemRepository.findByStatusAndTitleContainingIgnoreCaseOrStatusAndDescriptionContainingIgnoreCase(status, keyword, status, keyword, pageable);
        } else if (keyword != null && !keyword.isEmpty()) {
            return itemRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword, pageable);
        } else if (status != null) {
            return itemRepository.findByStatus(status, pageable);
        } else {
            return itemRepository.findAll(pageable);
        }
    }

    @Override
    public Item saveItem(Item item, MultipartFile imageFile, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        item.setUser(user);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Create directory if it doesn't exist
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Generate unique filename
                String originalFilename = imageFile.getOriginalFilename();
                String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
                String uniqueFilename = UUID.randomUUID().toString() + extension;

                // Save file
                Path filePath = uploadPath.resolve(uniqueFilename);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                item.setImagePath("/" + uploadDir + uniqueFilename);
            } catch (IOException e) {
                throw new RuntimeException("Could not store image file: " + e.getMessage());
            }
        }

        return itemRepository.save(item);
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @Override
    public void deleteItemById(Long id, String userEmail) {
        Item item = getItemById(id);
        User user = userRepository.findByEmail(userEmail).orElse(null);
        
        // Ensure only the owner or an admin can delete
        if (user != null && (item.getUser().getId().equals(user.getId()) || user.getRole().name().equals("ROLE_ADMIN"))) {
            itemRepository.deleteById(id);
        } else {
            throw new RuntimeException("Unauthorized to delete this item");
        }
    }

    @Override
    public void resolveItemById(Long id, String userEmail) {
        Item item = getItemById(id);
        User user = userRepository.findByEmail(userEmail).orElse(null);
        
        if (user != null && (item.getUser().getId().equals(user.getId()) || user.getRole().name().equals("ROLE_ADMIN"))) {
            item.setStatus(ItemStatus.RESOLVED);
            itemRepository.save(item);
        } else {
            throw new RuntimeException("Unauthorized to resolve this item");
        }
    }
}
