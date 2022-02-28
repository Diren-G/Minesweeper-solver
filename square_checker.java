public class square_checker{
  // location of square being checked
  private int current_row;
  private int current_column;

  // value of square
  private char current_square_char;
  /*
  0-8 = num mines around them
  n = unsearched
  f = flagged
  */
  private int current_square_as_int;
  // is the number of flags it should have around it equal to the number of squares flagged around it
  private boolean is_satisfied;
  
  // the number of spaces adjacent to the square which you are on which have not been opened
  private int num_of_cleared_adjacent = 0;

  // num of flags near the square
  private int num_of_flags_adjacent = 0;

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
    
    if((r == 0) || (c == 0) || (r == name.get_row()) || (c == name.get_column())){
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
        // tr
        if(corner_quadrant == '1'){
          l_char = name.get_value((r-1), c);
          bl_char = name.get_value((r-1), (c-1));
          b_char = name.get_value(r, (c-1));
        }
        // tl
        else if(corner_quadrant == '2'){
          b_char = name.get_value(r, (c-1));
          br_char = name.get_value((r+1), (c-1));
          r_char = name.get_value((r+1), c);
        }
        //bl
        else if(corner_quadrant == '3'){
          t_char = name.get_value(r, (c+1));
          r_char = name.get_value((r+1), c);
          tr_char = name.get_value((r+1), (c+1));
        }
        //br
        else if(corner_quadrant == '4'){
          t_char = name.get_value(r, (c+1));
          tl_char = name.get_value((r-1), (c+1));
          l_char = name.get_value((r-1), c);
        }
      }
      // is border
      else if(is_border){
        // top square
        if(border_type == 't'){
          l_char = name.get_value((r-1), c);
          bl_char = name.get_value((r-1), (c-1));
          b_char = name.get_value(r, (c-1));
          br_char = name.get_value((r+1), (c-1));
          r_char = name.get_value((r+1), c);
        }
        // left square
        else if(border_type == 'l'){
          t_char = name.get_value(r, (c+1));
          b_char = name.get_value(r, (c-1));
          br_char = name.get_value((r+1), (c-1));
          r_char = name.get_value((r+1), c);
          tr_char = name.get_value((r+1), (c+1));
        }
        // bottom square
        else if(border_type == 'b'){
          t_char = name.get_value(r, (c+1));
          tl_char = name.get_value((r-1), (c+1));
          l_char = name.get_value((r-1), c);
          r_char = name.get_value((r+1), c);
          tr_char = name.get_value((r+1), (c+1));
        }
        // right square
        else if(border_type == 'r'){
          t_char = name.get_value(r, (c+1));
          tl_char = name.get_value((r-1), (c+1));
          l_char = name.get_value((r-1), c);
          bl_char = name.get_value((r-1), (c-1));
          b_char = name.get_value(r, (c-1));
        }
      }
    }
    // is a normal piece
    else{
      t_char = name.get_value(r, (c+1));
      tl_char = name.get_value((r-1), (c+1));
      l_char = name.get_value((r-1), c);
      bl_char = name.get_value((r-1), (c-1));
      b_char = name.get_value(r, (c-1));
      br_char = name.get_value((r+1), (c-1));
      r_char = name.get_value((r+1), c);
      tr_char = name.get_value((r+1), (c+1));
    }
  }

  // counts the number of revealed adjacent tiles and flags
  public void update_cleared_or_adjacent(int r, int c, gameboard name){
    if (is_corner || is_border){
      // is corner
      if(is_corner){
        // tr
        if(corner_quadrant == '1'){
          if(l_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(l_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(bl_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(bl_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(b_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(b_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
        }
        // tl
        else if(corner_quadrant == '2'){
          if(r_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(r_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(br_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(br_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(b_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(b_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
        }
        //bl
        else if(corner_quadrant == '3'){
          if(r_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(r_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(tr_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(tr_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(t_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(t_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
        }
        //br
        else if(corner_quadrant == '4'){
          if(l_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(l_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(tl_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(tl_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(t_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(t_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
        }
      }
      // is border
      else if(is_border){
        // top square
        if(border_type == 't'){
          if(l_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(l_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(bl_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(bl_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(b_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(b_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(br_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(br_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(r_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(r_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
        }
        // left square
        else if(border_type == 'l'){
          if(t_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(t_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(br_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(br_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(b_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(b_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(tr_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(tr_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(r_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(r_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
        }
        // bottom square
        else if(border_type == 'b'){
          if(l_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(l_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(tl_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(tl_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(t_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(t_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(tr_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(tr_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(r_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(r_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
        }
        // right square
        else if(border_type == 'r'){
          if(l_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(l_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(bl_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(bl_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(b_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(b_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(tl_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(tl_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
          if(t_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
          if(t_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
        }
      }
    }
    else{
      if(l_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
      if(l_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
      if(bl_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
      if(bl_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
      if(b_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
      if(b_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
      if(br_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
      if(br_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
      if(r_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
      if(r_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
      if(tl_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
      if(tl_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
      if(t_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
      if(t_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
      if(tr_char != 'n'){num_of_cleared_adjacent = num_of_cleared_adjacent + 1;}
      if(tr_char == 'f'){num_of_flags_adjacent = num_of_flags_adjacent + 1;}
    }
  }

  // checks to see if the square has fullfilled the number it needs
  public void check_if_satisfied(int r, int c, gameboard name){
    if (num_of_flags_adjacent == current_square_as_int){
      is_satisfied = true;
    }
    else if(num_of_flags_adjacent > current_square_as_int){
      System.out.println("Error");
    }
    else{
      is_satisfied = false;
    }
 }

  // takes the char value if it is numerical and converts to integer
  public void equate_char_val_to_int(char val){
    if(val == '0'){
      current_square_as_int = 0;
    }
    else if(val == '1'){
      current_square_as_int = 1;
    }
    else if(val == '2'){
      current_square_as_int = 2;
    }
    else if(val == '3'){
      current_square_as_int = 3;
    }
    else if(val == '4'){
      current_square_as_int = 4;
    }
    else if(val == '5'){
      current_square_as_int = 5;
    }
    else if(val == '6'){
      current_square_as_int = 6;
    }
    else if(val == '7'){
      current_square_as_int = 7;
    }
    else if(val == '8'){
      current_square_as_int = 8;
    }
  }

  // prints the row and column of squares which should be cleared
  public void print_clear_spaces(int r, int c, gameboard name){
    for(int row = -1; row < 2; row++){
      for(int column = -1; row < 2; column++){
        if(!((row == 0) && (column == 0))){
          System.out.println("Row " + (r+row) + ", column " + (c+column) + " is a clear slot");
        }
      }
    }
  }

  // if adjacent flags is 0 and the number of open spaces is equivalent to the value of the square, then the open spaces are flags
  public void check_if_expected_matches_unkown(int r, int c, gameboard name){
    if (adjacent_flags == 0){
      if ((squares_near_said_square - num_of_cleared_adjacent) == current_square_as_int){
        // all open spaces are null and should be flags
      }
    }
  }

  // update all squares nearby with a value (set them as flags)
  public void update_all_adjacent_with_flags(int r, int c, gameboard name){
    if (t_char == 'n'){
      t_char  = 'f';
    }
    if (tl_char == ''){
      tl_char = 'f';
    }
    if (l_char == ''){
      l_char = 'f';
    }
    if (bl_char == ''){
      bl_char = 'f';
    }
    if (b_char == ''){
      b_char = 'f';
    }
    if (br_char == ''){
      br_char = 'f';
    }
    if (r_char == ''){
      r_char = 'f';
    }
    if (tr_char == ''){
      tr_char = 'f';
    }
  }
  /*
  Logic:
  1) check to see if the value is satisfied in what it knows is marked already
    if this is the case then tell the user to clear out all space besides it
  2) check to see if the remainind adjacent spaces is equivalent too that of all the mines it needs to fill
    if this is the case set all of their values to flags

  Complex logic:
  1) Preconditions: 
    there is a 1 and a 2 adjacent to one another
    there are 3 open spaces for the 2

    What it means:
      The one space which is not shared by the 1 and the 2 is a flag 100% of the time

If max number of flags is equal to the number of spaces flagged, check to maek sure that all squares are satisified
  */

  // if there isn't enough information skip it and return later
  
  /* special rules:
    - if there are unkowns adjacent to both a 1 and a 2, the space which is not adjacent to the 1 but is adjacent to the two should be a flag given that the two only has three adjacent
    - 
  */
}