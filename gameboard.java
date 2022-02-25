public class gameboard{

  // setup for the paramaters for how the board is sized
  private int row = 0;
  private int column = 0;

  // board setup
  private char[][] current_board;

  // flags
  private int total_flags;
  private int num_flagged;

  // object loaded only to google
  public gameboard(char difficulty){
    if(difficulty == 'e'){
      //easy
      current_board = new char[10][8];
      row = 10;
      column = 8;
      total_flags = 10;
      for(int r=0; r < current_board.length; r++){
      // iterate through column
        for(int c=0; c < current_board[r].length; c++){
          current_board[r][c] = 'n';
        }
      }
    }
    else if(difficulty == 'm'){
      // medium
      current_board = new char[18][14];
      total_flags = 40;
      row = 18;
      column = 14;
      for(int r=0; r < current_board.length; r++){
      // iterate through column
        for(int c=0; c < current_board[r].length; c++){
          current_board[r][c] = 'n';
        }
      }
    }
    else if(difficulty == 'h'){
      //hard
      current_board = new char[24][20];
      total_flags = 99;
      row = 24;
      column = 20;
      for(int r=0; r < current_board.length; r++){
      // iterate through column
        for(int c=0; c < current_board[r].length; c++){
          current_board[r][c] = 'n';
        }
      }
    }
  }
  // object overloaded to any format
  public gameboard(int r, int c, int f){
    current_board = new char[r][c];
    total_flags = f;
    for(r=0; r < current_board.length; r++){
      // iterate through column
        for(c=0; c < current_board[r].length; c++){
          current_board[r][c] = 'n';
        }
      }
  }

  // getters
  public int get_num_flags_left(){
    return (total_flags - num_flagged);
  }
  public int get_total_flags(){
    return(total_flags);
  }
  public int get_num_flagged(){
    return(num_flagged);
  }

  // array values either by column or all at once
  public void print_array_values(){
    for(int r = 0; r < current_board.length; r++){
      for(int c = 0; c < current_board[r].length; c++){
        System.out.print(current_board[r][c]);
      }
      System.out.println();
    }
  }

  public int get_value(int r, int c){
    return current_board[r][c];
  }
  public void set_value(int r, int c, char val){
    if (((val == '0') || // 0 mines
        (val == '1') || // 1 mine
        (val == '2') || // 2 mines
        (val == '3') || // 3 mines
        (val == '4') || // 4 mines
        (val == '5') || // 5 mines
        (val == '6') || // 6 mines
        (val == '7') || // 7 mines
        (val == '8') || // 8 mines
        (val == 'f')) && // is mine
        ((r < row) && (c < column))){ 
      current_board[r][c] = val;
      }
    else{}
  }

  // data check
  public boolean is_valid(int r, int c){
    if((r < row) && (c < column)){return true;}
    else{return false;}
  }

  // get max length
  public int get_row(){
    return row;
  }
  public int get_column(){
    return column;
  }
  
}