package view;

import game.DiffEnum;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import layers.DomainCtrl;

import javax.swing.text.View;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class CodeMakerViewController implements Initializable {

    private DomainCtrl domainCtrl;

    private static final int BLUE = 0;
    private static final int PINK = 1;
    private static final int ORANGE = 2;
    private static final int YELLOW = 3;
    private static final int GREEN = 4;
    private static final int RED = 5;
    private static final int VIOLET = 6;
    private static final int BROWN = 7;


    private Paint selectedColor = Color.WHITE;
    public GridPane mainGridPane;
    public GridPane checkGridPane;
    public VBox colorSelectionVBox;
    public Button solveButton;
    public Button newGameButton;
    public Button exitButton;

    private int nColors;
    private int nColumns;
    private int nRows;
    private int fullRow = 0;
    private int selectedRow;
    private int nAttemps;
    private Boolean first;
    private Boolean solved;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainGridPane.setOnMouseClicked(mainGridPaneOnMouseClicked);
        colorSelectionVBox.setOnMouseClicked(colorSelectionVBoxOnMouseClicked);
        checkGridPane.setOnMouseClicked(checkGridPaneOnMouseClicked);
    }

    public void setDomainCtrl(DomainCtrl domainCtrl) {
        this.domainCtrl = domainCtrl;
        newGame();
    }

    public void newGame() {
        switch (ViewController.askCodeMakerDifficulty()) {
            case ORIGINAL:
                domainCtrl.startNewCodeMaker(DiffEnum.ORIGINAL);
                nColumns = 4;
                nRows = 11;
                nColors = 6;
                break;
            case HARD:
                nColumns = 6;
                nRows = 11;
                nColors = 8;
                domainCtrl.startNewCodeMaker(DiffEnum.HARD);
                break;
        }
        selectedRow = 0;
        nAttemps = 0;
        first = true;
        solved = false;
        buildBoard();
    }

    private Color getColor(int i) {
        switch (i) {
            case 0:
                return Color.BLUE;
            case 1:
                return Color.HOTPINK;
            case 2:
                return Color.ORANGE;
            case 3:
                return Color.YELLOW;
            case 4:
                return Color.GREEN;
            case 5:
                return Color.RED;
            case 6:
                return Color.DARKVIOLET;
            case 7:
                return Color.BROWN;
            default:
                return Color.WHITE;
        }
    }

    private void buildBoard() {
        mainGridPane.getChildren().clear();
        checkGridPane.getChildren().clear();
        colorSelectionVBox.getChildren().clear();
        Circle circle;
        for (int i = 0; i < nColors; ++i) {
            circle = new Circle(16.0, getColor(i));
            circle.setId("colorSelectionCircle" + i);
            colorSelectionVBox.getChildren().add(i, circle);
        }
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                circle = new Circle(16.0, Color.WHITE);
                circle.setStroke(Color.BLACK);
                circle.setId("emptyCircle" + i + j);
                mainGridPane.add(circle, j, i);
            }
        }
        for (int i = 0; i < nRows -1; i++) {
            for (int j = 0; j < nColumns; j++) {
                circle = new Circle(8.0, Color.GRAY);
                circle.setStroke(Color.BLACK);
                circle.setId("checkPeg" + i + j);
                checkGridPane.add(circle, j, i);
            }
        }
    }

    private EventHandler<MouseEvent> colorSelectionVBoxOnMouseClicked = event -> {
        Object source = event.getTarget();
        if (source instanceof Circle) {
            selectedColor = ((Circle)source).getFill();
        }
    };

    private EventHandler<MouseEvent> mainGridPaneOnMouseClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (first) {
                Object source = event.getTarget();
                if (source instanceof Circle) {
                    if (GridPane.getRowIndex((Node) source) == 0) {
                        ((Circle) source).setFill(selectedColor);
                        if (fullRow < nColumns) {
                            solveButton.setDisable(false);
                        } else fullRow++;
                    }
                }
            }
        }
    };

    private EventHandler<MouseEvent> checkGridPaneOnMouseClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (!first) {
                Object source = event.getTarget();
                if (source instanceof Circle) {
                    if (GridPane.getRowIndex((Node) source) == selectedRow) {
                        ((Circle) source).setFill(selectedColor);
                        if (fullRow < nColumns) {
                            solveButton.setDisable(false);
                        } else fullRow++;
                    }
                }
            }
        }
    };

    /*public void checkButtonAction(ActionEvent actionEvent) {
        ArrayList<Integer> newCombination = getCombination();
        System.out.print(newCombination);
        if (domainCtrl.setNewCombination(newCombination)) {
            winLabel.setText("WIN");
            domainCtrl.updatePlayerOnFinishGame(true);
            finishCBgame();
        }
        System.out.print("WinnerCombo:" + domainCtrl.getWinnerCombinationArray() + "\n");
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
        solveButton.setDisable(true);
        fullRow = 0;
    }*/

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

    /*private void finishCBgame() {
        solveButton.setDisable(true);
        paintWinnerCombination();

        long time = domainCtrl.setTimeElapsed();
        int seconds = (int) (TimeUnit.MILLISECONDS.toSeconds(time) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(time);

        double score = domainCtrl.calculateScore();

        elapsedTimeLabel.setText(minutes + ":" + seconds);
        scoreLabel.setText(String.valueOf((int)score));

    }*/

    /*private void paintCheckPegs() {
        int nWhitePegs = domainCtrl.getWhitePegs(10 - selectedRow);
        int nBlackPegs = domainCtrl.getBlackPegs(10 - selectedRow);
        int i = 0;
        while (nBlackPegs > 0) {
            Circle selectedCircle = (Circle)checkGridPane.getChildren().get((selectedRow-1)* nColumns +i);
            selectedCircle.setFill(Color.BLACK);
            nBlackPegs--;
            i++;
        }
        while (nWhitePegs > 0) {
            Circle selectedCircle = (Circle)checkGridPane.getChildren().get((selectedRow-1)* nColumns +i);
            selectedCircle.setFill(Color.WHITE);
            nWhitePegs--;
            i++;
        }
    }*/

    private void paintNewCombination() {
        ArrayList<Integer> newComb = domainCtrl.getNewCodeMakerComb();
        domainCtrl.setNewCombination(newComb);
        for (int i = 0; i < nColumns; i++) {
            Circle selectedCircle = (Circle)mainGridPane.getChildren().get(selectedRow * nColumns + i);
            selectedCircle.setFill(getColor(newComb.get(i)));
        }
    }

    private Boolean checkCorrectionCombination() {
        int blackPegs = 0;
        int whitePegs = 0;
        for (int i = 0; i < nColumns; i++) {
            Circle selectedCircle = (Circle) checkGridPane.getChildren().get((selectedRow) * nColumns + i);
            if (selectedCircle.getFill().equals(Color.BLACK)) blackPegs++;
            else if (selectedCircle.getFill().equals(Color.WHITE)) whitePegs++;
        }
        if (domainCtrl.isGoodCorrection(blackPegs, whitePegs)) {
            System.out.print("black: " + blackPegs + "\n");
            System.out.print("white:" + whitePegs + "\n");
            if (blackPegs == domainCtrl.getNPieces()) solved = true;
            return true;
        } else {
            ViewController.showErrorMessage("La corrección introducida no es correcta.");
            return false;
        }
    }

    public void solveButtonAction(ActionEvent actionEvent) {
        if (first) {
            first = false;
            setWinnerCombination();
            selectedRow = 10;
            changeColors();
            paintNewCombination();
            selectedRow--;
        } else if (checkCorrectionCombination()) {
            if (solved) endGame();
            else paintNewCombination();
            selectedRow--;
            nAttemps++;
        } else checkCorrectionCombination();
    }

    private void endGame() {
        solveButton.setDisable(true);
        ViewController.showInformationMessage("Breaked in " + nAttemps + "!!!.");
    }

    private void setWinnerCombination() {
        ArrayList<Integer> combination = new ArrayList<>();
        for (int i = 0; i < nColumns; i++) {
            Circle selectedCircle = (Circle)mainGridPane.getChildren().get(selectedRow* nColumns +i);
            if (selectedCircle.getFill().equals(Color.BLUE)) combination.add(BLUE);
            else if (selectedCircle.getFill().equals(Color.HOTPINK)) combination.add(PINK);
            else if (selectedCircle.getFill().equals(Color.ORANGE)) combination.add(ORANGE);
            else if (selectedCircle.getFill().equals(Color.YELLOW)) combination.add(YELLOW);
            else if (selectedCircle.getFill().equals(Color.GREEN)) combination.add(GREEN);
            else if (selectedCircle.getFill().equals(Color.RED)) combination.add(RED);
            else if (selectedCircle.getFill().equals(Color.DARKVIOLET)) combination.add(VIOLET);
            else if (selectedCircle.getFill().equals(Color.BROWN)) combination.add(BROWN);
        }
        domainCtrl.setWinnerCombination(combination);
    }

    private void changeColors() {
        colorSelectionVBox.getChildren().clear();
        Circle circle;
        circle = new Circle(16.0, Color.BLACK);
        circle.setId("colorSelectionBlackCircle");
        colorSelectionVBox.getChildren().add(0, circle);
        circle = new Circle(16.0, Color.WHITE);
        circle.setId("colorSelectionWhiteCircle");
        colorSelectionVBox.getChildren().add(1, circle);
        circle = new Circle(16.0, Color.GRAY);
        circle.setId("colorSelectionWhiteCircle");
        colorSelectionVBox.getChildren().add(2, circle);
    }

    /*public void selectBlueColor() {
        this.selectedColor = Color.BLUE;
        System.out.print(this.selectedColor);
    }*/

    /*EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((Circle)(t.getSource())).getTranslateX();
                    orgTranslateY = ((Circle)(t.getSource())).getTranslateY();
                }
            };

    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((Circle)(t.getSource())).setTranslateX(newTranslateX);
                    ((Circle)(t.getSource())).setTranslateY(newTranslateY);
                }
            };*/
}
