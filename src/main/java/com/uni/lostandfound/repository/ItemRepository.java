package com.uni.lostandfound.repository;

import com.uni.lostandfound.entity.Item;
import com.uni.lostandfound.entity.ItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findByStatus(ItemStatus status, Pageable pageable);
    
    Page<Item> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description, Pageable pageable);
    
    Page<Item> findByStatusAndTitleContainingIgnoreCaseOrStatusAndDescriptionContainingIgnoreCase(
            ItemStatus status1, String title, ItemStatus status2, String description, Pageable pageable);
}
