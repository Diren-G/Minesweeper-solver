public class gameboard{

  // setup for the paramaters for how the board is sized
  private int row = 0;
  private int column = 0;

  // board setup
  private char[][] current_board;

  public void update_board_size_set(String word){
    // this is mainly for testing with the google version to make it easy
    char l = word.charAt(0);
    if (l == 'e'){
      // easy
      current_board = new char[10][8];
    } 
    else if(l == 'm'){
      // medium
      current_board = new char[18][14];
    }
    else if(l == 'h'){
      // hard
      current_board = new char[24][20];
    }
    else{
      // null
    }
  }
  
  public void update_board_size_variable(int r, int c){
    // passes in variables so that the private data is reflected

    /*
    logic needed here
    */
    row = r;
    column = c;
  }

  public void create_board(){
    // creates an array of null values based off of rows and columns
    current_board = new char[row][column];

    // make all values 'n' to reflect the beginning of the game, where everything is unkown
    // iterate through row
    for(r=0; r < current_board.length; r++){
      // iterate through column
      for(c=0; c < current_board[r].length; c++){
        current_board[r][c] = 'n';
      }
    }
    
  }
}