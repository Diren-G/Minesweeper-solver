public class square_checker{
  // location of said sqaure
  private int current_row;
  private int current_column;

  // value of square
  private char current_square_char;
  private int current_square_as_char;
  private boolean is_satisfied;
  private int num_of_cleared_adjacent;

  // need to be able to update the variables
  public void update_current_square(int r, int c){
    // check if the values are valid and updoot if true

    // 0 = no mines in all squares next to it
    // 1-8 = inidcates the number of mines near it
    // f = indicates that the spot has been flagged for having a mine
    // n = null or unopened
  }
}