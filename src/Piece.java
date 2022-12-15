public class Piece {
    private int index;
    private char icon;

    public Piece(int index, char icon) {
        this.index = index;
        this.icon = icon;
    }

    public void setIcon(char icon) {
        this.icon = icon;
    }

    public int getIndex() {
        return index;
    }

    public char getIcon() {
        return icon;
    }
}
