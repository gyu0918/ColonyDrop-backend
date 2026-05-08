package com.example.colonydrop.controller.item;

import com.example.colonydrop.entity.item.Item;
import com.example.colonydrop.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ItemController {

    private final ItemRepository itemRepository;

    // 전체 상품 조회
    @GetMapping
    public ResponseEntity<List<Item>> getProducts() {
        List<Item> items = itemRepository.findAll();
        return ResponseEntity.ok(items);
    }

    // 상품 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<Item> getProduct(@PathVariable Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        return ResponseEntity.ok(item);
    }
}
