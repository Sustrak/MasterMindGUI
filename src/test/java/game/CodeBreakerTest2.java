package game;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class CodeBreakerTest2 {
    @Test
    public void getElementsComb() throws Exception {
        CodeBreaker c = new CodeBreaker(DiffEnum.EASY);
        int res = c.getElementsComb();
        assertEquals(4, res);
    }

    @Test
    public void secondClueUsed() throws Exception {
        CodeBreaker c = new CodeBreaker(DiffEnum.EASY);
        c.useSecondClue();
        boolean res = c.secondClueUsed();
        assertEquals(true, res);
    }

    @Test
    public void firstClueUsed() throws Exception {
        CodeBreaker c = new CodeBreaker(DiffEnum.EASY);
        c.useFirstClue();
        boolean res = c.firstClueUsed();
        assertEquals(true, res);
    }

    @Test
    public void getPositionClue() throws Exception {
        //Depende de la combinacion ganadora generada aleatoriamente
    }

    @Test
    public void getColorClue() throws Exception {
        //Depende de la combinacion ganadora generada aleatoriamente
    }

    @Test
    public void getColorRemoved() throws Exception {
        //Depende de la combinacion ganadora generada aleatoriamente
    }

    @Test
    public void useFirstClue() throws Exception {
        //Depende de la combinacion ganadora generada aleatoriamente
    }

    @Test
    public void useSecondClue() throws Exception {
        //Depende de la combinacion ganadora generada aleatoriamente
    }

    @Test
    public void setTimeElapsed() throws Exception {
        //Devuelve el tiempo entre el inicio de la partida i la llamada de esta funcion
        //por lo tanto es dificil hacer un assert sobre esta funcion
    }

    @Test
    public void calculateScore() throws Exception {
        //Depende del transcurso de la partida i el tiempo empleado para completarla
    }

    @Test
    public void getScore() throws Exception {
        //Depende del test anterior
    }

    @Test
    public void getDificulty() throws Exception {
        CodeBreaker c = new CodeBreaker(DiffEnum.ORIGINAL);
        DiffEnum res = c.getDificulty();
        assertEquals(res, DiffEnum.ORIGINAL);
    }

    @Test
    public void boardIsFull() throws Exception {
        CodeBreaker c = new CodeBreaker(DiffEnum.ORIGINAL);
        for (int i = 0; i < 12; i++) {
            c.setNewCombination(new ArrayList<>(Arrays.asList(0, 1, 2, 3)));
        }
        //El nombre de la funcion es contradictorio, aunque el funcionamiento es el correcto
        boolean res = c.boardIsFull();
        assertEquals(true, res);
    }

    @Test
    public void getWhitePegs() throws Exception {
        //Depende de la combinacion ganadora generada aleatoriamente
    }

    @Test
    public void setNewCombination() throws Exception {
        CodeBreaker c = new CodeBreaker(DiffEnum.ORIGINAL);
        boolean res = c.setNewCombination(new ArrayList<>(Arrays.asList(0, 1, 2, 3)));
        assertEquals(false, res);
    }

    @Test
    public void getBlackPegs() throws Exception {
        //Depende de la combinacion ganadora generada aleatoriamente
    }

    @Test
    public void getNPieces() throws Exception {
        CodeBreaker c = new CodeBreaker(DiffEnum.HARD);
        int res = c.getNPieces();
        assertEquals(6, res);
    }

    @Test
    public void getColorsAvailable() throws Exception {
        CodeBreaker c = new CodeBreaker(DiffEnum.HARD);
        int res = c.getColorsAvailable().size();
        assertEquals(8, res);
        c.useFirstClue();
        res = c.getColorsAvailable().size();
        assertEquals(7, res);
    }

    @Test
    public void getNCombinations() throws Exception {
        CodeBreaker c = new CodeBreaker(DiffEnum.ORIGINAL);
        for (int i = 0; i < 8; i++) {
            c.setNewCombination(new ArrayList<>(Arrays.asList(0, 1, 2, 3)));
        }
        int res = c.getNCombinations();
        assertEquals(8, res);
    }

    @Test
    public void getWinnerCombination() throws Exception {
        //Depende de la combinacion ganadora generada aleatoriamente
    }

    @Test
    public void setLastCorrection() throws Exception {
        //Depende de la combinacion ganadora generada aleatoriamente
    }

}