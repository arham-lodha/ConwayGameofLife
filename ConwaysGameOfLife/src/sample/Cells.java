package sample;

public class Cells {

    private Cell[][] cells;
    private int height;
    private int width;

    public Cells(String[][] cell){
        this.height = cell.length;
        this.width = cell[0].length;

        this.cells = new Cell[cell.length][cell[0].length];

        int id = 0;

        for (int r = 0; r < this.height; r++) {
            for (int c = 0; c < this.width; c++) {
                if(cell[r][c].equals("O")){
                    this.cells[r][c] = new Cell(true, id);
                }
                else if(cell[r][c].equals("-")){
                    this.cells[r][c] = new Cell(false, id);
                }
                id++;
            }
        }
    }

    public Cells(boolean[][] cell){
        this.height = cell.length;
        this.width = cell[0].length;

        this.cells = new Cell[height][width];

        int id = 0;

        for (int r = 0; r < this.height; r++) {
            for (int c = 0; c < this.width; c++) {
                if(cell[r][c]){
                    this.cells[r][c] = new Cell(true, id);
                }
                else {
                    this.cells[r][c] = new Cell(false, id);
                }
                id++;
            }
        }
    }

    public int countNeighborsAlive(int r, int c){

        int neighbors = 0;

        int rcid = cells[r][c].getId();

        for(int i = r - 1; i < r + 2; i++){

            for (int j = c - 1; j < c + 2; j++) {

                    if (!((i < 0) || (i > cells.length - 1) || (j < 0) || (j > cells[r].length - 1))) {
                        if(cells[i][j].isAlive() && rcid != cells[i][j].getId()){
                            neighbors++;
                        }
                    }

            }

        }

        return neighbors;
    }

//    Any live cell with fewer than two live neighbours dies (referred to as underpopulation or exposure[1]).
//    Any live cell with more than three live neighbours dies (referred to as overpopulation or overcrowding).
//    Any live cell with two or three live neighbours lives, unchanged, to the next generation.
//    Any dead cell with exactly three live neighbours will come to life.

    public void setCells(){

        boolean[][] temp = new boolean[cells.length][cells[0].length];
        for (int r = 0; r < cells.length; r++) {

            for (int c = 0; c < cells[r].length; c++) {
                temp[r][c] = cells[r][c].isAlive();

                if(cells[r][c].isAlive() && (countNeighborsAlive(r, c) == 2 || countNeighborsAlive(r,c) == 3)){
                    temp[r][c] = true;
                }
                else if(cells[r][c].isAlive() && (countNeighborsAlive(r, c) < 2 || countNeighborsAlive(r,c) > 3)){
                    temp[r][c] = false;
                }
                else if(!cells[r][c].isAlive() && countNeighborsAlive(r,c) == 3){
                    temp[r][c]=true;
                }
            }
        }

        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < cells[r].length; c++) {
                cells[r][c].setAlive(temp[r][c]);
            }
        }

    }


    @Override
    public String toString() {
        String string = "";

        for (int r = 0; r < cells.length; r++) {

            for (int c = 0; c < cells[0].length; c++) {

                if(cells[r][c].isAlive()){
                    string += "O ";
                }
                else {
                    string += "- ";
                }
            }

            string += "\n";
        }

        return string;
    }

    public Cell[][] getCells() {
        return cells;
    }

}
