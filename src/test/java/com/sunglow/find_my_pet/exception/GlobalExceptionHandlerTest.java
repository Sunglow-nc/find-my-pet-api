package com.sunglow.find_my_pet.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testItemNotFoundExceptionShouldReturnNotFoundStatus() {
        // Arrange
        String errorMessage = "Item not found";
        ItemNotFoundException exception = new ItemNotFoundException(errorMessage);

        // Act
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleItemNotFoundException(
            exception);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }

    @Test
    void testItemNotFoundExceptionWithNoMessageShouldReturnNotFoundStatus() {
        // Arrange
        ItemNotFoundException exception = new ItemNotFoundException();

        // Act
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleItemNotFoundException(
            exception);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Item not found", responseEntity.getBody());
    }
}