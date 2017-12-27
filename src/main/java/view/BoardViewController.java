package view;

import game.DiffEnum;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import layers.DomainCtrl;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class BoardViewController implements Initializable {

    private static int BLUE = 0;
    private static int PINK = 1;
    private static int ORANGE = 2;
    private static int YELLOW = 3;
    private static int GREEN = 4;
    private static int RED = 5;
    private static int VIOLET = 6;
    private static int BROWN = 7;


    private Paint selectedColor = Color.WHITE;
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

    private int selectedRow = 10;
    private DomainCtrl dCtrl;

    private ViewController vCtrl = new ViewController();
    private boolean allowRepeat;
    private int nColors;
    private int nColumns;
    private int nRows;
    private int fullRow = 0;
    private DiffEnum difficulty;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // blueCircle.setOnMousePressed(circleOnMousePressedEventHandler);
       // blueCircle.setOnMouseDragged(circleOnMouseDraggedEventHandler);
        mainGridPane.setOnMouseClicked(mainGridPaneOnMouseClicked);
        colorSelectionVBox.setOnMouseClicked(colorSelectionVBoxOnMouseClicked);
    }

    public void setDomainCtrl(DomainCtrl dCtrl) {
        this.dCtrl = dCtrl;
    }

    public void setDifficulty(DiffEnum difficulty) {
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
                allowRepeat = true;
                break;
        }
        this.difficulty = difficulty;
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
            Object source = event.getTarget();
            if (source instanceof Circle) {
                if (GridPane.getRowIndex((Node)source) == selectedRow) {
                    ((Circle)source).setFill(selectedColor);
                    if (fullRow < nColumns) {
                        checkButton.setDisable(false);
                    } else fullRow++;
                }
            }
        }
    };

    public void checkButtonAction(ActionEvent actionEvent) {
        ArrayList<Integer> newCombination = getCombination();
        System.out.print(newCombination);
        if (dCtrl.setNewCombination(newCombination)) {
            winLabel.setText("WIN");
            dCtrl.updatePlayerOnFinishGame(true);
            finishCBgame();
        }
        System.out.print("WinnerCombo:" + dCtrl.getWinnerCombinationArray() + "\n");
        System.out.print("WhitePegs: " + dCtrl.getWhitePegs(9 - selectedRow) + "\n");
        System.out.print("BlackPegs: " + dCtrl.getBlackPegs(9 - selectedRow) + "\n");
        paintCheckPegs();
        if (selectedRow > 1) {
            selectedRow--;
        } else {
            winLabel.setText("LOSE");
            dCtrl.updatePlayerOnFinishGame(false);
            finishCBgame();
        }
        checkButton.setDisable(true);
        fullRow = 0;
    }

    public void clue1ButtonAction(ActionEvent actionEvent) {
        int colorRemoved = dCtrl.useFirstClue();
        Circle selectedCircle = (Circle)colorSelectionVBox.getChildren().get(colorRemoved);
        selectedCircle.setDisable(true);
        selectedCircle.setFill(Color.GRAY);
        clue1Button.setDisable(true);
    }

    public void clue2ButtonAction(ActionEvent actionEvent) {
        dCtrl.useSecondClue();
        int clue2Position = dCtrl.getPositionClue();
        int clue2Color = dCtrl.getColorClue();
        for (int i = 0; i <= 10; i++) {
            Circle selectedCircle = (Circle)mainGridPane.getChildren().get(i * nColumns + clue2Position);
            selectedCircle.setFill(getColor(clue2Color));
            selectedCircle.setDisable(true);
        }
        clue2Button.setDisable(true);
    }

    public void saveGameButtonAction(ActionEvent actionEvent) {
        dCtrl.updatePlayerOnFinishGame(false);
    }

    public void newGameButtonAction(ActionEvent actionEvent) {
        dCtrl.startNewCodeBreaker(difficulty);
        selectedRow = 10;
        mainGridPane.getChildren().clear();
        checkGridPane.getChildren().clear();
        colorSelectionVBox.getChildren().clear();
        buildBoard();
        checkButton.setDisable(true);
        clue1Button.setDisable(false);
        clue2Button.setDisable(false);
        winLabel.setText("");
        elapsedTimeLabel.setText("");
        scoreLabel.setText("");
    }

    public void exitButtonAction(ActionEvent actionEvent) {
        vCtrl.loginView(dCtrl);
    }

    private void finishCBgame() {
        checkButton.setDisable(true);
        paintWinnerCombination();

        long time = dCtrl.setTimeElapsed();
        int seconds = (int) (TimeUnit.MILLISECONDS.toSeconds(time) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(time);

        double score = dCtrl.calculateScore();

        elapsedTimeLabel.setText(minutes + ":" + seconds);
        scoreLabel.setText(String.valueOf((int)score));

    }

    private void paintCheckPegs() {
        int nWhitePegs = dCtrl.getWhitePegs(10 - selectedRow);
        int nBlackPegs = dCtrl.getBlackPegs(10 - selectedRow);
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
    }

    private void paintWinnerCombination() {
        ArrayList<Integer> winnerCombination = dCtrl.getWinnerCombinationArray();
        for (int i = 0; i < nColumns; i++) {
            Circle selectedCircle = (Circle)mainGridPane.getChildren().get(i);
            selectedCircle.setFill(getColor(winnerCombination.get(i)));
        }
    }

    private ArrayList<Integer> getCombination() {
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
        return combination;
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
