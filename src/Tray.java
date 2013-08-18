import java.util.*;

/**
 * Tray class that carries all blocks and relevant information.
 * @author Dickson Lui, Jesse Luo
 */

public class Tray implements Comparable<Tray> {

	private ArrayList<Block> myBlocks;
	private boolean[][] mySpace;
	private int myHeight;
	private int myWidth;
	public int myScore;
	

	public Tray (int columns, int rows) {
		// Tray constructor that creates a Tray given the number of rows and columns.
		myHeight = rows;
		myWidth = columns;
		myBlocks = new ArrayList<Block>(rows * columns / 2);
		mySpace = new boolean[columns][rows];
	}
	
	
	private Tray (int columns,int rows, ArrayList<Block> copy) {
		myHeight = rows;
		myWidth = columns;
		myBlocks = copy;
		mySpace = addBlock(copy);
		this.sortBlocks();
	}
	
    public boolean[][] addBlock (ArrayList<Block> list) {
        boolean[][] newSpace = new boolean[this.myHeight][this.myWidth];
        for (int i = 0; i < list.size(); i++) {
                Block x = list.get(i);
                for (int j = x.getRow(); j < x.getRow() + x.getHeight(); j++) {
                        for (int k = x.getCol(); k < x.getCol() + x.getWidth(); k++) {
                                newSpace[k][j] = true;
                        }
                }
        }return newSpace;
}

	public LinkedList<Tray> generateMoves () {
		// When called on a tray, generates a Linked List structure with every possible tray
		// configuration that could result from moving a block.
		LinkedList<Tray> possibleTrays = new LinkedList<Tray>();

		for (int j = 0; j < this.getBlocks().size(); j++) {
			Block currBlock=this.getBlocks().get(j);
			Block copyblock=currBlock;
			ArrayList<Block> copy = getBlocks();
			boolean canUp = true;
			boolean canDown = true;
			boolean canLeft = true;
			boolean canRight = true;
			System.out.println("3");
			// If this block can move up, generate the move and add it to the LinkedList.
			if (currBlock.getRow() != 0) {
				for (int i = currBlock.getCol(); i < currBlock.getCol() + currBlock.getWidth(); i++) {
					if (this.getSpace()[i][currBlock.getRow() - 1]) {
						canUp = false;
					}
				}
				if (canUp) {
					// Generate move here.
					copy.remove(currBlock);
					copyblock.setRow(copyblock.getRow()-1);
					copy.add(copyblock);
					possibleTrays.add(new Tray(myHeight,myWidth,copy));
				}
			}
			copyblock=currBlock;
			copy = getBlocks();
			
			// If this block can move down, generate the move and add it to the LinkedList.
			if (currBlock.getRow() + currBlock.getHeight() < this.getHeight()) {
				for (int i = currBlock.getCol(); i < currBlock.getCol() + currBlock.getWidth(); i++) {
					if (this.getSpace()[i][currBlock.getRow() + currBlock.getHeight()]) {
						canDown = false;
					}
				}
				if (canDown) {
					// Generate move here.
					copy.remove(currBlock);
					copyblock.setRow(copyblock.getRow()+11);
					copy.add(copyblock);
					possibleTrays.add(new Tray(myHeight,myWidth,copy));
				}
			}
			copyblock=currBlock;
			copy = getBlocks();
			// If this block can move left, generate the move and add it to the LinkedList.
			if (currBlock.getCol() != 0) {
				for (int i = currBlock.getRow(); i < currBlock.getRow() + currBlock.getHeight(); i++) {
					if (this.getSpace()[currBlock.getCol() - 1][i]) {
						canLeft = false;
					}
				}
				if (canLeft) {
					// Generate move here.
					copy.remove(currBlock);
					copyblock.setCol(copyblock.getCol()-1);
					copy.add(copyblock);
					possibleTrays.add(new Tray(myHeight,myWidth,copy));
				}
			}
			copyblock=currBlock;
			copy = getBlocks();
			// If this block can move right, generate the move and add it to the LinkedList.
			if (currBlock.getCol() + currBlock.getWidth() < this.getWidth()) {
				for (int i = currBlock.getRow(); i < currBlock.getRow() + currBlock.getHeight(); i++) {
					if (this.getSpace()[currBlock.getCol() + currBlock.getWidth()][i]) {
						canRight = false;
					}
						}
				if (canRight) {
					// Generate move here.
					copy.remove(currBlock);
					copyblock.setCol(copyblock.getCol()+1);
					copy.add(copyblock);
					possibleTrays.add(new Tray(myHeight,myWidth,copy));
				}
			}			
		}
		return possibleTrays;
	}

	public ArrayList<Block> getBlocks () {
		// Returns the ArrayList containing all the blocks on the tray.
		return this.myBlocks;
	}

	public boolean[][] getSpace () {
		// Returns the two-dimensional boolean representation of the board.
		// All spaces filled in are marked as true.
		return this.mySpace;
	}

	public int getHeight () {
		// Returns the height of the tray.
		return this.myHeight;
	}

	public int getWidth () {
		// Returns the width of the tray.
		return this.myWidth;
	}

	public void addBlock (String block) {
		// Block adding class used in TrayReader to populate the board.
		Block toAdd = new Block(block);
		for (int a = toAdd.getCol(); a < toAdd.getCol() + toAdd.getWidth(); a++) {
			for (int b = toAdd.getRow(); b < toAdd.getRow() + toAdd.getHeight(); b++) {
				this.mySpace[a][b] = true;
			}
		}
		myBlocks.add(toAdd);
	}

	public void sortBlocks () {
		// When called on a Tray class, InsertionSorts the block into increasing order 
		// by row, then column.
		ArrayList<Block> toBeSorted = this.getBlocks();
		ArrayList<Block> tempBlocks = new ArrayList<Block>(toBeSorted.size());

		while (toBeSorted.size() > 0) {
			Block currBlock = toBeSorted.get(0);
			for (Block compareBlock: toBeSorted) {
				if (compareBlock.getRow() < currBlock.getRow()) {
					currBlock = compareBlock;
				} else if (compareBlock.getRow() == currBlock.getRow()) {
					if (compareBlock.getCol() < currBlock.getCol()) {
						currBlock = compareBlock;
					}
				}
			}
			tempBlocks.add(currBlock);
			toBeSorted.remove(currBlock);
		}
		this.myBlocks = tempBlocks;
	}

	public int score (ArrayList<Block> current, ArrayList<Block> goal) {
		// Scores the Tray based on how close the current board configuration is to the goal board
		// configuration. This will help us implement our comparable interface to determine the best
		// Tray route to take.
		int [] scores = new int[goal.size()];
		ArrayList<Integer> blockUsed = new ArrayList<Integer>();

		for (int i = 0; i < goal.size(); i++) {
			scores[i] = 0;
			int goalwidth = goal.get(i).getWidth();
			int goalheight = goal.get(i).getHeight();
			for (int j = 0; j < current.size(); j++) {
				if (current.get(j).getWidth() == goalwidth && current.get(j).getHeight() == goalheight) {
					int tempScore = (int) Math.abs((current.get(j).getCol() - goal.get(i).getCol()));
					tempScore = tempScore + (int) Math.abs((current.get(j).getRow() - goal.get(i).getRow()));
					if (tempScore < scores[i] && !blockUsed.contains(j)) {
						scores[i] = tempScore;
						blockUsed.add(j);
					}
				}
				scores[i] = scores[i] * goalwidth * goalheight;
			}
		}
		int totalScore = 0;
		for (int l = 0; l < scores.length; l ++) {
			totalScore = totalScore + scores[l];
		}
		myScore=totalScore;
		return totalScore;
	}

	public int compareTo (Tray compareTray) {
		// Implementing the comparable interface.
		return Math.round(compareTray.myScore - myScore);
	}

    public String toString () {
    	// Hash code toString method that prints out the information for each block.
    	// (Top left row, top left column, height of block, width of block)
        String string = "";
        for (int i = 0; i < myBlocks.size(); i++) {
        	Block currBlock = myBlocks.get(i);
        	string = string + currBlock.getCol() + " " + currBlock.getRow() + " " 
        				    + currBlock.getHeight() + " " + currBlock.getWidth() + "\n";
        }     
        return string;
    }
    
    public boolean equals(Object obj){
        // Override object equals method by checking if Strings are the same
        // Every Tray + block configuration should be different if valid Tray
        return this.myBlocks.toString() == ((Tray)obj).myBlocks.toString();
    }
    
    public int hashCode(){
    	// Override object hash code with hash code of toString.
    	return this.toString().hashCode();
    }

}