public class Piece {
    private int x;
    private int y;
    private char icon;

    public Piece(int x, int y, char icon) {
        this.x = x;
        this.y = y;
        this.icon = icon;
    }

    public void SetIcon(char icon) {
        //TODO: functie om het icoon te veranderen
    }

    public int[] GetPosition() {
        int[] position = {x, y};
        return position;
    }

    public char GetIcon() {
        return icon;
    }
}
