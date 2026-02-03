import java.util.Scanner;

/**
 * Entry point for Conway's Game of Life simulation.
 * Handles user input and starts the game.
 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
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
        
        System.out.println("Conway's Game of Life:");
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
