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


    private String selectedColor;
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
        for (int i = 0; i < nRows -1; i++) {
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
            selectedColor = ((Circle)source).getId();
        }
    };

    private EventHandler<MouseEvent> mainGridPaneOnMouseClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (first) {
                Object source = event.getTarget();
                if (source instanceof Circle) {
                    if (GridPane.getRowIndex((Node) source) == 0) {
                        ((Circle) source).setId(selectedColor);
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
                        ((Circle) source).setId(selectedColor);
                        if (fullRow < nColumns) {
                            solveButton.setDisable(false);
                        } else fullRow++;
                    }
                }
            }
        }
    };

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

    private void paintNewCombination() {
        ArrayList<Integer> newComb = domainCtrl.getNewCodeMakerComb();
        domainCtrl.setNewCombination(newComb);
        for (int i = 0; i < nColumns; i++) {
            Circle selectedCircle = (Circle)mainGridPane.getChildren().get(selectedRow * nColumns + i);
            selectedCircle.setId(getColorId(newComb.get(i)) + "Circle");
        }
    }

    private Boolean checkCorrectionCombination() {
        int blackPegs = 0;
        int whitePegs = 0;
        for (int i = 0; i < nColumns; i++) {
            Circle selectedCircle = (Circle) checkGridPane.getChildren().get((selectedRow) * nColumns + i);
            if (selectedCircle.getId().equals("blackCheckPeg")) blackPegs++;
            else if (selectedCircle.getId().equals("whiteCheckPeg")) whitePegs++;
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
            if (selectedCircle.getId().equals("blueCircle")) combination.add(BLUE);
            else if (selectedCircle.getId().equals("pinkCircle")) combination.add(PINK);
            else if (selectedCircle.getId().equals("orangeCircle")) combination.add(ORANGE);
            else if (selectedCircle.getId().equals("yellowCircle")) combination.add(YELLOW);
            else if (selectedCircle.getId().equals("greenCircle")) combination.add(GREEN);
            else if (selectedCircle.getId().equals("redCircle")) combination.add(RED);
            else if (selectedCircle.getId().equals("violetCircle")) combination.add(VIOLET);
            else if (selectedCircle.getId().equals("brownCircle")) combination.add(BROWN);
        }
        domainCtrl.setWinnerCombination(combination);
    }

    private void changeColors() {
        colorSelectionVBox.getChildren().clear();
        Circle circle;
        circle = new Circle(16.0, Color.BLACK);
        circle.setId("blackCheckPeg");
        colorSelectionVBox.getChildren().add(0, circle);
        circle = new Circle(16.0, Color.WHITE);
        circle.setId("whiteCheckPeg");
        colorSelectionVBox.getChildren().add(1, circle);
        circle = new Circle(16.0, Color.GRAY);
        circle.setId("grayCheckPeg");
        colorSelectionVBox.getChildren().add(2, circle);
    }
}
