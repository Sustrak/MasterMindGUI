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

    public int BLUE = 0;
    public int PINK = 1;
    public int ORANGE = 2;
    public int YELLOW = 3;
    public int GREEN = 4;
    public int RED = 5;


    public Paint selectedColor;
    public GridPane mainGridPane;
    public GridPane checkGridPane;
    public VBox colorSelectionVBox;
    public Button checkButton;
    public Button clue1Button;
    public Button clue2Button;
    public Button saveGameButton;
    public Button newGameButton;
    public Label elapsedTimeLabel;
    public Label scoreLabel;
    public Label winLabel;
    public int selectedRow = 10;
    public DomainCtrl dCtrl;

    private ViewController vCtrl = new ViewController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // blueCircle.setOnMousePressed(circleOnMousePressedEventHandler);
       // blueCircle.setOnMouseDragged(circleOnMouseDraggedEventHandler);
        mainGridPane.setOnMouseClicked(mainGridPaneOnMouseClicked);
        colorSelectionVBox.setOnMouseClicked(colorSelectionVBoxOnMouseClicked);
        checkButton.setOnAction(checkButtonAction);
        clue1Button.setOnAction(clue1ButtonAction);
        clue2Button.setOnAction(clue2ButtonAction);
        saveGameButton.setOnAction(saveGameButtonAction);
        newGameButton.setOnAction(newGameButtonAction);
    }

    public void setDctrl(DomainCtrl dCtrl) {
        this.dCtrl = dCtrl;
    }

    EventHandler<MouseEvent> mainGridPaneOnMouseClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Object source = event.getTarget();
            if (source instanceof Circle) {
                if (GridPane.getRowIndex((Node)source) == selectedRow) {
                    ((Circle)source).setFill(selectedColor);
                }
            }
        }
    };

    EventHandler<MouseEvent> colorSelectionVBoxOnMouseClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Object source = event.getTarget();
            if (source instanceof Circle) {
                selectedColor = ((Circle)source).getFill();
            }
        }
    };

    EventHandler<ActionEvent> checkButtonAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            ArrayList<Integer> newCombination = getCombination();
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
        }
    };

    EventHandler<ActionEvent> clue1ButtonAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            int colorRemoved = dCtrl.useFirstClue();
            Circle selectedCircle = (Circle)colorSelectionVBox.getChildren().get(colorRemoved);
            selectedCircle.setDisable(true);
            selectedCircle.setFill(Color.GRAY);
            clue1Button.setDisable(true);
        }
    };

    EventHandler<ActionEvent> clue2ButtonAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            dCtrl.useSecondClue();
            int clue2Position = dCtrl.getPositionClue();
            int clue2Color = dCtrl.getColorClue();
            for (int i = 0; i <= 10; i++) {
                Circle selectedCircle = (Circle)mainGridPane.getChildren().get(i*4+clue2Position);
                switch (clue2Color) {
                    case 0:
                        selectedCircle.setFill(Color.BLUE);
                        break;
                    case 1:
                        selectedCircle.setFill(Color.HOTPINK);
                        break;
                    case 2:
                        selectedCircle.setFill(Color.ORANGE);
                        break;
                    case 3:
                        selectedCircle.setFill(Color.YELLOW);
                        break;
                    case 4:
                        selectedCircle.setFill(Color.GREEN);
                        break;
                    case 5:
                        selectedCircle.setFill(Color.RED);
                        break;
                }
                selectedCircle.setDisable(true);
            }
            clue2Button.setDisable(true);
        }
    };

    EventHandler<ActionEvent> saveGameButtonAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            dCtrl.updatePlayerOnFinishGame(false);
        }
    };

    EventHandler<ActionEvent> newGameButtonAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            dCtrl.startNewCodeBreaker(DiffEnum.EASY);
            selectedRow = 10;
            resetBoard();
            checkButton.setDisable(false);
            clue1Button.setDisable(false);
            clue2Button.setDisable(false);
            winLabel.setText("");
            elapsedTimeLabel.setText("");
            scoreLabel.setText("");

            System.out.print(dCtrl.getWinnerCombinationArray());

        }
    };

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

    private void resetBoard() {
        for (int i = 0; i < 44; i++) {
            Circle selectedCircle = (Circle)mainGridPane.getChildren().get(i);
            selectedCircle.setFill(Color.WHITE);
            selectedCircle.setDisable(false);
        }
        for (int i = 0; i < 40; i++) {
            Circle selectedCircle = (Circle)checkGridPane.getChildren().get(i);
            selectedCircle.setFill(Color.GRAY);
        }
        for (int i = 0; i < 6; i++ ) {
            Circle selectedCircle = (Circle)colorSelectionVBox.getChildren().get(i);
            selectedCircle.setDisable(false);
            switch (i) {
                case 0:
                    selectedCircle.setFill(Color.BLUE);
                    break;
                case 1:
                    selectedCircle.setFill(Color.HOTPINK);
                    break;
                case 2:
                    selectedCircle.setFill(Color.ORANGE);
                    break;
                case 3:
                    selectedCircle.setFill(Color.YELLOW);
                    break;
                case 4:
                    selectedCircle.setFill(Color.GREEN);
                    break;
                case 5:
                    selectedCircle.setFill(Color.RED);
                    break;
            }
        }
    }

    private void paintCheckPegs() {
        int nWhitePegs = dCtrl.getWhitePegs(10 - selectedRow);
        int nBlackPegs = dCtrl.getBlackPegs(10 - selectedRow);
        int i = 0;
        while (nBlackPegs > 0) {
            Circle selectedCircle = (Circle)checkGridPane.getChildren().get((selectedRow-1)*4+i);
            selectedCircle.setFill(Color.BLACK);
            nBlackPegs--;
            i++;
        }
        while (nWhitePegs > 0) {
            Circle selectedCircle = (Circle)checkGridPane.getChildren().get((selectedRow-1)*4+i);
            selectedCircle.setFill(Color.WHITE);
            nWhitePegs--;
            i++;
        }
    }

    private void paintWinnerCombination() {
        ArrayList<Integer> winnerCombination = dCtrl.getWinnerCombinationArray();
        for (int i = 0; i < 4; i++) {
            Circle selectedCircle = (Circle)mainGridPane.getChildren().get(i);
            switch (winnerCombination.get(i)) {
                case 0:
                    selectedCircle.setFill(Color.BLUE);
                    break;
                case 1:
                    selectedCircle.setFill(Color.HOTPINK);
                    break;
                case 2:
                    selectedCircle.setFill(Color.ORANGE);
                    break;
                case 3:
                    selectedCircle.setFill(Color.YELLOW);
                    break;
                case 4:
                    selectedCircle.setFill(Color.GREEN);
                    break;
                case 5:
                    selectedCircle.setFill(Color.RED);
                    break;
            }
        }
    }

    private ArrayList<Integer> getCombination() {
        ArrayList<Integer> combination = new ArrayList<Integer>();
        for (int i=0; i < 4; i++) {
            Circle selectedCircle = (Circle)mainGridPane.getChildren().get(selectedRow*4+i);
            if (selectedCircle.getFill().equals(Color.BLUE)) combination.add(BLUE);
            else if (selectedCircle.getFill().equals(Color.HOTPINK)) combination.add(PINK);
            else if (selectedCircle.getFill().equals(Color.ORANGE)) combination.add(ORANGE);
            else if (selectedCircle.getFill().equals(Color.YELLOW)) combination.add(YELLOW);
            else if (selectedCircle.getFill().equals(Color.GREEN)) combination.add(GREEN);
            else if (selectedCircle.getFill().equals(Color.RED)) combination.add(RED);
        }
        return combination;
    }

    public void exitButtonAction(ActionEvent actionEvent) {
        vCtrl.loginView(dCtrl);
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
