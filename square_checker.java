public class square_checker{
  // location of square being checked
  private int current_row;
  private int current_column;

  // value of square
  private char current_square_char;
  // is the number of flags it should have around it equal to the number of squares flagged around it
  private boolean is_satisfied;
  // the number of spaces adjacent to the square which you are on which have not been opened
  private int num_of_cleared_adjacent;

  // special squares
  private boolean is_corner;
  private boolean is_border;
  private char border_type;
    /*
    t = top
    r = right
    b = bottom
    l = left
    */
  // this is just following graphing quadrants
  private char corner_quadrant;
  

  // constants which only change if it is a border or corner
  private int squares_near_said_square = 8;

  // values near squares
  private char t_char;
  private char tl_char;
  private char l_char;
  private char bl_char;
  private char b_char;
  private char br_char;
  private char r_char;
  private char tr_char;

  // call upon this to make sure that we don't create an error
  public void border_logic(int r, int c, gameboard name){
    // r = row of the current square
    // c = column of the current squrare
    // name = name of the gameboard object
    
    if((r == 0) || (c == 0) || (r == name.get_row()) || (c == name.get_column()){
      // either border or corner

      // is corner
      if (((r == 0) && (c == 0)) || // top left corner
         ((r == 0) && (c == name.get_column())) || // top right corner
         ((r == name.get_row()) && (c == 0)) || // bottom left corner
         ((r == name.get_row()) && (c == name.get_column()))){ // bottom right corner
        is_corner = true;
        squares_near_said_square = 3;
        // top left
        if ((r == 0) && (c == 0)){
          corner_quadrant = '2';
        }
        // top right
        else if ((r == 0) && (c == name.get_column())){
          corner_quadrant = '1';
        }
        // bottom left
        else if((r == name.get_row()) && (c == 0)){
          corner_quadrant = '3';
        }
        // bottom right
        else if((r == name.get_row()) && (c == name.get_column())){
          corner_quadrant = '4';
        }
       }
      // is border
      else{
          is_border = true;
        if(r == 0){
          // this is top
          border_type = 't';
        }
        else if(r == name.get_row()){
          // this is the bottom
          border_type = 'b';
        }
        else if(c == 0){
          // this is left
          border_type = 'l';
        }
        else{
          // this is right
          border_type = 'r';
        }
        squares_near_said_square = 5;
      }
      
    }
  }
  //sets the value of the surrounding chars
  public void update_surrounding(int r, int c, gameboard name){
    // is corner or border
    if (is_corner || is_border){
      // is corner
      if(is_corner){
      }
      // is border
      else(is_border){
      }
    }
    // is a normal piece
    else{
      t_char = name.get_value(r, (c+1));
      tl_char = name.get_value((r-1), (c+1));
      l_char = name.get_value((r-1), c);
      bl_char = name.get_value((r-1), (c-1));
    }
  }

  //logic
  // if adjacent flags is 0 and the number of open spaces is equivalent to the value of the square, then the open spaces are flags
  // if there isn't enough information skip it and return later
  
  /* special rules:
    - if there are unkowns adjacent to both a 1 and a 2, the space which is not adjacent to the 1 but is adjacent to the two should be a flag given that the two only has three adjacent
    - 
  */
}