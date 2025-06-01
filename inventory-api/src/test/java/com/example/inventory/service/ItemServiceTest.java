package com.example.inventory.service;

import com.example.inventory.model.Item;
import com.example.inventory.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @Mock
    private ItemRepository repository;
    private ItemService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new ItemService(repository);
    }

    @Test
    void getAll_ShouldReturnEmptyList_WhenNoItems() {
        when(repository.findAll()).thenReturn(List.of());
        
        List<Item> result = service.getAll();
        
        assertThat(result).isEmpty();
        verify(repository).findAll();
    }

    @Test
    void getAll_ShouldReturnItems_WhenItemsExist() {
        Item item = Item.builder()
                .id(1)
                .name("Test Item")
                .description("Description")
                .quantity(10)
                .build();
        when(repository.findAll()).thenReturn(List.of(item));
        
        List<Item> result = service.getAll();
        
        assertThat(result)
                .hasSize(1)
                .first()
                .satisfies(i -> {
                    assertThat(i.getId()).isEqualTo(1);
                    assertThat(i.getName()).isEqualTo("Test Item");
                    assertThat(i.getDescription()).isEqualTo("Description");
                    assertThat(i.getQuantity()).isEqualTo(10);
                });
        verify(repository).findAll();
    }

    @Test
    void getById_ShouldReturnEmpty_WhenItemNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        
        Optional<Item> result = service.getById(1);
        
        assertThat(result).isEmpty();
        verify(repository).findById(1);
    }

    @Test
    void getById_ShouldReturnItem_WhenItemExists() {
        Item item = Item.builder()
                .id(1)
                .name("Test Item")
                .description("Description")
                .quantity(10)
                .build();
        when(repository.findById(1)).thenReturn(Optional.of(item));
        
        Optional<Item> result = service.getById(1);
        
        assertThat(result)
                .isPresent()
                .hasValueSatisfying(i -> {
                    assertThat(i.getId()).isEqualTo(1);
                    assertThat(i.getName()).isEqualTo("Test Item");
                    assertThat(i.getDescription()).isEqualTo("Description");
                    assertThat(i.getQuantity()).isEqualTo(10);
                });
        verify(repository).findById(1);
    }

    @Test
    void save_ShouldReturnSavedItem() {
        Item itemToSave = Item.builder()
                .name("New Item")
                .description("New Description")
                .quantity(5)
                .build();
        Item savedItem = Item.builder()
                .id(1)
                .name("New Item")
                .description("New Description")
                .quantity(5)
                .build();
        when(repository.save(any(Item.class))).thenReturn(savedItem);
        
        Item result = service.save(itemToSave);
        
        assertThat(result)
                .satisfies(i -> {
                    assertThat(i.getId()).isEqualTo(1);
                    assertThat(i.getName()).isEqualTo("New Item");
                    assertThat(i.getDescription()).isEqualTo("New Description");
                    assertThat(i.getQuantity()).isEqualTo(5);
                });
        verify(repository).save(itemToSave);
    }

    @Test
    void deleteById_ShouldDeleteItem() {
        doNothing().when(repository).deleteById(1);
        
        service.deleteById(1);
        
        verify(repository).deleteById(1);
    }

    @Test
    void searchByName_ShouldReturnMatchingItems() {
        Item item = Item.builder()
                .id(1)
                .name("Test Item")
                .description("Description")
                .quantity(10)
                .build();
        when(repository.findByNameContainingIgnoreCase("Test"))
                .thenReturn(List.of(item));
        
        List<Item> result = service.searchByName("Test");
        
        assertThat(result)
                .hasSize(1)
                .first()
                .satisfies(i -> {
                    assertThat(i.getId()).isEqualTo(1);
                    assertThat(i.getName()).isEqualTo("Test Item");
                    assertThat(i.getDescription()).isEqualTo("Description");
                    assertThat(i.getQuantity()).isEqualTo(10);
                });
        verify(repository).findByNameContainingIgnoreCase("Test");
    }
} 