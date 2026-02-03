import java.util.Random;

/**
 * Manages the game board for Conway's Game of Life.
 * Contains a 2D array of cells and provides methods for grid operations.
 */
public class Grid {
    private Cell[][] cells;
    private int size;

    /**
     * Creates a new grid with the specified size.
     * All cells are initialized as dead.
     * 
     * @param size the width and height of the square grid
     */
    public Grid(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        
        // Initialize all cells as dead
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(false);
            }
        }
    }

    /**
     * Gets the size of the grid.
     * 
     * @return the grid size (width and height)
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the cell at the specified position.
     * 
     * @param row the row index
     * @param col the column index
     * @return the cell at the given position
     */
    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    /**
     * Sets the state of the cell at the specified position.
     * 
     * @param row the row index
     * @param col the column index
     * @param alive true to make the cell alive, false to make it dead
     */
    public void setCell(int row, int col, boolean alive) {
        cells[row][col].setAlive(alive);
    }

    /**
     * Counts the number of live neighbors for a specific cell.
     * Checks all 8 adjacent cells (horizontal, vertical, and diagonal).
     * 
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return the number of live neighbors (0-8)
     */
    public int countLiveNeighbors(int row, int col) {
        int count = 0;
        
        // Check all 8 neighbors
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Skip the cell itself
                
                int newRow = row + i;
                int newCol = col + j;
                
                // Check boundaries
                if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                    if (cells[newRow][newCol].isAlive()) {
                        count++;
                    }
                }
            }
        }
        
        return count;
    }

    /**
     * Generates a random initial state for all cells in the grid.
     * Each cell has a 50% chance of being alive.
     */
    public void randomize() {
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j].setAlive(rand.nextBoolean());
            }
        }
    }

    /**
     * Displays the current state of the grid in the console.
     * Uses ■ for live cells and □ for dead cells.
     */
    public void display() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(cells[i][j].getDisplay() + " ");
            }
            System.out.println();
        }
    }
}
