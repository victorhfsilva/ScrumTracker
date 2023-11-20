package com.db.scrumtrackerapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.db.scrumtrackerapi.model.Product;
import com.db.scrumtrackerapi.model.ProductBacklog;
import com.db.scrumtrackerapi.repositories.ProductBacklogRepository;
import com.db.scrumtrackerapi.services.impl.ProductBacklogService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductBacklogServiceTest {
    
    @Autowired
    @InjectMocks
    private ProductBacklogService productBacklogService;

    @Mock
    private ProductBacklogRepository productBacklogRepository;

    private Product product = new Product(
            "ExampleName",
            "ExampleClient",
            "ExampleObjectives",
            "ExampleVision",
            "ExampleState",
            "ExampleReady",
            null
        );

    private ProductBacklog expectedProductBacklog = new ProductBacklog(new ArrayList<>(), product);

    @Test
    @DisplayName("Assert Find By Product Id Returns Correct Product Backlog")
    public void testFindByProductId() {
        when(productBacklogRepository.findByProductId(eq(1L))).thenReturn(expectedProductBacklog);
        ProductBacklog actualProductBacklog = productBacklogService.findByProductId(1L);
        assertEquals(expectedProductBacklog, actualProductBacklog);
    }

    // @Test
    // public void testUpdateProduct() {

    //     Product savedProduct = new Product(
    //         "ExampleName",
    //         "ExampleClient",
    //         "ExampleObjectives",
    //         "ExampleVision",
    //         "ExampleState",
    //         "ExampleReady",
    //         null
    //     );

    //     when(productBacklogRepository.findById(eq(1L))).thenReturn(Optional.of(savedProduct));
        
    //     Product expectedProduct = new Product(
    //         "ModifiedExampleName",
    //         "ModifiedExampleClient",
    //         "ModifiedExampleObjectives",
    //         "ModifiedExampleVision",
    //         "ModifiedExampleState",
    //         "ModifiedReady",
    //         null
    //     );

    //     when(productBacklogRepository.save(eq(expectedProduct))).thenReturn(expectedProduct);
    //     Product actualProduct = productBacklogService.update(1L, expectedProduct);
    //     assertEquals(expectedProduct, actualProduct);
    // }

}
