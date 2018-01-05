package view;

import game.DiffEnum;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import layers.DomainCtrl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class BoardViewController implements Initializable {

    private DomainCtrl domainCtrl;

    private static final int BLUE = 0;
    private static final int PINK = 1;
    private static final int ORANGE = 2;
    private static final int YELLOW = 3;
    private static final int GREEN = 4;
    private static final int RED = 5;
    private static final int VIOLET = 6;
    private static final int BROWN = 7;


    private String selectedColor = "";
    public GridPane mainGridPane;
    public GridPane checkGridPane;
    public VBox colorSelectionVBox;
    public VBox buttonsVBox;
    public Button checkButton;
    public Button clue1Button;
    public Button clue2Button;
    public Button saveGameButton;
    public Button newGameButton;
    public Button exitButton;
    public Label elapsedTimeLabel;
    public Label scoreLabel;
    public Label winLabel;

    private boolean allowRepeat;
    private int nColors;
    private int nColumns;
    private int nRows;
    private int selectedRow = 10;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainGridPane.setOnMouseClicked(mainGridPaneOnMouseClicked);
        colorSelectionVBox.setOnMouseClicked(colorSelectionVBoxOnMouseClicked);
    }

    public void setDomainCtrl(DomainCtrl domainCtrl) {
        this.domainCtrl = domainCtrl;
        newGame();
    }

    public void newGame() {
        switch (ViewController.askCodeBreakerDifficulty()) {
            case EASY:
                domainCtrl.startNewCodeBreaker(DiffEnum.EASY);
                nColumns = 4;
                nRows = 11;
                nColors = 6;
                allowRepeat = false;
                break;
            case ORIGINAL:
                domainCtrl.startNewCodeBreaker(DiffEnum.ORIGINAL);
                nColumns = 4;
                nRows = 11;
                nColors = 6;
                allowRepeat = true;
                break;
            case HARD:
                domainCtrl.startNewCodeBreaker(DiffEnum.HARD);
                nColumns = 6;
                nRows = 11;
                nColors = 8;
                break;
        }
        selectedColor = "";
        selectedRow = 10;
        checkButton.setDisable(true);
        saveGameButton.setDisable(false);
        clue1Button.setDisable(false);
        clue2Button.setDisable(false);
        winLabel.setText("");
        elapsedTimeLabel.setText("");
        scoreLabel.setText("");
        buildBoard();
    }

    private String getColorId(int i) {
        switch (i) {
            case 0:
                return "blue";
            case 1:
                return "pink";
            case 2:
                return "orange";
            case 3:
                return "yellow";
            case 4:
                return "green";
            case 5:
                return "red";
            case 6:
                return "violet";
            case 7:
                return "brown";
            default:
                return "white";
        }
    }

    private void buildBoard() {
        mainGridPane.getChildren().clear();
        checkGridPane.getChildren().clear();
        colorSelectionVBox.getChildren().clear();
        Circle circle;
        for (int i = 0; i < nColors; ++i) {
            circle = new Circle(30.0);
            circle.setId(getColorId(i) + "Circle");
            colorSelectionVBox.getChildren().add(i, circle);
        }
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                circle = new Circle(20.0);
                circle.setId("whiteCircle");
                mainGridPane.add(circle, j, i);
            }
        }
        for (int i = 0; i < nRows - 1; i++) {
            for (int j = 0; j < nColumns; j++) {
                circle = new Circle(10.0);
                circle.setId("grayCheckPeg");
                checkGridPane.add(circle, j, i);
            }
        }
    }

    private EventHandler<MouseEvent> colorSelectionVBoxOnMouseClicked = event -> {
        Object source = event.getTarget();
        if (source instanceof Circle) {
            selectedColor = ((Circle) source).getId();
        }
    };

    private EventHandler<MouseEvent> mainGridPaneOnMouseClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Object source = event.getTarget();
            if (source instanceof Circle) {
                if (GridPane.getRowIndex((Node) source) == selectedRow && !selectedColor.isEmpty()) {
                    ((Circle) source).setId(selectedColor);
                    if (checkFullRow()) checkButton.setDisable(false);
                }
            }
        }
    };

    public void checkButtonAction(ActionEvent actionEvent) {
        ArrayList<Integer> newCombination = getCombination();
        System.out.print(newCombination);
        if (domainCtrl.setNewCombination(newCombination)) {
            winLabel.setText("WIN");
            domainCtrl.updatePlayerOnFinishGame(true);
            finishCBgame();
        }
        System.out.print("WinnerCombo:" + domainCtrl.getWinnerCombinationArray() + "\n");
        System.out.print("newCombination: " + newCombination + "\n");
        System.out.print("WhitePegs: " + domainCtrl.getWhitePegs(9 - selectedRow) + "\n");
        System.out.print("BlackPegs: " + domainCtrl.getBlackPegs(9 - selectedRow) + "\n");
        paintCheckPegs();
        if (selectedRow > 1) {
            selectedRow--;
        } else {
            winLabel.setText("LOSE");
            domainCtrl.updatePlayerOnFinishGame(false);
            finishCBgame();
        }
        checkButton.setDisable(true);
    }

    public void clue1ButtonAction(ActionEvent actionEvent) {
        int colorRemoved = domainCtrl.useFirstClue();
        Circle selectedCircle = (Circle) colorSelectionVBox.getChildren().get(colorRemoved);
        selectedCircle.setDisable(true);
        selectedCircle.setId("grayCircle");
        clue1Button.setDisable(true);
    }

    public void clue2ButtonAction(ActionEvent actionEvent) {
        domainCtrl.useSecondClue();
        int clue2Position = domainCtrl.getPositionClue();
        int clue2Color = domainCtrl.getColorClue();
        for (int i = 0; i <= 10; i++) {
            Circle selectedCircle = (Circle) mainGridPane.getChildren().get(i * nColumns + clue2Position);
            selectedCircle.setId(getColorId(clue2Color) + "Circle");
            selectedCircle.setDisable(true);
        }
        clue2Button.setDisable(true);
    }

    public void saveGameButtonAction(ActionEvent actionEvent) {
        domainCtrl.saveGame();
    }

    public void newGameButtonAction(ActionEvent actionEvent) {
        newGame();
    }

    public void exitButtonAction(ActionEvent actionEvent) {
        try {
            ViewController.mainMenuView(domainCtrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void finishCBgame() {
        checkButton.setDisable(true);
        clue2Button.setDisable(true);
        clue1Button.setDisable(true);
        saveGameButton.setDisable(true);
        paintWinnerCombination();

        long time = domainCtrl.setTimeElapsed();
        int seconds = (int) (TimeUnit.MILLISECONDS.toSeconds(time) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(time);

        double score = domainCtrl.calculateScore();

        elapsedTimeLabel.setText(minutes + ":" + seconds);
        scoreLabel.setText(String.valueOf((int) score));

    }

    private boolean checkFullRow() {
        for (int i = 0; i < nColumns; i++) {
            Circle selectedCircle = (Circle) mainGridPane.getChildren().get(selectedRow * nColumns + i);
            if (selectedCircle.getId().equals("whiteCircle")) return false;
        }
        return true;
    }

    private void paintCheckPegs() {
        int nWhitePegs = domainCtrl.getWhitePegs(10 - selectedRow);
        int nBlackPegs = domainCtrl.getBlackPegs(10 - selectedRow);
        int i = 0;
        while (nBlackPegs > 0) {
            Circle selectedCircle = (Circle) checkGridPane.getChildren().get((selectedRow - 1) * nColumns + i);
            selectedCircle.setId("blackCheckPeg");
            nBlackPegs--;
            i++;
        }
        while (nWhitePegs > 0) {
            Circle selectedCircle = (Circle) checkGridPane.getChildren().get((selectedRow - 1) * nColumns + i);
            selectedCircle.setId("whiteCheckPeg");
            nWhitePegs--;
            i++;
        }
    }

    private void paintWinnerCombination() {
        ArrayList<Integer> winnerCombination = domainCtrl.getWinnerCombinationArray();
        for (int i = 0; i < nColumns; i++) {
            Circle selectedCircle = (Circle) mainGridPane.getChildren().get(i);
            selectedCircle.setId(getColorId(winnerCombination.get(i)) + "Circle");
        }
    }

    private ArrayList<Integer> getCombination() {
        ArrayList<Integer> combination = new ArrayList<>();
        for (int i = 0; i < nColumns; i++) {
            Circle selectedCircle = (Circle) mainGridPane.getChildren().get(selectedRow * nColumns + i);
            if (selectedCircle.getId().equals("blueCircle")) combination.add(BLUE);
            else if (selectedCircle.getId().equals("pinkCircle")) combination.add(PINK);
            else if (selectedCircle.getId().equals("orangeCircle")) combination.add(ORANGE);
            else if (selectedCircle.getId().equals("yellowCircle")) combination.add(YELLOW);
            else if (selectedCircle.getId().equals("greenCircle")) combination.add(GREEN);
            else if (selectedCircle.getId().equals("redCircle")) combination.add(RED);
            else if (selectedCircle.getId().equals("violetCircle")) combination.add(VIOLET);
            else if (selectedCircle.getId().equals("brownCircle")) combination.add(BROWN);
        }
        return combination;
    }


}
