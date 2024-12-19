package edu.wm.cs.cs301.wordle.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.awt.Color;

public class WordleResponseTest {
	
	private WordleResponse response;
	
	@BeforeEach
    public void setUp() {
        response = new WordleResponse('a', Color.BLUE, Color.YELLOW);
    }
	
    @Test
    public void testGetChar() {
        assertEquals('a', response.getChar());
    }

    @Test
    public void testGetBackgroundColor() {
        assertEquals(Color.BLUE, response.getBackgroundColor());
    }

    @Test
    public void testGetForegroundColor() {
        assertEquals(Color.YELLOW, response.getForegroundColor());
    }
}

