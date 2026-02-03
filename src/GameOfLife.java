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
