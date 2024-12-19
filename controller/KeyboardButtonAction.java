package edu.wm.cs.cs301.wordle.controller;

import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import edu.wm.cs.cs301.wordle.model.AppColors;
import edu.wm.cs.cs301.wordle.model.WordleModel;
import edu.wm.cs.cs301.wordle.model.WordleResponse;
import edu.wm.cs.cs301.wordle.view.StatisticsDialog;
import edu.wm.cs.cs301.wordle.view.WordleFrame;

public class KeyboardButtonAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private final WordleFrame view;
	
	private final WordleModel model;
	
	private boolean FirstGuessIndicator = false;
	
	private boolean GrayLetterIndicator = false;
	
	private int GreenandYellowcount = 0;

	public KeyboardButtonAction(WordleFrame view, WordleModel model) {
		this.view = view;
		this.model = model;
	}
	

	
	@Override
	public void actionPerformed(ActionEvent event) {
		JButton button = (JButton) event.getSource();
		String text = button.getActionCommand();
		switch (text) {
	
		case "Enter":
			if (model.getCurrentColumn() >= (model.getColumnCount() - 1)) {
				boolean moreRows = model.setCurrentRow();
				WordleResponse[] currentRow = model.getCurrentRow();
				int greenCount = 0;
				GreenandYellowcount = 0;
				GrayLetterIndicator = false;
				FirstGuessIndicator = true; // Because the guess went through, we know that it is no longer the first guess
				
                WordleResponse[] previousGuessResponses = model.getWordleGrid()[model.getCurrentRowNumber()];
                for (WordleResponse response : previousGuessResponses) {
                   if (response != null && response.getBackgroundColor().equals(AppColors.GRAY)) { // Check for gray letters
                       GrayLetterIndicator = true; 
                       break;
                   } else if (response.getBackgroundColor().equals(AppColors.GREEN) || response.getBackgroundColor().equals(AppColors.YELLOW)) {
                       GreenandYellowcount += 1;
                   }
                }
			
				if (FirstGuessIndicator && GrayLetterIndicator && (GreenandYellowcount < model.getColumnCount())) {
					view.updateHintButtonState(true, Color.WHITE);
				} else {
					view.updateHintButtonState(false, Color.GRAY);
				}
				
				for (WordleResponse wordleResponse : currentRow) {
					view.setColor(Character.toString(wordleResponse.getChar()),
							wordleResponse.getBackgroundColor(), 
							wordleResponse.getForegroundColor());
					if (wordleResponse.getBackgroundColor().equals(AppColors.GREEN)) {
						greenCount++;
					} 
				}
				
				if (greenCount >= model.getColumnCount()) {
					view.repaintWordleGridPanel();
					model.getStatistics().incrementTotalGamesPlayed();
					int currentRowNumber = model.getCurrentRowNumber();
					model.getStatistics().addWordsGuessed(currentRowNumber);
					int currentStreak = model.getStatistics().getCurrentStreak();
					model.getStatistics().setCurrentStreak(++currentStreak);
					new StatisticsDialog(view, model);
				} else if (!moreRows) {
					view.repaintWordleGridPanel();
					model.getStatistics().incrementTotalGamesPlayed();
					model.getStatistics().setCurrentStreak(0);
					new StatisticsDialog(view, model);
				} else {
					view.repaintWordleGridPanel();
				}
			}
			break;
		case "Backspace":
			model.backspace();
			view.repaintWordleGridPanel();
			break;
		default:
			model.setCurrentColumn(text.charAt(0));
			view.repaintWordleGridPanel();
			break;
		}
	}

}
