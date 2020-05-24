package lodz.uni.math;

public class User {

    private static String nickname;
    private static User user = null;

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

    public static void setNickname(String nickname) {
        User.nickname = nickname;
    }
}
