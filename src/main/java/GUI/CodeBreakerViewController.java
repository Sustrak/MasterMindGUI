package GUI;

import game.DiffEnum;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.util.Duration;
import layers.DomainCtrl;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class CodeBreakerViewController implements Initializable {

    private DomainCtrl domainCtrl;

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
    public Label timerLabel;

    private String selectedColor = "";
    private boolean allowRepeat;
    private int nColors;
    private int nColumns;
    private int nRows;
    private int selectedRow;
    private int seconds;

    private Timeline secondUpdate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainGridPane.setOnMouseClicked(mainGridPaneOnMouseClicked);
        colorSelectionVBox.setOnMouseClicked(colorSelectionVBoxOnMouseClicked);

        //Timer
        seconds = -1;
        
        NumberFormat f = new DecimalFormat("00");
        secondUpdate = new Timeline(new KeyFrame(Duration.seconds(1), event -> timerLabel.setText("" + f.format(TimeUnit.SECONDS.toMinutes(++seconds)) + " : " + f.format(seconds%60))));
        secondUpdate.setCycleCount(Timeline.INDEFINITE);
    }

    public void setDomainCtrl(DomainCtrl domainCtrl) {
        this.domainCtrl = domainCtrl;
    }

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
        System.out.print("WhitePegs: " + domainCtrl.getWhitePegs(10 - selectedRow) + "\n");
        System.out.print("BlackPegs: " + domainCtrl.getBlackPegs(10 - selectedRow) + "\n");
        paintCheckPegs(selectedRow);
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
        domainCtrl.useFirstClue();
        setClue1();
    }

    public void clue2ButtonAction(ActionEvent actionEvent) {
        domainCtrl.useSecondClue();
        setClue2();
    }

    public void saveGameButtonAction(ActionEvent actionEvent) {
        domainCtrl.saveGame();
        ViewController.showInformationMessageSeconds("Partida guardada correctamente", 1);
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

    public void newGame() {
        DiffEnum difficulty = ViewController.askCodeBreakerDifficulty();
        setDifficulty(difficulty);
        domainCtrl.startNewCodeBreaker(difficulty);
        selectedColor = "";
        selectedRow = 10;
        checkButton.setDisable(true);
        saveGameButton.setDisable(false);
        clue1Button.setDisable(false);
        clue2Button.setDisable(false);
        winLabel.setText("");
        elapsedTimeLabel.setText("");
        scoreLabel.setText("");
        timerLabel.setText("00 : 00");
        seconds = 0;
        secondUpdate.play();
        buildBoard();
    }

    public void loadGame(int gameId) {
        try {
            domainCtrl.loadGame(gameId);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setDifficulty(domainCtrl.getDifficulty());
        buildBoard();
        selectedColor = "";
        selectedRow = 10 - domainCtrl.getNCombinations();
        System.out.print("selectedRow: " + selectedRow + "\n");
        System.out.print("getNCombinations: " + domainCtrl.getNCombinations() + "\n");

        checkButton.setDisable(true);
        saveGameButton.setDisable(false);
        if (domainCtrl.firstClueUsed()) {
            clue1Button.setDisable(true);
            setClue1();
        }
        if (domainCtrl.secondClueUsed()) {
            clue2Button.setDisable(true);
            setClue2();
        }
        winLabel.setText("");
        elapsedTimeLabel.setText("");
        scoreLabel.setText("");
        loadBuildBoard();
    }

    private void setDifficulty(DiffEnum difficulty) {
        switch (difficulty) {
            case EASY:
                nColumns = 4;
                nRows = 11;
                nColors = 6;
                allowRepeat = false;
                break;
            case ORIGINAL:
                nColumns = 4;
                nRows = 11;
                nColors = 6;
                allowRepeat = true;
                break;
            case HARD:
                nColumns = 6;
                nRows = 11;
                nColors = 8;
                break;
        }
    }

    private void buildBoard() {
        mainGridPane.getChildren().clear();
        checkGridPane.getChildren().clear();
        colorSelectionVBox.getChildren().clear();
        Circle circle;
        for (int i = 0; i < nColors; ++i) {
            circle = new Circle(30.0);
            circle.setId(BoardViewsUtils.getColorId(i) + "Circle");
            colorSelectionVBox.getChildren().add(i, circle);
        }
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                circle = new Circle(20.0);
                circle.setId("whiteCircle");
                mainGridPane.add(circle, j, i);
            }
        }

        Label winComb = new Label();
        winComb.setText("Combinación Ganadora");
        winComb.setId("winComb");
        winComb.setWrapText(true);
        checkGridPane.add(winComb, 0, 0, nColumns, 1);

        for (int i = 1; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                circle = new Circle(10.0);
                circle.setId("grayCheckPeg");
                checkGridPane.add(circle, j, i);
            }
        }
    }

    private void setClue1() {
        int colorRemoved = domainCtrl.getColorRemoved();
        Circle selectedCircle = (Circle) colorSelectionVBox.getChildren().get(colorRemoved);
        selectedCircle.setDisable(true);
        selectedCircle.setId("grayCircle");
        clue1Button.setDisable(true);
    }

    private void setClue2() {
        int clue2Position = domainCtrl.getPositionClue();
        int clue2Color = domainCtrl.getColorClue();
        for (int i = 0; i <= 10; i++) {
            Circle selectedCircle = (Circle) mainGridPane.getChildren().get(i * nColumns + clue2Position);
            selectedCircle.setId(BoardViewsUtils.getColorId(clue2Color) + "Circle");
            selectedCircle.setDisable(true);
        }
        clue2Button.setDisable(true);
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

        double score = domainCtrl.getScore();

        elapsedTimeLabel.setText(minutes + ":" + seconds);
        scoreLabel.setText(String.valueOf((int) score));

        secondUpdate.stop();

    }

    private boolean checkFullRow() {
        for (int i = 0; i < nColumns; i++) {
            Circle selectedCircle = (Circle) mainGridPane.getChildren().get(selectedRow * nColumns + i);
            if (selectedCircle.getId().equals("whiteCircle")) return false;
        }
        return true;
    }

    private void paintCheckPegs(int row) {
        
        int nWhitePegs = domainCtrl.getWhitePegs(10 - row);
        int nBlackPegs = domainCtrl.getBlackPegs(10 - row);
        int i = 0;
        while (nBlackPegs > 0) {
            Circle selectedCircle = (Circle) checkGridPane.getChildren().get((row-1) * nColumns + i + 1);
            selectedCircle.setId("blackCheckPeg");
            nBlackPegs--;
            i++;
        }
        while (nWhitePegs > 0) {
            Circle selectedCircle = (Circle) checkGridPane.getChildren().get((row-1) * nColumns + i + 1);
            selectedCircle.setId("whiteCheckPeg");
            nWhitePegs--;
            i++;
        }
    }

    private void paintWinnerCombination() {
        ArrayList<Integer> winnerCombination = domainCtrl.getWinnerCombinationArray();
        for (int i = 0; i < nColumns; i++) {
            Circle selectedCircle = (Circle) mainGridPane.getChildren().get(i);
            selectedCircle.setId(BoardViewsUtils.getColorId(winnerCombination.get(i)) + "Circle");
        }
    }

    private ArrayList<Integer> getCombination() {
        ArrayList<Integer> combination = new ArrayList<>();
        for (int i = 0; i < nColumns; i++) {
            Circle selectedCircle = (Circle) mainGridPane.getChildren().get(selectedRow * nColumns + i);
            if (selectedCircle.getId().equals("blueCircle")) combination.add(0);
            else if (selectedCircle.getId().equals("pinkCircle")) combination.add(1);
            else if (selectedCircle.getId().equals("orangeCircle")) combination.add(2);
            else if (selectedCircle.getId().equals("yellowCircle")) combination.add(3);
            else if (selectedCircle.getId().equals("greenCircle")) combination.add(4);
            else if (selectedCircle.getId().equals("redCircle")) combination.add(5);
            else if (selectedCircle.getId().equals("violetCircle")) combination.add(6);
            else if (selectedCircle.getId().equals("brownCircle")) combination.add(7);
        }
        return combination;
    }

    private void loadBuildBoard() {
        for (int i = 0; i < domainCtrl.getNCombinations(); i++) {
            ArrayList<Integer> combination = domainCtrl.getCombination(i);
            for (int j = 0; j < nColumns; j++) {
                Circle selectedCircle = (Circle)mainGridPane.getChildren().get((10-i) * nColumns + j);
                selectedCircle.setId(BoardViewsUtils.getColorId(combination.get(j)) + "Circle");
            }
            paintCheckPegs(10 - i);
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
}
