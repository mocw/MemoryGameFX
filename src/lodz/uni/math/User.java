package lodz.uni.math;

public class User {

    private static String nickname;
    private static User user = null;
    private static int id;

    private User() {}

    public static User getUser(){
        if(user == null){
            user = new User();
        }
        return user;
    }

    public static String getNickname() {
        return nickname;
    }

    public static int getId() {
        return id;
    }

    public static void setNickname(String nickname) {
        User.nickname = nickname;
    }

    public static void setId(int id) {
        User.id = id;
    }
}
