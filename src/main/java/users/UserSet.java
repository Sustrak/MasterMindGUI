package users;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    public Map<String,String> getUserInfo(String nickname) throws UserNotFoundException {
        User u = users.get(nickname);
        if (u == null) throw new UserNotFoundException();
        Map<String, String> info = new HashMap<>();
        info.put("name", u.getName());
        info.put("category", u.getCategory().toString());
        info.put("experience", String.valueOf(u.getExperience()));
        info.put("maxScore", u.getMaxScore().toString());
        info.put("playedGames", u.getPlayedGames().toString());
        info.put("winnedGames", u.getWinnedGames().toString());
        info.put("winningSpree", u.getWinningSpree().toString());

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
