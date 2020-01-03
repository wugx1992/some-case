package indi.gxwu.other;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: gx.wu
 * @Date: 2020/1/2 11:43
 * @Description: 判断数独是否合法，该数独可能只填充了部分数字，其中缺少的数字用 . 表示。
 */
public class ValidSudokuTest {

    public static void main(String[] args) {
        ValidSudokuTest t = new ValidSudokuTest();
//        String[] ex1 = new String[]{
//                "........2","......6..","..14..8..",".........",".........","....3....","5.86.....",".9....4..","....5...."
//        };
//        char[][] board1 = t.transfer(ex1);
//        System.out.println(t.isValidSudoku(board1));
//
//        System.out.println("-----------------------------------------------");
//        String[] ex2 = new String[]{
//                "53..7....","6..195...",".98....6.","8...6...3","4..8.3..1","7...2...6",".6....28.","...419..5","....8..79"
//        };
//        char[][] board2 = t.transfer(ex2);
//        System.out.println(t.isValidSudoku(board2));
//
//        System.out.println("-----------------------------------------------");
//        String[] ex3 = new String[]{
//                "....5..1.",".4.3.....",".....3..1","8......2.","..2.7....",".15......",".....2...",".2.9.....","..4......"
//        };
//        char[][] board3 = t.transfer(ex3);
//        System.out.println(t.isValidSudoku(board3));
//
//        System.out.println("-----------------------------------------------");
//        String[] ex4 = new String[]{
//                "....5..1.",".4.3.....",".....3..1","8......2.","..2.7....",".15......",".....2...",".2.9.....","..4......"
//        };
//        char[][] board4 = t.transfer(ex4);
//        System.out.println(t.isValidSudoku(board4));

        t.fixSudoku();
    }


    public char[][] transfer(String[] ex) {
        char[][] board = new char[9][9];
        for (int i = 0; i < ex.length; i++) {
            String val = ex[i];
            for (int j = 0; j < val.length(); j++) {
                board[i][j] = val.charAt(j);
            }
        }
        print(board);
        return board;
    }

    public void print(char[][] board) {
        if(board==null){
            System.out.println("\nno found!!");
            return;
        }
        System.out.println("\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.printf(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * @param board: the board
     * @return: whether the Sudoku is valid
     */
    public boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            char[] innerBoard = board[i];
            Set<Character> uniqueNumber = new HashSet<Character>();
            for (int j = 0; j < innerBoard.length; j++) {
                if (innerBoard[j] == '.') {
                    continue;
                }
                if (uniqueNumber.contains(innerBoard[j])) {
                    return false;
                }
                uniqueNumber.add(innerBoard[j]);
            }
        }

        for (int j = 0; j < board.length; j++) {
            Set<Character> uniqueNumber = new HashSet<Character>();
            for (int i = 0; i < board.length; i++) {
                if (board[i][j] == '.') {
                    continue;
                }
                if (uniqueNumber.contains(board[i][j])) {
                    return false;
                }
                uniqueNumber.add(board[i][j]);
            }
        }

        for (int i = 0; i < board.length; i = i + 3) {
            for (int j = 0; j < board[i].length; j = j + 3) {
                Set<Character> uniqueNumber = new HashSet<Character>();
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        if (board[i + k][j + l] == '.') {
                            continue;
                        }
                        if (uniqueNumber.contains(board[i + k][j + l])) {
                            return false;
                        }
                        uniqueNumber.add(board[i + k][j + l]);
                    }
                }
            }
        }

        return true;
    }

    public void fixSudoku() {
        long begin = System.currentTimeMillis();
        System.out.println("-----------------------------------------------");
        String[] ex = new String[]{
                "..75...3.",
                ".68..9.45",
                ".5.32..6.",
                ".741.....",
                "19..7.628",
                ".32..64.1",
                "51.8.7392",
                "7..6915.4",
                "4..23...6"
        };
        ex = new String[]{
                "........2", "......6..", "..14..8..", ".........", ".........", "....3....", "5.86.....", ".9....4..", "....5...."
        };

        ex = new String[]{
                "53..7....", "6..195...", ".98....6.", "8...6...3", "4..8.3..1", "7...2...6", ".6....28.", "...419..5", "....8..79"
        };

//        ex = new String[]{
//                "....5..1.",".4.3.....",".....3..1","8......2.","..2.7....",".15......",".....2...",".2.9.....","..4......"
//        };
        char[][] board = transfer(ex);
        char[][] result = find(board);
        print(result);
        long end = System.currentTimeMillis();
        System.out.println("time cost : "+(end-begin));

    }

    public char[][] copy(char[][] board) {
        char[][] result = new char[9][9];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                result[i][j] = board[i][j];
            }
        }
        return result;
    }

    public char[][] find(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            Set<Character> numberSet = new HashSet<Character>();
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != '.') {
                    numberSet.add(board[i][j]);
                    continue;
                }
                boolean validResult = false;
                for (int n = 1; n <= 9; n++) {
                    char[][] temp = copy(board);
                    char val = String.valueOf(n).charAt(0);
                    if (numberSet.contains(val)) {
                        continue;
                    }
                    temp[i][j] = val;
                    validResult = isValidSudoku(temp);
                    System.out.println(i + " " + j + " " + n + " ~");
                    if (validResult == true) {
                        System.out.println(i + " " + j + " " + n + " OK");
                        char[][] findResult = find(temp);
                        if (findResult != null) {
                            return findResult;
                        } else {
                            System.out.println(i + " " + j + " " + n + " !");
                            validResult = false;
                        }
                    }
                }

                if (validResult == false) {
                    System.out.println(i + " " + j + " !");
                    return null;
                } else {
                    print(board);
                }
            }
        }
        return board;
    }

}
