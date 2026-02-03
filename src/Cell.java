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
