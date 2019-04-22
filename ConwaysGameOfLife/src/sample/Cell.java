package sample;

public class Cell {

    private boolean isAlive;
    private int id;

    public Cell(boolean isAlive , int id){
        this.isAlive = isAlive;
        this.id = id;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getId() {
        return id;
    }
}
