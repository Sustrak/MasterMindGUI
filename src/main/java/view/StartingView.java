package view;

import game.DiffEnum;

import java.util.*;

public class StartingView {

    private Scanner s;

    public StartingView(){

        s = new Scanner(System.in);
        System.out.println("Bienvenido Jugador, Mastermind te espera.");
    }

    // Initial Options

    public int getInitialOption() {

        System.out.println(System.lineSeparator() + "Seleccione una opción:" + System.lineSeparator());
        System.out.println("Opción 1: Registrarse como nuevo jugador");
        System.out.println("Opción 2: Hacer login con tu usuario");
        System.out.println("Opción 3: Recuperar contraseña");
        System.out.println("Opción 4: Salir del juego");

        int result = 0;
        boolean selected = false;

        while(!selected){
            result = getInt();
            if(result < 1 || result > 4) System.out.println("La opción seleccionada no es una opción válida, elija de nuevo");
            else selected = true;
        }

        return result;
    }

    // Info Player

    public String getNamePlayer() {

        System.out.println("Introduzca su nombre");
        String name = s.nextLine();
        while(name.equals("")) name = s.nextLine();
        return name;
    }

    public String getSurnamePlayer() {

        System.out.println("Introduzca su apellido");
        String surname = s.nextLine();
        while(surname.equals("")) surname = s.nextLine();
        return surname;
    }

    public String getBirthDate() {

        System.out.println("Introduzca su fecha de nacimiento de la forma dd/mm/yyyy");
        String bD = s.nextLine();
        while(bD.equals("")) bD = s.nextLine();
        return bD;
    }

    public String getNickname() {
        System.out.println("Introduzca el nickname:");
        String nickname = s.nextLine();
        while(nickname.equals("")) nickname = s.nextLine();
        return nickname;
    }

    /**
     * If option == 1 a user only has to introduce his password one time.
     * Otherwise password should be introduced twice. Two passwords should match
     * @param option
     * @return
     */
    public String getPassword(int option) {
        System.out.println("Introduzca la contraseña:");
        String p1 = s.nextLine();
        while(p1.equals("")) p1 = s.nextLine();
        if(option == 1) return p1;

        System.out.println("Repita su contraseña:");
        String p2 = s.nextLine();
        while(p2.equals("")) p2 = s.nextLine();

        if(!p1.equals(p2)){
            System.out.println("Las contraseñas no coinciden. Repita el proceso");
            return getPassword(0);
        }
        return p1;
    }


    // Game Options

    public int getTypeOfGame() {

        System.out.println("Elija la opción deseada:" + System.lineSeparator());
        System.out.println("Opción 1. Jugar como CodeBreaker");
        System.out.println("Opción 2. Jugar como CodeMaker");
        System.out.println("Opción 3. Cargar partida");
        System.out.println("Opción 4: Consultar rankings");
        System.out.println("Opción 5: Consultar records");
        System.out.println("Opción 6: Consultar jugador");
        System.out.println("Opción 7. LogOut");

        int option = getInt();
        if(option < 1 || option > 7) {
            System.out.println("La opción seleccionada no es una opción válida, elija de nuevo");
            return getTypeOfGame();
        }
        return option;
    }

    public int getDiffRanking() {

        System.out.println(System.lineSeparator() + "Seleccione un Ranking:" + System.lineSeparator());
        System.out.println("Opción 1: Ranking dificultad Fácil");
        System.out.println("Opción 2: Ranking dificultad Original");
        System.out.println("Opción 3: Ranking dificultad Difícil");
        System.out.println("Opción 4: Salir");

        int result = 0;
        boolean selected = false;

        while (!selected){
            result = getInt();
            if (result < 1 || result > 4)
                System.out.println("La opción seleccionada no es una opció válida, elija de nuevo");
            else {
                selected = true;
            }
        }
        return result;
    }

    public int getCBOption(boolean firstClue, boolean secondClue) {

        System.out.println(System.lineSeparator() + "Seleccione una opción:" + System.lineSeparator());
        System.out.println("Opción 1: Introducir nueva combinación");
        if(!firstClue)System.out.println("Opción 2: Usar pista: Eliminar un posible color");
        if(!secondClue)System.out.println("Opción 3: Usar pista: Fijar una ficha de la combinación ganadora");
        System.out.println("Opción 4: Guardar partida y salir");
        System.out.println("Opción 5: Salir sin guardar");

        int result = 0;
        boolean selected = false;

        while(!selected){
            result = getInt();
            if(result < 1 || result > 6 || (firstClue && result == 2) || (secondClue && result == 3))
                System.out.println("La opción seleccionada no es una opción válida, elija de nuevo");
            else selected = true;
        }
        return result;
    }

    public DiffEnum getCBdifficulty() {

        System.out.println("Elija la dificultat del juego:" + System.lineSeparator());
        System.out.println("Opción 1: Fácil. 4 espacios. 6 colores. Sin repetidos");
        System.out.println("Opción 2: Original. 4 espacios. 6 colores. Con repetidos");
        System.out.println("Opción 3: Dificil. 6 espacios. 8 colores. Con repetidos");
        int option = getInt();
        if(option < 1 || option > 3) {
            System.out.println("La dificultat seleccionada no es válida, elija de nuevo");
            return getCBdifficulty();
        }
        DiffEnum diff = DiffEnum.values()[option-1];
        return diff;
    }
    
    public DiffEnum getCMdifficulty() {

        System.out.println("Elija la dificultat del juego:" + System.lineSeparator());
        System.out.println("Opción 1: Original. 4 espacios. 6 colores. Con repetidos");
        System.out.println("Opción 2: Dificil. 6 espacios. 8 colores. Con repetidos");
        int option = getInt();
        if(option < 1 || option > 2) {
            System.out.println("La dificultat seleccionada no es válida, elija de nuevo");
            return getCMdifficulty();
        }
        DiffEnum diff = DiffEnum.values()[option];
        return diff;
    }

    public int getCMOption() {

        System.out.println("Seleccione una opción:" + System.lineSeparator());
        System.out.println("Opción 1: Obtener y corregir nueva combinación");
        System.out.println("Opción 2: Salir sin acabar la partida");

        int result = 0;
        boolean selected = false;

        while(!selected){
            result = getInt();
            if(result < 1 || result > 2)
                System.out.println("La opción seleccionada no es una opción válida, elija de nuevo");
            else selected = true;
        }
        return result;
    }

    public void showRREntrys(String[] rrEntrys, DiffEnum diff, boolean forRanking) {
        if (forRanking) System.out.println(System.lineSeparator() + "Ranking dificultad " + diff.toString() + System.lineSeparator());
        else {
            System.out.println(System.lineSeparator() + "Records" + System.lineSeparator());
            System.out.print("                                  ");
        }
        System.out.println("Date" + "                            " + "NickName" + "    " + "Score");

        String nameRecords[] = {"Más Experiencia:                  ",
                "Más partidos ganados sin perder:  ",
                "Más partidas jugadas:             ",
                "Más partidas ganadas:             "};
        for (int i = 0; i < rrEntrys.length; i++) {
            if (!forRanking) System.out.print(nameRecords[i]);
            System.out.println(rrEntrys[i]);
        }
        System.out.println();
    }

    public void showColorsAvailable(ArrayList<Integer> colorsAvailable) {
        System.out.println("Los colores disponibles son:");
        for(int i = 0; i < colorsAvailable.size(); ++i)
            System.out.print(colorsAvailable.get(i) + " ");
    }

    public int getColor() {

        return getInt();
    }

    public void showCombination(ArrayList<Integer> colors, int nWP, int nBP) {

        System.out.print(colors + "   ");
        for(int i = 0; i < nBP; ++i) System.out.print("X");
        for(int i = 0; i < nWP; ++i) System.out.print("O");
        System.out.println();
        System.out.println();
    }



    public int getNumBP() {

        System.out.println("Introduce el número de fichas negras:");
        return getInt();
    }

    public int getNumWP() {

        System.out.println("Introduce el número de fichas blancas:");
        return getInt();
    }

    public ArrayList<Integer> getWinnerCombination(int nPieces) {

        System.out.println("\nIntroduce la combinación ganadora. " + nPieces + " colores." );
        System.out.println("Escribe cada color separado por un espacio:");
        ArrayList<Integer> winnerComb = new ArrayList<>(nPieces);
        for(int i = 0; i < nPieces; ++i)  winnerComb.add(getInt());

        return winnerComb;
    }



    public void showUser(String nickname, Map<String, ArrayList<String>> info) {
        System.out.println(System.lineSeparator() + nickname + System.lineSeparator());
        System.out.println("Name:          " + info.get("name").get(0));
        System.out.println("Category:      " + info.get("category").get(0));
        System.out.println("Experience:    " + info.get("experience").get(0));

        System.out.println("               EASY   ORIGINAL    HARD");

        System.out.println("Max Score:     " + info.get("maxScore").get(0) + "    " + info.get("maxScore").get(1) + "         " + info.get("maxScore").get(2));
        System.out.println("Played Games:  " + info.get("playedGames").get(0) + "    " + info.get("playedGames").get(1) + "         " + info.get("playedGames").get(2));
        System.out.println("Winned Games:  " + info.get("winnedGames").get(0) + "    " + info.get("winnedGames").get(1) + "         " + info.get("winnedGames").get(2));
        System.out.println("Winning Spree: " + info.get("winningSpree").get(0) + "    " + info.get("winningSpree").get(1) + "         " + info.get("winningSpree").get(2));
    }

    public String getYesNo() {
        String a = "";
        while(a.equals("")) a = s.nextLine();
        return a;
    }

    private int getInt() {

        int n = 0;
        try {
            n = s.nextInt();
        }
        catch(InputMismatchException ime){
            s.next();
        }
        return n;
    }

    public void showInfoGame(ArrayList<String> infoGame) {
        System.out.println("Showing info of game: " + infoGame.get(0));
        System.out.println(" -- Dificulty: " + infoGame.get(1));
        System.out.println(" -- Is first clue used ? " + infoGame.get(2));
        System.out.println(" -- Is second clue used ? " + infoGame.get(3));
    }

    public int getGameId(Vector ids) {
        System.out.println("Introduzca el id de la partida que quiere cargar");
        int a = s.nextInt();
        while (!ids.contains(a)) {
            System.out.println("El id introducino no esta en su lista de partids guardadas");
            a = s.nextInt();
        }
        return a;
    }

    public void showMessage(String message) {

        System.out.println(message);
    }
}
