package users;

import java.util.*;

public class UserSet {

    private Map<String, User> users;

    /**
     * UserSet creator
     */
    public UserSet() {
        this.users = new HashMap<>();
    }

    /**
     * Creates a new user and returns it
     * @param name of the new user
     * @param surname of the new user
     * @param nickname of the new user
     * @param birthDate of the new user
     * @param password of the new user
     * @return 1 if the user already exists otherwise returns 0
     */
    public int newUser(String name, String surname, String nickname, Date birthDate, String password) {

        User u = new User(name, surname, nickname, birthDate, password);
        if(users.containsKey(nickname)) return 1;
        users.put(nickname, u);
        return 0;
    }

    /**
     * Log in a user and returns it
     * @param nickname of the user
     * @param password of the user
     * @return an user if it exists and nickname and password are correct
     * @throws WrongPasswordException if password is wrong
     * @throws UserNotFoundException if the user is not match user's password
     */
    public User logIn(String nickname, String password) throws WrongPasswordException, UserNotFoundException {
        User u = users.get(nickname);
        if (u == null) throw new UserNotFoundException();
        if (!u.getPassword().equals(password)) throw new WrongPasswordException();
        return u;
    }

    /**
     * Change the password of an user
     * @param nickname of the user
     * @param birthDate of the user
     * @param newPassword to replace the old password
     * @throws UserNotFoundException if the user is not in the set
     * @throws WrongDateException if birthDate not match user's birthDate
     */
    public void resetPassword(String nickname, Date birthDate, String newPassword) throws UserNotFoundException, WrongDateException {
        User u = users.get(nickname);
        if (u == null) throw new UserNotFoundException();
        if (!u.getBirthDate().equals(birthDate)) throw new WrongDateException();
        u.setPassword(newPassword);
    }

    public int getNUsers(){
        return users.size();
    }

    /**
     * @param nickname of the player to retrieve the info
     * @return Map<String, ArrayList<String>> where if there's more than one value the info is organized like this:
     *         Vector[0] -> Easy
     *         Vector[1] -> Original
     *         Vector[2] -> Hard
     *         otherwise Vector[0] contains the info
     * @throws UserNotFoundException
     */
    public Map<String, ArrayList<String>> getUserInfo(String nickname) throws UserNotFoundException {
        User u = users.get(nickname);
        if (u == null) throw new UserNotFoundException();
        Map<String, ArrayList<String>> info = new HashMap<>();
        ArrayList<String> a = new ArrayList<>(1);
        a.add(0, u.getName());
        info.put("name", a);
        ArrayList<String> b = new ArrayList<>(1);
        b.add(0, u.getCategory().toString());
        info.put("category", b);
        ArrayList<String> c = new ArrayList<>(1);
        c.add(0, String.valueOf(u.getExperience()));
        info.put("experience", c);
        ArrayList<String> d = new ArrayList<>(1);
        d.add(0, String.valueOf(u.getMaxScore().getEasy()));
        d.add(1, String.valueOf(u.getMaxScore().getOriginal()));
        d.add(2, String.valueOf(u.getMaxScore().getHard()));
        info.put("maxScore", d);
        ArrayList<String> e = new ArrayList<>(1);
        e.add(0, String.valueOf(u.getPlayedGames().getEasy()));
        e.add(1, String.valueOf(u.getPlayedGames().getOriginal()));
        e.add(2, String.valueOf(u.getPlayedGames().getHard()));
        info.put("playedGames", e);
        ArrayList<String> f = new ArrayList<>(1);
        f.add(0, String.valueOf(u.getWinnedGames().getEasy()));
        f.add(1, String.valueOf(u.getWinnedGames().getOriginal()));
        f.add(2, String.valueOf(u.getWinnedGames().getHard()));
        info.put("winnedGames", f);
        ArrayList<String> g = new ArrayList<>(1);
        g.add(0, String.valueOf(u.getWinningSpree().getEasy()));
        g.add(1, String.valueOf(u.getWinningSpree().getOriginal()));
        g.add(2, String.valueOf(u.getWinningSpree().getHard()));
        info.put("winningSpree", g);

        return info;
    }


    public boolean existsUser(String nickname) {
        return users.containsKey(nickname);
    }

    //For Serialization

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }
}
