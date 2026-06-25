package com.uni.lostandfound.config;

import com.uni.lostandfound.entity.Item;
import com.uni.lostandfound.entity.ItemStatus;
import com.uni.lostandfound.entity.Role;
import com.uni.lostandfound.entity.User;
import com.uni.lostandfound.repository.ItemRepository;
import com.uni.lostandfound.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Create a dummy user
            User user1 = new User();
            user1.setName("John Doe");
            user1.setEmail("john@example.com");
            user1.setPassword(passwordEncoder.encode("password123"));
            user1.setRole(Role.ROLE_USER);
            userRepository.save(user1);

            User user2 = new User();
            user2.setName("Jane Smith");
            user2.setEmail("jane@example.com");
            user2.setPassword(passwordEncoder.encode("password123"));
            user2.setRole(Role.ROLE_USER);
            userRepository.save(user2);

            // Create dummy items
            itemRepository.save(new Item(null, "Brown Leather Wallet", "Lost my brown leather wallet near the library entrance. Contains ID and some cash.", "Main Library", LocalDate.now().minusDays(2), ItemStatus.LOST, "https://loremflickr.com/300/300/wallet", user1));
            itemRepository.save(new Item(null, "Keys with Red Keychain", "Found a set of keys with a red Ferrari keychain on the cafeteria bench.", "Student Cafeteria", LocalDate.now().minusDays(1), ItemStatus.FOUND, "https://loremflickr.com/300/300/keys", user2));
            itemRepository.save(new Item(null, "iPhone 13 Pro", "Left my phone in the lecture hall 3. It has a clear case.", "Lecture Hall 3", LocalDate.now(), ItemStatus.LOST, "https://loremflickr.com/300/300/iphone", user1));
            itemRepository.save(new Item(null, "NorthFace Backpack", "Found a black backpack left unattended in the study zone.", "Study Zone B", LocalDate.now().minusDays(3), ItemStatus.FOUND, "https://loremflickr.com/300/300/backpack", user2));
            itemRepository.save(new Item(null, "Black Umbrella", "Lost my umbrella yesterday during the rain near the bus stop.", "Bus Stop", LocalDate.now().minusDays(1), ItemStatus.LOST, "https://loremflickr.com/300/300/umbrella", user1));
            itemRepository.save(new Item(null, "Sony Headphones", "Found these black over-ear headphones on the gym bleachers.", "University Gym", LocalDate.now(), ItemStatus.FOUND, "https://loremflickr.com/300/300/headphones", user2));
            itemRepository.save(new Item(null, "Denim Jacket", "Lost my blue denim jacket. I think I left it in the lab.", "Computer Lab 2", LocalDate.now().minusDays(4), ItemStatus.LOST, "https://loremflickr.com/300/300/jacket", user1));
            itemRepository.save(new Item(null, "Silver Wristwatch", "Found a silver Casio watch near the water fountain.", "Science Building", LocalDate.now().minusDays(2), ItemStatus.FOUND, "https://loremflickr.com/300/300/watch", user2));
            itemRepository.save(new Item(null, "Wireless Earbuds", "Lost my white wireless earbuds case.", "Basketball Court", LocalDate.now(), ItemStatus.LOST, "https://loremflickr.com/300/300/earbuds", user1));
            itemRepository.save(new Item(null, "HydroFlask Bottle", "Found a blue water bottle. Turned it in to the security desk.", "Security Desk", LocalDate.now().minusDays(5), ItemStatus.RESOLVED, "https://loremflickr.com/300/300/bottle", user2));
        }
    }
}
