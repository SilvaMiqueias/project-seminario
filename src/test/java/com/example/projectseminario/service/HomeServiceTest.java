package com.example.projectseminario.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@WebMvcTest(HomeService.class)
public class HomeServiceTest {

    @InjectMocks
    private HomeService userService;

    @Test
    public void testAdd() {
        int exp = 3;
        int result = userService.add(2,1);
        assertEquals("Soma: ", exp, result);
    }

    @Test
    public void testSub() {
        int exp = 3;
        int result = userService.sub(4,1);
        assertEquals("Sub: ", exp, result);
    }

    @Test
    public void testMult() {
        int exp = 2;
        int result = userService.mult(2,1);
        assertEquals("Mult: ", exp, result);
    }

    @Test
    public void testDiv() {
        int exp = 3;
        int result = userService.div(2,1);
        assertEquals("Div: ", exp, result);
    }

    @Test
    public void testDivByZero() {
        assertThrows(ArithmeticException.class, () -> userService.div(1, 0));
    }

}
