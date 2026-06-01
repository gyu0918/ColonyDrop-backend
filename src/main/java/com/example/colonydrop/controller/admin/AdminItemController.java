package com.example.colonydrop.controller.admin;

import com.example.colonydrop.entity.item.Item;
import com.example.colonydrop.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/items")
@RequiredArgsConstructor
public class AdminItemController {

    private final ItemRepository itemRepository;

    // 상품 목록 조회
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemRepository.findAll());
    }

    // 상품 등록
    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        return ResponseEntity.ok(itemRepository.save(item));
    }

    // 상품 수정
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item request) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품 없음"));
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());       // BigDecimal
        item.setImgUrl(request.getImgUrl());
        item.setStatus(request.getStatus());     // "SALE" or "SOLD"
        return ResponseEntity.ok(itemRepository.save(item));
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 상태 변경
    @PatchMapping("/{id}/status")
    public ResponseEntity<Item> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품 없음"));
        item.setStatus(body.get("status")); // "SALE" or "SOLD"
        return ResponseEntity.ok(itemRepository.save(item));
    }
}