public class Piece {
    private int index;
    private char icon;

    public Piece(int index, char icon) {
        this.index = index;
        this.icon = icon;
    }

    public void SetIcon(char icon) {
        this.icon = icon;
    }

    public int GetIndex() {
        return index;
    }

    public char GetIcon() {
        return icon;
    }
}
