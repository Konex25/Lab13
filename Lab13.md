# Lab 13
**Author:** 292680  
**Time spent on the tasks:** 2.5h  
**Github:** https://github.com/Konex25/Lab13
---

## Conway's Game of Life 

---

### Cell.java
```java
/**
 * Represents a single cell in the Game of Life grid.
 * Each cell can be either alive or dead.
 */
public class Cell {
    private boolean alive;

    /**
     * Creates a new cell with the specified state.
     * 
     * @param alive true if the cell should be alive, false otherwise
     */
    public Cell(boolean alive) {
        this.alive = alive;
    }

    /**
     * Checks if this cell is currently alive.
     * 
     * @return true if the cell is alive, false otherwise
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Sets the state of this cell.
     * 
     * @param alive true to make the cell alive, false to make it dead
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Returns the display character for this cell.
     * 
     * @return '■' if alive, '□' if dead
     */
    public char getDisplay() {
        return alive ? '■' : '□';
    }
}
```

---

### Grid.java
```java
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
```

---

### GameOfLife.java
```java
/**
 * Main game logic for Conway's Game of Life.
 * Manages the simulation and applies the rules to evolve the grid.
 */
public class GameOfLife {
    private Grid currentGrid;
    private int size;

    /**
     * Creates a new Game of Life instance with the specified grid size.
     * 
     * @param size the size of the square grid
     */
    public GameOfLife(int size) {
        this.size = size;
        this.currentGrid = new Grid(size);
    }

    /**
     * Gets the current grid.
     * 
     * @return the current state of the grid
     */
    public Grid getGrid() {
        return currentGrid;
    }

    /**
     * Computes the next generation by applying Conway's Game of Life rules:
     * - Any live cell with fewer than two live neighbors dies (underpopulation)
     * - Any live cell with two or three live neighbors lives on
     * - Any live cell with more than three live neighbors dies (overpopulation)
     * - Any dead cell with exactly three live neighbors becomes alive (reproduction)
     */
    public void nextGeneration() {
        Grid nextGrid = new Grid(size);
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int neighbors = currentGrid.countLiveNeighbors(i, j);
                boolean currentlyAlive = currentGrid.getCell(i, j).isAlive();
                
                // Apply Game of Life rules
                if (currentlyAlive) {
                    // Cell is alive
                    if (neighbors < 2) {
                        nextGrid.setCell(i, j, false); // Dies by underpopulation
                    } else if (neighbors == 2 || neighbors == 3) {
                        nextGrid.setCell(i, j, true); // Lives on
                    } else {
                        nextGrid.setCell(i, j, false); // Dies by overpopulation
                    }
                } else {
                    // Cell is dead
                    if (neighbors == 3) {
                        nextGrid.setCell(i, j, true); // Becomes alive by reproduction
                    }
                }
            }
        }
        
        currentGrid = nextGrid;
    }

    /**
     * Runs the simulation continuously, displaying each generation every 2 seconds.
     * The simulation runs indefinitely until interrupted.
     */
    public void run() {
        int generation = 0;
        
        while (true) {
            // Clear console (works on most systems)
            System.out.print("\033[H\033[2J");
            System.out.flush();
            
            System.out.println("Generation: " + generation);
            System.out.println();
            currentGrid.display();
            
            try {
                Thread.sleep(2000); // Wait 2 seconds
            } catch (InterruptedException e) {
                break;
            }
            
            nextGeneration();
            generation++;
        }
    }
}
```

---

### Main.java
```java
import java.util.Scanner;

/**
 * Entry point for Conway's Game of Life simulation.
 * Handles user input and starts the game.
 */
public class Main {
    /**
     * Main method that initializes and runs the Game of Life.
     * Prompts the user for initial configuration (random or manual input).
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameOfLife game = new GameOfLife(30);
        
        System.out.println("Conway's Game of Life");
        System.out.println("Enter 'Random' for random initialization");
        System.out.println("Or enter the initial state manually (30 lines of 30 characters each)");
        System.out.println("Use 0 for dead cells and 1 for live cells");
        System.out.print("\nYour choice: ");
        
        String input = scanner.nextLine().trim();
        
        if (input.equalsIgnoreCase("Random")) {
            // Generate random starting grid
            game.getGrid().randomize();
            System.out.println("\nRandom grid generated!");
        } else {
            // Manual input
            System.out.println("\nEnter 30 lines of 30 characters (0 or 1):");
            
            // First line was already read
            processLine(game.getGrid(), 0, input);
            
            // Read remaining 29 lines
            for (int i = 1; i < 30; i++) {
                String line = scanner.nextLine();
                processLine(game.getGrid(), i, line);
            }
            
            System.out.println("\nGrid initialized!");
        }
        
        System.out.println("Starting simulation in 2 seconds...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Start the simulation
        game.run();
        
        scanner.close();
    }
    
    /**
     * Helper method to process a single line of input.
     * Converts string of 0s and 1s to cell states in the grid.
     * 
     * @param grid the grid to populate
     * @param row the row index to fill
     * @param line the input string containing 0s and 1s
     */
    private static void processLine(Grid grid, int row, String line) {
        // Remove spaces if any
        line = line.replace(" ", "");
        
        for (int col = 0; col < Math.min(line.length(), 30); col++) {
            char c = line.charAt(col);
            if (c == '1') {
                grid.setCell(row, col, true);
            }
        }
    }
}
```
