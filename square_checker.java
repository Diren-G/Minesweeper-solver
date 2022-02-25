public class square_checker{
  // location of square being checked
  private int current_row;
  private int current_column;

  // value of square
  private char current_square_char;
  private boolean is_satisfied;
  private int num_of_cleared_adjacent;
  private boolean is_corner;
  private boolean is_border;

  // constants
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

  public void border_logic(int r, int c, gameboard name){
    if((r == 0) || (c == 0) || (r == name.get_row()) || (c == name.get_column()){
      // either border or corner
      
    }
  }
}