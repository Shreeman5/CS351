package sample;

import java.io.*;
import java.net.URL;

public class Board {
    String[][] board = null;
    String[][] fakeBoard = null;

    /**
     * make two boards, one whilst the player fiddles around the board,
     * and the other being a permanent once player makes move
     * @param file
     */
    public Board(String file){
        int arrLen = 0;
        int counterX = 0;
        int counterY = 0;

        InputStream path = this.getClass().getResourceAsStream(file);
        //File f = new File(path.getFile());
        BufferedReader reader;
        try{
            reader = new BufferedReader(new InputStreamReader(path));
            String line = reader.readLine();
            while(line != null){
                if(line.length() == 1 || line.length() == 2){
                    arrLen = Integer.valueOf(line);
                    board = new String[arrLen][arrLen];
                    fakeBoard = new String[arrLen][arrLen];
                }
                else{
                    int length = line.length();
                    int i = 0;
                    while(i < length){
                        board[counterX][counterY] = line.substring(i, i+2);
                        fakeBoard[counterX][counterY] = line.substring(i, i + 2);
                        counterY++;
                        i = i + 3;
                    }
                    counterY = 0;
                    counterX++;
                }
                line = reader.readLine();
            }
            reader.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * send real board
     * @return
     */
    public String[][] sendBoard(){
        return board;
    }

    /**
     * send changing board
     * @return
     */
    public String[][] getFakeBoard(){
        return fakeBoard;
    }

    public int boardLength(){
        return board.length;
    }

}
