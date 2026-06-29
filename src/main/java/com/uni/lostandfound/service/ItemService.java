package com.uni.lostandfound.service;

import com.uni.lostandfound.entity.Item;
import com.uni.lostandfound.entity.ItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ItemService {
    Page<Item> findPaginatedAndFiltered(int pageNo, int pageSize, String sortField, String sortDir, String keyword, String statusFilter);
    Item saveItem(Item item, MultipartFile imageFile, String userEmail);
    Item getItemById(Long id);
    void deleteItemById(Long id, String userEmail);
    void resolveItemById(Long id, String userEmail);
}
