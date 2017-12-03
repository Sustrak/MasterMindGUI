package users;

public class WrongDateException extends Exception {

    public WrongDateException(){}

    public WrongDateException(String msg){
        super(msg);
    }
}
