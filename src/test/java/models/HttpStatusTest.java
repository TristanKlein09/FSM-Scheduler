package models;

import models.server.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpStatusTest {

    @Test
    @DisplayName("S1: fromCode(404) returns NOT_FOUND and there is correct reason phrase (valid)")
    void fromCodeNotFound() {
        HttpStatus status = HttpStatus.fromCode(404);

        assertEquals(HttpStatus.NOT_FOUND, status);
        assertEquals("Not Found", status.getReasonPhrase());
    }

    @Test
    @DisplayName("S2: Unknown code returns null (invalid)")
    void unknownCode() {
        assertNull(HttpStatus.fromCode(999));
    }
}
