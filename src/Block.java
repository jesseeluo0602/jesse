

public class Block {
    
    private int myRow;
    private int myCol;
    private int myHeight;
    private int myWidth;

   
    public Block (String s) {
            // Block constructor. Takes in a Block object given the text file line input.
           
            String[] corners = s.split(" ");
            // Checks if the line input has four arguments passed in for the top left corner
            // and bottom right corner. If not, throw an error.
            if (corners.length != 4) {
                    throw new IllegalArgumentException("Invalid number of arguments: " +
                                    "Board construction requires FOUR integers to be passed in");
            }
 
            myRow = Integer.parseInt(corners[0]);
            myCol = Integer.parseInt(corners[1]);
            myHeight = Integer.parseInt(corners[2]) - myRow;
            myWidth = Integer.parseInt(corners[3]) - myCol;

    }
    
    public Block copy(Block b){
    	Block c=new Block("0 0 0 0");
    	c.setRow(b.getRow());
    	c.setCol(c.getCol());
    	c.setHeight(c.getHeight());
    	c.setWidth(c.getWidth());
    	return c;
    }
   
    public void setRow(int newRow){
    	//Sets top left row of the block.
    	this.myRow=newRow;
    }
    
    public void setCol(int newCol){
    	//Sets topleft row of the block.
    	this.myCol=newCol;
    }
    public int getRow () {
            // Returns the top left row of the block.
            return this.myRow;
    }
   
    public int getCol () {
            // Returns the top left column of the block.
            return this.myCol;
    }

    public int getHeight () {
            // Returns the height (top to bottom) of the block.
            return this.myHeight;
    }
   
    public int getWidth () {
            // Returns the width (left to right) of the block.
            return this.myWidth;
    }
    
    public void setWidth(int newWidth){
    	this.myWidth=newWidth;
    }
    
    public void setHeight(int newHeight){
    		this.myHeight=newHeight;
    }

    
	public String toString() {
		return "" + myCol + " " + myRow +" " + myHeight+ " " + myWidth;
	}
	
    public boolean equals (Object other) {
		return myRow == ((Block) other).myRow && myCol == ((Block) other).myCol && myHeight == ((Block) other).myHeight && myWidth == ((Block) other).myWidth;
	}
}