package com.example.flemm.danielflemming_mpp1;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;



public class TicTacToeGame extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String KEY_INDEX = "index";

    int board[][]; //store board moves
    int i, j = 0; //for my loops
    Button button[][]; //array for button clicks
    Button newGame;
    TextView textView;
    computer computer;

    private int mBoardPosition = 0;


    // define the SharedPreferences object
    private SharedPreferences savedValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState != null) {
            mBoardPosition = savedInstanceState.getInt(KEY_INDEX, 0);
        }


        computer = new computer();
        button = new Button[4][4];
        board = new int[4][4];

        /**The better way to so it is have an array so i dont have to individually
         set the listeners for each button*
         Get null pointer exception when array size isnt set */

        textView = findViewById(R.id.textView);
        newGame = findViewById(R.id.newGame);

        button[0][0] = findViewById(R.id.square1);
        button[0][1] = findViewById(R.id.square2);
        button[0][2] = findViewById(R.id.square3);

        button[1][0] = findViewById(R.id.square4);
        button[1][1] = findViewById(R.id.square5);
        button[1][2] = findViewById(R.id.square6);


        button[2][0] = findViewById(R.id.square7);
        button[2][1] = findViewById(R.id.square8);
        button[2][2] = findViewById(R.id.square9);

        /** Button listener for new game... calls clear grid */
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearGrid();
            }
        });

        /**Set all the board pieces to equal 2.
         1 = computer 0 = user*/
        for (i = 0; i <= 2; i++){
            for(j = 0; j <= 2; j++){
                board[i][j] = 2;
            }
        }

        mBoardPosition = board[i][j];
        textView.setText(" ");



        /**Add the listeners for each button done most efficiently
         The reason why I changed all my code
         If the button wansnt clicked set to enables and blank text*/
        for (i = 0; i <= 2; i++){
            for(j = 0; j <= 2; j++){
                button[i][j].setOnClickListener(new MyClickListener(i,j));
                if(!button[i][j].isEnabled()){
                    button[i][j].setText(" ");
                    button[i][j].setEnabled(true);
                }
            }
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt(KEY_INDEX, mBoardPosition);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }



    /** Start new game by clearing the board and set the starting values*/



    class MyClickListener implements View.OnClickListener {
        int x;
        int y;


        public MyClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }


        public void onClick(View view) {
            if (button[x][y].isEnabled()) {
                button[x][y].setEnabled(false);
                button[x][y].setText("O");
                board[x][y] = 0;
                textView.setText("");
                if (!checkForGameOver()) {
                    computer.takeTurn();
                }
            }
        }
    }


    /**allows the computer to check the board and make a move*/
    private class computer {

        public void takeTurn() {

            /**Checking for top row*/
            if(board[0][0]==2 &&
                    ((board[0][1]==0 && board[0][2]==0) ||
                            (board[1][1]==0 && board[2][2]==0) ||
                            (board[1][0]==0 && board[2][0]==0))) {
                changeSquare(0,0);

            } else if (board[0][1]==2 &&
                    ((board[1][1]==0 && board[2][1]==0) ||
                            (board[0][0]==0 && board[0][2]==0))) {
                changeSquare(0,1);

            } else if(board[0][2]==2 &&
                    ((board[0][0]==0 && board[0][1]==0) ||
                            (board[2][0]==0 && board[1][1]==0) ||
                            (board[1][2]==0 && board[2][2]==0))) {
                changeSquare(0,2);

                /**Checking for the middle row*/
            } else if(board[1][0]==2 &&
                    ((board[1][1]==0 && board[1][2]==0) ||
                            (board[0][0]==0 && board[2][0]==0))){
                changeSquare(1,0);

            } else if(board[1][1]==2 &&
                    ((board[0][0]==0 && board[2][2]==0) ||
                            (board[0][1]==0 && board[2][1]==0) ||
                            (board[2][0]==0 && board[0][2]==0) ||
                            (board[1][0]==0 && board[1][2]==0))) {
                changeSquare(1,1);

            } else if(board[1][2]==2 &&
                    ((board[1][0]==0 && board[1][1]==0) ||
                            (board[0][2]==0 && board[2][2]==0))) {
                changeSquare(1,2);

                /**Checking for Bottom row*/
            } else if(board[2][0]==2 &&
                    ((board[0][0]==0 && board[1][0]==0) ||
                            (board[2][1]==0 && board[2][2]==0) ||
                            (board[1][1]==0 && board[0][2]==0))){
                changeSquare(2,0);

            } else if(board[2][1]==2 &&
                    ((board[0][1]==0 && board[1][1]==0) ||
                            (board[2][0]==0 && board[2][2]==0))) {
                changeSquare(2,1);

            }else if( board[2][2]==2 &&
                    ((board[0][0]==0 && board[1][1]==0) ||
                            (board[0][2]==0 && board[1][2]==0) ||
                            (board[2][0]==0 && board[2][1]==0))) {
                changeSquare(2,2);

            } else {
                Random rand = new Random();

                int a = rand.nextInt(4);
                int b = rand.nextInt(4);

                while(a==0 || b==0 || board[a][b]!=2) {
                    a = rand.nextInt(4);
                    b = rand.nextInt(4);
                }
                changeSquare(a,b);
            }
        }

        /**Gets called everytime constantly checking for game over
         Disable the button and set text x = computer
         1 is for the computer if the game board piece = 1
         then we know that the computer has that square*/
        private void changeSquare(int x, int y) {
            button[x][y].setEnabled(false);
            button[x][y].setText("X");
            board[x][y] = 1;
            checkForGameOver();
        }
    }



    /** Check for game over and return boolean if it is*/
    private boolean checkForGameOver() {
        boolean gameOver = false;
        if ((board[0][0] == 0 && board[1][1] == 0 && board[2][2] == 0)
                || (board[0][2] == 0 && board[1][1] == 0 && board[2][0] == 0)
                || (board[0][1] == 0 && board[1][1] == 0 && board[2][1] == 0)
                || (board[0][2] == 0 && board[1][2] == 0 && board[2][2] == 0)
                || (board[0][0] == 0 && board[0][1] == 0 && board[0][2] == 0)
                || (board[1][0] == 0 && board[1][1] == 0 && board[1][2] == 0)
                || (board[2][0] == 0 && board[2][1] == 0 && board[2][2] == 0)
                || (board[0][0] == 0 && board[1][0] == 0 && board[2][0] == 0)) {
            textView.setText("Game over. You win!");
            gameOver = true;

        } else if ((board[0][0] == 1 && board[1][1] == 1 && board[2][2] == 1)
                || (board[0][2] == 1 && board[1][1] == 1 && board[2][0] == 1)
                || (board[0][1] == 1 && board[1][1] == 1 && board[2][1] == 1)
                || (board[0][2] == 1 && board[1][2] == 1 && board[2][2] == 1)
                || (board[0][0] == 1 && board[0][1] == 1 && board[0][2] == 1)
                || (board[1][0] == 1 && board[1][1] == 1 && board[1][2] == 1)
                || (board[2][0] == 1 && board[2][1] == 1 && board[2][2] == 1)
                || (board[0][0] == 1 && board[1][0] == 1 && board[2][0] == 1)) {
            textView.setText("Game over. You lost!");
            gameOver = true;

        } else {
            boolean empty = false;
            for(i=0; i <= 2; i++) {
                for(j=0; j <= 2; j++) {
                    if(board[i][j]==2) {
                        empty = true;
                        break;
                    }
                }
            }
            if(!empty) {
                gameOver = true;
                textView.setText("Game over. It's a draw!");
            }
        }
        return gameOver;
    }

    public void clearGrid(){
        // Go through board and set all values = to 2 then set text to " "
        for (i = 0; i <= 2; i++){
            for(j = 0; j <= 2; j++){
                board[i][j] = 2;
                button[i][j].setText(" ");
                button[i][j].setEnabled(true);
            }
        }


    }

}


