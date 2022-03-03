
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Created By Sean Lai 
//Created for U8 APCSA SAS 2D Array Project
//Free for use for Diren Gomez
public class Minesweep {
    class gridNode {
        static final char flag = 'F';
        static final char hidden = '#';
        public char value;
        public int row;
        public int col;
        public int reveal;

        gridNode(char v, int r, int c) {
            value = v;
            row = r;
            col = c;
            reveal = 2;
        }

        public char getVal() {
            if (reveal == 0) {
                return value;
            }
            if (reveal == 1) {
                return flag;
            }
            return hidden;
        }

        @Override
        public int hashCode() {
            final int prime = 127;
            int result = 1;
            result = prime * result + col;
            result = prime * result + row;
            result = prime * result + value;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            gridNode other = (gridNode) obj;
            if (col != other.col)
                return false;
            if (row != other.row)
                return false;
            if (value != other.value)
                return false;
            return true;
        }

    };

    public int row;
    public int col;
    public Character[][] ansGrid; // For Convience in checkInput
    public gridNode[][] grid; // To delete
    private boolean gameInstance = false;
    private int maxMine;
    public gridNode[][] gridN;
    private double difficulty = 0.89578;

    Minesweep(int r, int c) {
        row = r;
        col = c;
        maxMine = (int) (r * c / 2);
        gridN = new gridNode[row][col];
        ansGrid = ansGridCreate();
    }

    Minesweep(int r, int c, int mM) {
        row = r;
        col = c;
        maxMine = mM;
        gridN = new gridNode[row][col];
        ansGrid = ansGridCreate();

    }

    Character[][] ansGridCreate() {
        Character[][] ansGrid = new Character[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (Math.random() > difficulty) {
                    ansGrid[i][j] = '*'; // Notation for mine
                } else {
                    ansGrid[i][j] = '0'; // Notation for empty space
                }
            }
        }
        ArrayList<int[]> mineLocations = new ArrayList<int[]>();

        for (int i = 0; i < row; i++) { // Find all mine locations
            for (int j = 0; j < col; j++) {
                if (ansGrid[i][j] == '*') {
                    mineLocations.add(new int[] { i, j });
                }
            }
        }
        if (mineLocations.size() > maxMine) { // Check if there are too many mines
            while (mineLocations.size() > maxMine) {
                int[] loc = mineLocations.remove((int) (Math.random() * mineLocations.size())); // Remove Randomly
                ansGrid[loc[0]][loc[1]] = '0';
            }
        }
        for (int i = 0; i < row; i++) { // Do an initial BFS to find all empty spaces and assign correct number if mine
                                        // is near
            for (int j = 0; j < col; j++) {
                /// check all directions including diagonals for mines denoted *
                if (ansGrid[i][j] != '*') {
                    int temp = (int) ansGrid[i][j];
                    if (i - 1 >= 0 && j >= 0 && ansGrid[i - 1][j] == '*') {
                        temp++;
                    }
                    if (i + 1 < row && j >= 0 && ansGrid[i + 1][j] == '*') {
                        temp++;
                    }
                    if (j - 1 >= 0 && ansGrid[i][j - 1] == '*') {
                        temp++;
                    }
                    if (j + 1 < col && ansGrid[i][j + 1] == '*') {
                        temp++;
                    }
                    if (i - 1 >= 0 && j - 1 >= 0 && ansGrid[i - 1][j - 1] == '*') {
                        temp++;
                    }
                    if (i + 1 < row && j + 1 < col && ansGrid[i + 1][j + 1] == '*') {
                        temp++;
                    }
                    if (i - 1 >= 0 && j + 1 < col && ansGrid[i - 1][j + 1] == '*') {
                        temp++;
                    }
                    if (i + 1 < row && j - 1 >= 0 && ansGrid[i + 1][j - 1] == '*') {
                        temp++;
                    }
                    if (temp == (int) ansGrid[i][j]) {
                        ansGrid[i][j] = '.';
                    } else {
                        ansGrid[i][j] = (Character) (char) temp;
                    }
                }
                gridN[i][j] = new gridNode(ansGrid[i][j], i, j);

            }
        }
        return ansGrid;
    }

    public static void startSession() { // Need To Debug
        Scanner session = new Scanner(System.in);
        System.out.println("Enter the number of rows: ");
        int row = session.nextInt();
        System.out.println("Enter the number of columns: ");
        int col = session.nextInt();
        System.out.println("Enter the number of mines: ");
        int mine = session.nextInt();
        Minesweep game = new Minesweep(row, col, mine);
        game.gameInstance = true;
        game.printGrid(game.gridN);

        while (game.gameInstance && !game.isWin()) {

            System.out.println("Enter the row: ");
            int r = session.nextInt();
            System.out.println("Enter the column: ");
            int c = session.nextInt();
            if (r < 0 || r >= game.row || c < 0 || c >= game.col) {
                System.out.println("Invalid Input");
                continue;
            }
            System.out.println("Flag or Reveal? (f/r): ");
            String flag = session.next();
            if (flag.equals("f")) {
                game.flag(r, c);
            } else if (flag.equals("r")) {
                game.checkInput(r, c);
            }
            if (game.gameInstance == true) {
                game.printGrid(game.gridN);
            }

        }
        session.close();

    }

    private boolean isWin() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (gridN[i][j].value == '*' && gridN[i][j].reveal == 2) {
                    return false;
                }
                if (gridN[i][j].reveal != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void flag(int r, int c) {
        if (gridN[r][c].reveal == 1) {
            gridN[r][c].reveal = 2;
        } else if (gridN[r][c].reveal == 2) {
            gridN[r][c].reveal = 1;
        }
    }

    void printGrid(Minesweep.gridNode[][] gridN) {
        for (int i = 0; i < gridN.length; i++) {
            for (int j = 0; j < gridN[i].length; j++) {
                System.out.print(gridN[i][j].getVal());
            }
            System.out.println();
        }
    }

    public void printG() {
        for (int i = 0; i < gridN.length; i++) {
            for (int j = 0; j < gridN[i].length; j++) {
                System.out.print(gridN[i][j].getVal());
            }
            System.out.println();
        }
    }

    private void test() {
        for (gridNode[] characters : gridN) {
            for (gridNode gridNode : characters) {
                System.out.print(gridNode);
            }
            System.out.println();
        }
    }

    private void loseGame() {
        gameInstance = false;
        printGridS(ansGrid);
        System.out.println("You Lose");
    }

    private void printGridS(Character[][] ansGrid2) {
        // Print grid slowly
        for (int i = 0; i < ansGrid2.length; i++) {
            for (int j = 0; j < ansGrid2[i].length; j++) {
                System.out.print(ansGrid2[i][j]);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println();
        }
    }

    public void checkInput(int r, int c) {
        List<gridNode> bfs = new ArrayList<>();
        List<gridNode> visited = new ArrayList<>();
        // Breath first search from input, dont queue if it is a number
        bfs.add(gridN[r][c]);
        if (gridN[r][c].value == '*') {
            gameInstance = false;
            loseGame();
        }
        while (!bfs.isEmpty()) {
            gridNode t = bfs.remove(0);
            // Check if gridNode is visited
            if (visited.indexOf(t) != -1) {
                continue;
            }
            t.reveal = 0;
            visited.add(t);
            if (t.value == '.') { // Problem
                if (t.row - 1 >= 0 && t.col >= 0 && gridN[t.row - 1][t.col].value != '*'
                        && visited.indexOf(gridN[t.row - 1][t.col]) == -1) {
                    bfs.add(gridN[t.row - 1][t.col]);
                }
                if (t.row + 1 < row && t.col >= 0 && gridN[t.row + 1][t.col].value != '*'
                        && visited.indexOf(gridN[t.row + 1][t.col]) == -1) {
                    bfs.add(gridN[t.row + 1][t.col]);

                }
                if (t.row >= 0 && t.col - 1 >= 0 && gridN[t.row][t.col - 1].value != '*'
                        && visited.indexOf(gridN[t.row][t.col - 1]) == -1) {
                    bfs.add(gridN[t.row][t.col - 1]);

                }
                if (t.row >= 0 && t.col + 1 < col && gridN[t.row][t.col + 1].value != '*'
                        && visited.indexOf(gridN[t.row][t.col + 1]) == -1) {
                    bfs.add(gridN[t.row][t.col + 1]);

                }
                if (t.row - 1 >= 0 && t.col - 1 >= 0 && gridN[t.row - 1][t.col - 1].value != '*'
                        && visited.indexOf(gridN[t.row - 1][t.col - 1]) == -1) {
                    bfs.add(gridN[t.row - 1][t.col - 1]);

                }
                if (t.row + 1 < r && t.col + 1 < col && gridN[t.row + 1][t.col + 1].value != '*'
                        && visited.indexOf(gridN[t.row + 1][t.col + 1]) == -1) {
                    bfs.add(gridN[t.row + 1][t.col + 1]);

                }
                if (t.row - 1 >= 0 && t.col + 1 < col && gridN[t.row - 1][t.col + 1].value != '*'
                        && visited.indexOf(gridN[t.row - 1][t.col + 1]) == -1) {
                    bfs.add(gridN[t.row - 1][t.col + 1]);

                }
                if (t.row + 1 < row && t.col - 1 >= 0 && gridN[t.row + 1][t.col - 1].value != '*'
                        && visited.indexOf(gridN[t.row + 1][t.col - 1]) == -1) {
                    bfs.add(gridN[t.row + 1][t.col - 1]);

                }
            }

        }
    }

    // Create a compare method between ansGrid and Grid
    // If the two grids are equal, return true
    // If the two grids are not equal, return false
    public boolean compare(char[][] ansGrid, char[][] grid) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (ansGrid[i][j] != grid[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}