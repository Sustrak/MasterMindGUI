package GUI;

import game.DiffEnum;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import layers.DomainCtrl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CodeMakerViewController implements Initializable {

    private DomainCtrl domainCtrl;

    public GridPane mainGridPane;
    public GridPane checkGridPane;
    public VBox colorSelectionVBox;
    public Button solveButton;
    public Button newGameButton;
    public Button exitButton;

    private String selectedColor = "";
    private int nColors;
    private int nColumns;
    private int nRows;
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
        selectedColor = "";
        solveButton.setDisable(true);
        selectedRow = 0;
        nAttemps = 1;
        first = true;
        solved = false;
        buildBoard();
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
        }
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

    private boolean checkFullRow(GridPane gridPane) {
        for (int i = 0; i < nColumns; i++) {
            Circle selectedCircle = (Circle) gridPane.getChildren().get(selectedRow * nColumns + i);
            if (selectedCircle.getId().equals("whiteCircle")) return false;
        }
        return true;
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

    private void paintNewCombination() {
        ArrayList<Integer> newComb = domainCtrl.getNewCodeMakerComb();
        domainCtrl.setNewCombination(newComb);
        for (int i = 0; i < nColumns; i++) {
            Circle selectedCircle = (Circle)mainGridPane.getChildren().get(selectedRow * nColumns + i);
            selectedCircle.setId(BoardViewsUtils.getColorId(newComb.get(i)) + "Circle");
        }
    }

    private Boolean checkCorrectionCombination() {
        int blackPegs = 0;
        int whitePegs = 0;
        for (int i = 0; i < nColumns; i++) {
            Circle selectedCircle = (Circle) checkGridPane.getChildren().get((selectedRow) * nColumns + i + 1);
            System.out.println(selectedRow);
            System.out.println((selectedRow) * nColumns + i + 1);
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

    private void endGame() {
        solveButton.setDisable(true);
        ViewController.showInformationMessage("Breaked in " + nAttemps + "!!!.");
    }

    private void setWinnerCombination() {
        ArrayList<Integer> combination = new ArrayList<>();
        for (int i = 0; i < nColumns; i++) {
            Circle selectedCircle = (Circle)mainGridPane.getChildren().get(selectedRow* nColumns +i);
            if (selectedCircle.getId().equals("blueCircle")) combination.add(0);
            else if (selectedCircle.getId().equals("pinkCircle")) combination.add(1);
            else if (selectedCircle.getId().equals("orangeCircle")) combination.add(2);
            else if (selectedCircle.getId().equals("yellowCircle")) combination.add(3);
            else if (selectedCircle.getId().equals("greenCircle")) combination.add(4);
            else if (selectedCircle.getId().equals("redCircle")) combination.add(5);
            else if (selectedCircle.getId().equals("violetCircle")) combination.add(6);
            else if (selectedCircle.getId().equals("brownCircle")) combination.add(7);
        }
        domainCtrl.setWinnerCombination(combination);
    }

    private void changeColors() {
        colorSelectionVBox.getChildren().clear();
        Circle circle;
        circle = new Circle(16.0);
        circle.setId("blackCheckPeg");
        colorSelectionVBox.getChildren().add(0, circle);
        circle = new Circle(16.0);
        circle.setId("whiteCheckPeg");
        colorSelectionVBox.getChildren().add(1, circle);
        circle = new Circle(16.0);
        circle.setId("grayCheckPeg");
        colorSelectionVBox.getChildren().add(2, circle);
        selectedColor = "";
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
                    if (GridPane.getRowIndex((Node) source) == 0 && !selectedColor.isEmpty()) {
                        ((Circle) source).setId(selectedColor);
                        if (checkFullRow(mainGridPane)) solveButton.setDisable(false);
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
                    if (GridPane.getRowIndex((Node) source) == selectedRow+1 && !selectedColor.isEmpty()) {
                        ((Circle) source).setId(selectedColor);
                    }
                }
            }
        }
    };
}
