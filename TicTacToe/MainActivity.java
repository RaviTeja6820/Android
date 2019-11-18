package com.example.tictactoe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] wins = { {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 4, 8}, {2, 4, 6}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8} };
    int activePlayer = 0;
    boolean gameActive = true;
    public void dropIn(View view){
        ImageView ttt = (ImageView) view;

        int tappedCounter = Integer.parseInt(ttt.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameActive){
            gameState[tappedCounter] = activePlayer;

            ttt.setTranslationY(-1500);

            if( activePlayer == 0) {
                ttt.setImageResource(R.drawable.o);

                activePlayer = 1;
            } else {
                ttt.setImageResource(R.drawable.x);

                activePlayer = 0;
            }

            ttt.animate().translationYBy(1500).setDuration(300);

            for(int[] winningPosition : wins) {

                if ( gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2){
                    gameActive = false;
                    Context context;
                    String text, winner;
                    if(activePlayer == 1) {
                        winner = "Mr.O";
                    } else {
                        winner = "Mr.X";
                    }

                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

                    winnerTextView.setText(winner + " has won!!");

                    winnerTextView.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    public void playAgain(View view){
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        for (int i = 0; i < layout.getChildCount(); i++) {

            View v = (View) layout.getChildAt(i);
            if (v instanceof ImageView) {
                //validate your EditText here
                ((ImageView) v).setImageDrawable(null);
            }

        }
        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }
        activePlayer = 0;
        gameActive = true;

        winnerTextView.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
