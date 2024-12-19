package edu.wm.cs.cs301.wordle.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Arrays;


public class WordleModelTest {
	
	private WordleModel model;
    private final int expectedMaximumRows = 6; // Adjust these values as the game settings change
    private final int expectedColumnCount = 5;
    private final int expectedTotalWordCount = 4435; // This is for 5 letter words
	
    @BeforeEach
    public void setUp() {
        model = new WordleModel();
        model.generateCurrentWord();
    }

    @Test
    public void testGenerateCurrentWord() {
    	model.generateCurrentWord();
    	String currentWord = model.getCurrentWord();
    	assertNotNull(currentWord, "Current word should not be null.");
    }
    
    @Test
    public void testGetCurrentWord() {
        String currentWord = model.getCurrentWord();
    	assertNotNull(currentWord, "Current word should not be null.");
    	}
    
    @Test
    public void testSetCurrentColumn() {
        model.setCurrentColumn('a');
        assertEquals(0, model.getCurrentColumn(), "Current column should be 0 after setting 'a'.");
    	}
    
    @Test
    public void testBackspace_initialState() {
        model.backspace();
        // Verify that backspace has no effect when called initially
        assertEquals(-1, model.getCurrentColumn(), "Backspace should not decrease current column below -1.");
        // Assuming the WordleResponse array contains 'null' values initially
        for (WordleResponse wr : model.getCurrentRow()) {
            assertNull(wr, "Every slot should be null when no character has been set.");
        }
    }

    @Test
    public void testBackspace_afterAddingCharacter() {
        model.setCurrentColumn('a');
        model.backspace();
        // Verify that backspace removes the character
        assertEquals(-1, model.getCurrentColumn(), "Backspace should decrease current column to -1 after one character.");
        assertNull(model.getCurrentRow()[0], "First colSumn should be null after backspace.");
    }

    @Test
    public void testGetCurrentRowNumber_initialState() {
        int currentRowNumber = model.getCurrentRowNumber();
        assertEquals(-1, currentRowNumber, "Initial current row number should be -1.");
    }

    @Test
    public void testSetCurrentRow_newRow() {
        boolean canContinue = model.setCurrentRow();
        assertTrue(canContinue, "Should be able to set new current row when the game starts.");
    }

    @Test
    public void testSetCurrentRow_lastRow() {
        // Simulate reaching the last row
        for (int i = 0; i < model.getMaximumRows() - 1; i++) {
            model.setCurrentRow();
        }
        boolean canContinue = model.setCurrentRow();
        assertFalse(canContinue, "Should not be able to set new current row after reaching maximum rows.");
    }

    @Test
    public void testGetWordleGrid_initialState() {
        WordleResponse[][] grid = model.getWordleGrid();
        assertNotNull(grid);
        assertEquals(model.getMaximumRows(), grid.length);
        assertEquals(model.getColumnCount(), grid[0].length);
        for (WordleResponse[] row : grid) {
            for (WordleResponse response : row) {
                assertNull(response, "All WordleResponse objects should initially be null.");
            }
        }
    }
    
    @Test
    public void testGetMaximumRows() {
        assertEquals(expectedMaximumRows, model.getMaximumRows(), "The maximum number of rows should match the expected value.");
    }

    @Test
    public void testGetColumnCount() {
        assertEquals(expectedColumnCount, model.getColumnCount(), "The column count should match the expected value.");
    }

    @Test
    public void testGetCurrentColumn_InitialState() {
        assertEquals(-1, model.getCurrentColumn(), "The initial current column should be -1 (or as per your initial game state).");
    }

    @Test
    public void testGetCurrentColumn_AfterSettingValue() {
        char testChar = 'A';
        model.setCurrentColumn(testChar);
        assertEquals(0, model.getCurrentColumn(), "After setting a character, the current column should be 0.");
    }

    @Test
    public void testGetTotalWordCount() {
        assertEquals(expectedTotalWordCount, model.getTotalWordCount(), "The total word count should match the number of words in the list.");
    }

}