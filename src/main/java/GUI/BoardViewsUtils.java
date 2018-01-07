package GUI;

public class BoardViewsUtils {
    public static String getColorId(int i) {
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


}
