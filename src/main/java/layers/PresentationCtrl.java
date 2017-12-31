package layers;

import game.DiffEnum;
import users.UserNotFoundException;
import view.StartingView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class PresentationCtrl {

    private DomainCtrl dCtrl;
    private StartingView sView;

    public PresentationCtrl(){

        dCtrl = new DomainCtrl();
        sView = new StartingView();

    }

    public void startMasterMind() {

        int option = -1;
        while(option != 4){

            option = sView.getInitialOption();
            switch(option){
                case 1: newUser(); break;
                case 2:
                    logIn();
                    while(dCtrl.isLoggedIn()) getInitialGameOption();
                    break;
                case 3: resetPassword(); break;
                case 4: break;
                default: sView.showMessage("Error no Soportado"); break;
            }
        }

    }

    private void getInitialGameOption() {

        int tGame = sView.getTypeOfGame();
        switch(tGame){

            case 1: playCodeBreaker(); break;
            case 2: playCodeMaker(); break;
            case 3: loadGame(); break;
            case 4: showRankings(); break;
            case 5: showRecords(); break;
            case 6: showPlayer(); break;
            case 7: logOut(); break;
            default: sView.showMessage("Error no soportado en elección de juego"); break;
        }
    }

    // Initial Options

    private void newUser() {

        sView.showMessage("Alta Jugador");
        String name = sView.getNamePlayer();
        String surname = sView.getSurnamePlayer();
        String nickname = sView.getNickname();
        while(dCtrl.usedNickname(nickname)){
            sView.showMessage("El nickname introducido ya existe, elija otro.");
            nickname = sView.getNickname();
        }
        String birthDate = sView.getBirthDate();
        String password = sView.getPassword(0);

        int result = dCtrl.createUser(name, surname, nickname, birthDate, password);
        switch(result){
            case 0: sView.showMessage("Alta efectuada correctamente"); break;
            case 1: sView.showMessage("El usuario ya existe"); break;
            case 2: sView.showMessage("La fecha de nacimiento no es correcta"); break;
            default: sView.showMessage("Error no Soportado"); break;
        }
    }

    private void logIn() {

        sView.showMessage("LogIn");
        boolean loggedIn = false;
        while(!loggedIn){
            String nickname = sView.getNickname();
            String password = sView.getPassword(1);

            int result = dCtrl.logIn(nickname, password);
            switch(result){
                case 0: {
                    sView.showMessage("Usuario y contraseña correctos. Disfrute del juego.");
                    loggedIn = true;
                    break;
                }
                case 1: sView.showMessage("La contraseña introducida no es correcta"); break;
                case 2: sView.showMessage("El usuario introducido no existe"); break;
                default: sView.showMessage("Error no soportado en login"); break;
            }
        }
    }

    private void resetPassword() {
        sView.showMessage("Resetear Password");
        String nickname = sView.getNickname();
        String birthDate = sView.getBirthDate();
        String newPassword = sView.getPassword(1);

        int result = dCtrl.resetPassword(nickname, birthDate, newPassword);

        switch (result) {
            case 0: sView.showMessage("Password reseteado correctamente"); break;
            case 1:
                // TODO: Comprovar el format de birthDate al agafar-la de sView per no haver de tornar a introduïr tots els altres camp
                sView.showMessage("Introduzca la fecha siguiendo el siguiente formato dd/MM/yyyy");
                resetPassword();
                break;
            case 2: sView.showMessage("No se ha encontrado al usuario"); break;
            case 3: sView.showMessage("La fecha no coincide con la fecha de nacimiento introducida al crear el usuario, no ha sido posible resetear la password");
        }
    }

    // Game Options

    private void playCodeBreaker() {

        sView.showMessage("Has elegido jugar como CodeBreaker.");
        DiffEnum difficulty = sView.getCBdifficulty();
        dCtrl.startNewCodeBreaker(difficulty);

        playGame();
    }

    void playGame() {
        boolean keepPlaying = true;
        DiffEnum difficulty = dCtrl.getDifficulty();
        while(keepPlaying){

            boolean fClue = dCtrl.firstClueUsed();
            boolean sClue = dCtrl.secondClueUsed();

            int option = sView.getCBOption(fClue, sClue);
            switch(option){

                case 1:
                    // Introduce new combination
                    sView.showMessage("Introduce una nueva combinación:");
                    boolean repeatColor = true;
                    if(difficulty == DiffEnum.EASY)repeatColor = false;
                    ArrayList<Integer> newCombination = introduceNewCombination(repeatColor);
                    keepPlaying = !dCtrl.setNewCombination(newCombination);
                    showGame();
                    if(!keepPlaying) {
                        dCtrl.calculateScore();
                        dCtrl.updatePlayerOnFinishGame(true);
                        finishCBGame();
                    }
                    break;

                case 2:
                    // Usar pista 1: Eliminar un color
                    int colorRemoved = dCtrl.useFirstClue();
                    sView.showMessage("Se ha eliminado el color " + colorRemoved);
                    break;

                case 3:
                    // Usar pista 2: Fijar un color y posición
                    dCtrl.useSecondClue();
                    sView.showMessage("En la posición " + (dCtrl.getPositionClue() + 1) + " está el color " + dCtrl.getColorClue());
                    break;

                case 4:
                    // Guarda la partida y sale
                    dCtrl.saveGame();
                    keepPlaying = false;
                    break;

                case 5:
                    // Salir sin guardar la partida
                    dCtrl.updatePlayerOnFinishGame(false);
                    keepPlaying = false;
                    break;

                case 6:
                    //Temporal
                    ArrayList<Integer> wC = dCtrl.getWinnerCombinationArray();
                    sView.showMessage("" + wC);
                    break;

                default: break;

            }
            keepPlaying &= !dCtrl.boardIsFull();
        }
    }

    private void playCodeMaker() {


        sView.showMessage("Has elegido jugar como CodeMaker.");
        dCtrl.startNewCodeMaker(sView.getCMdifficulty());
        ArrayList<Integer> winnerComb = getWinnerCombination();
        dCtrl.setWinnerCombination(winnerComb);

        boolean keepPlaying = true;
        while(keepPlaying){


            int option = sView.getCMOption();
            switch(option){

                case 1:
                    //Make a correction of the combination
                    ArrayList<Integer> newComb = dCtrl.getNewCodeMakerComb();
                    dCtrl.setNewCombination(newComb);
                    sView.showMessage("La combinación introducida es " + newComb + "\n");
                    sView.showMessage("Correción de la combinación.\n");
                    sView.showMessage("La combinación ganadora es:" + dCtrl.getWinnerCombinationArray());
                    boolean goodCorrection = false;
                    int bP = 0, wP = 0;
                    while(!goodCorrection){
                        bP = sView.getNumBP();
                        wP = sView.getNumWP();
                        goodCorrection = dCtrl.isGoodCorrection(bP, wP);
                        if(!goodCorrection) sView.showMessage("La corrección no es correcta");
                    }
                    showGame();
                    if(bP == dCtrl.getNPieces()) {
                        keepPlaying = false;
                        finishCMGame();
                    }
                    break;

                case 2:
                    //Finish the game without ending
                    sView.showMessage("Estás a punto de salir de la partida. Deseas continuar? (s/n)");
                    String s = sView.getYesNo();
                    if(s.equals("s")){
                        sView.showMessage("Has abandonado la partida");
                        keepPlaying = false;
                    }

                    break;
            }
            keepPlaying &= !dCtrl.boardIsFull();
        }

    }

    private void showRankings() {

        sView.showMessage("Rankings");
        int option = sView.getDiffRanking();
        if(option < 4){

            DiffEnum diff = DiffEnum.values()[option-1];
            String rrEntrys[] = dCtrl.getRanking(diff);
            sView.showRREntrys(rrEntrys, diff, true);
        }
    }

    private void showRecords() {
        sView.showMessage("Records");
        String rrEntrys[] = dCtrl.getRecords();
        sView.showRREntrys(rrEntrys, null, false);
    }

    private void showPlayer() {
        sView.showMessage("Player Info");
        String nickname = sView.getNickname();
        Map<String, ArrayList<String>> info;
        try {
            info = dCtrl.getPlayerInfo(nickname);
            sView.showUser(nickname, info);
        } catch (UserNotFoundException ex) {
            sView.showMessage("User not found");
        }
    }

    private void loadGame() {

        sView.showMessage("Cargar partida guardada.");
        Vector ids = dCtrl.getIdSavedGames();
        for (Object id1 : ids) {
            int id = (int) id1;
            ArrayList<String> infoGame = null;
            try {
                infoGame = dCtrl.getInfoGame(id);
            } catch (FileNotFoundException e) {
                String s = "No se pudo cargar la partida con id: " + id + " comprueve que el archivo exista";
                sView.showMessage(s);
                continue;
            }
            sView.showInfoGame(infoGame);
        }
        if (ids.size() == 0) sView.showMessage("No tiene partidas guardadas");
        else {
            int id = sView.getGameId(ids);
            try {
                dCtrl.loadGame(id);
                showGame();
                playGame();
            } catch (FileNotFoundException e) {
                sView.showMessage("Error cargando la partida");
            }
        }
    }

    private void logOut() {
        dCtrl.logOut();
    }

    // Auxiliary methods

    private void showGame() {

        int nCombinations = dCtrl.getNCombinations();
        sView.showMessage("\nJuego con " + nCombinations + " combinaciones.\n");
        for(int i = nCombinations - 1; i >= 0; --i){

            ArrayList<Integer> comb = dCtrl.getCombination(i);
            int nWhitePegs = dCtrl.getWhitePegs(i);
            int nBlackPegs = dCtrl.getBlackPegs(i);
            sView.showCombination(comb, nWhitePegs, nBlackPegs);
        }
    }

    private void finishCBGame() {


        long time = dCtrl.setTimeElapsed();

        int seconds = (int) (TimeUnit.MILLISECONDS.toSeconds(time) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(time);

        sView.showMessage("Enhorabuena, has resuelto la combinación secreta");
        double score = dCtrl.calculateScore();
        sView.showMessage("Duración del juego: " + minutes + " minutos y " + seconds + " segundos.");
        sView.showMessage("La puntuación obtenida es de: " + (int) score + " puntos." + "\n");
        sView.showMessage("---------------------------------------------------------------------\n");
    }

    private void finishCMGame() {

        int nComb = dCtrl.getNCombinations();
        sView.showMessage("La partida ha acabado. Se han necesitado " + nComb + " combinaciones.\n");
        sView.showMessage("---------------------------------------------------------------------\n");
    }

    private ArrayList<Integer> introduceNewCombination(boolean repeatColor){

        ArrayList<Integer> colorsAvailable = dCtrl.getColorsAvailable();
        sView.showColorsAvailable(colorsAvailable);
        System.out.println();

        // If second clue has been used it appears a message showing the position and color of one piece
        if(dCtrl.secondClueUsed())
            sView.showMessage("Pista 2: En la posición " + (dCtrl.getPositionClue() + 1) + " está el color " + dCtrl.getColorClue() + System.lineSeparator());

        // If no color has been removed, colorRemoved = -1;
        int colorRemoved = dCtrl.getColorRemoved();
        int colorAux = 0;
        if(colorRemoved != -1)colorAux++;

        ArrayList<Boolean> colorsIntroduced = new ArrayList<>(colorsAvailable.size() + colorAux);
        for(int i = 0; i < colorsAvailable.size() + colorAux; ++i) colorsIntroduced.add(false);
        if(colorRemoved != -1) colorsIntroduced.set(colorRemoved, true);

        int nPieces = dCtrl.getNPieces();
        ArrayList<Integer> newCombination = new ArrayList<>(nPieces);
        for(int i = 0; i < nPieces; ++i){

            boolean colorIntroduced = false;

            while(!colorIntroduced){
                sView.showMessage("En la posición " + (i+1) + " introduces el color: ");
                int color = sView.getColor();

                if(color == colorRemoved || color < 0 || color > (colorsAvailable.size() - 1 + colorAux))
                    sView.showMessage("El color introducido no está disponible" + "\n");
                else if(colorsIntroduced.get(color))
                    sView.showMessage("El color introducido ya lo has utilizado"  + "\n");
                else {
                    if(!repeatColor) colorsIntroduced.set(color, true);
                    colorIntroduced = true;
                    newCombination.add(color);
                }
            }
        } return newCombination;
    }

    private ArrayList<Integer> getWinnerCombination() {

        boolean goodCombination = false;
        ArrayList<Integer> colorsAvailable = dCtrl.getColorsAvailable();
        ArrayList<Integer> winnerComb = null;
        while(!goodCombination){

            sView.showColorsAvailable(colorsAvailable);
            winnerComb = sView.getWinnerCombination(dCtrl.getNPieces());
            if(colorsAvailable.containsAll(winnerComb)) goodCombination = true;
            else sView.showMessage("La combinación introducida no es correcta");
        }
        return winnerComb;
    }

}
