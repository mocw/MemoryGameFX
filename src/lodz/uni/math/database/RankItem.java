package lodz.uni.math.database;

public class RankItem {

    private int pos;
    private String nick;
    private float time;

    public RankItem(int pos, String nick, float time) {
        this.pos = pos;
        this.nick = nick;
        this.time = time;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
