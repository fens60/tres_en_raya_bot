package com.pmdm1.ejercicio7;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final ImageButton[][] buttons = new ImageButton[3][3];
    private boolean isHumanTurn = true; // true = turno del humano, false = turno del bot
    private int roundCount = 0;

    private TextView humanTextView, botTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializar los botones del tablero
        buttons[0][0] = findViewById(R.id.button1);
        buttons[0][1] = findViewById(R.id.button2);
        buttons[0][2] = findViewById(R.id.button3);
        buttons[1][0] = findViewById(R.id.button4);
        buttons[1][1] = findViewById(R.id.button5);
        buttons[1][2] = findViewById(R.id.button6);
        buttons[2][0] = findViewById(R.id.button7);
        buttons[2][1] = findViewById(R.id.button8);
        buttons[2][2] = findViewById(R.id.button9);

        //inicializar
        humanTextView = findViewById(R.id.humantxt);
        botTextView = findViewById(R.id.bottext);
        RadioButton easyRadioButton = findViewById(R.id.eazy);
        RadioButton difficultRadioButton = findViewById(R.id.difficult);

        //configurar listeners para los botones del tablero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setOnClickListener(this::onButtonClick);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void onButtonClick(View v) {
        ImageButton button = (ImageButton) v;

        //verificar si el boton esta ocupado
        if (button.getTag() != null) {
            return;
        }

        //turno del humano
        if (isHumanTurn) {
            button.setImageResource(R.drawable.symbox);
            button.setTag("X");
            humanTextView.setText("Turno del Bot");
            botTextView.setText("");
        }

        roundCount++;

        //comprobar si el humano ha ganado
        if (checkForWin()) {
            showWinner("Humano");
            return;
        }

        //si se completa el tablero sin ganador
        if (roundCount == 9) {
            showWinner("Empate");
            return;
        }

        //turno del bot
        isHumanTurn = false;
        botMove();
    }

    @SuppressLint("SetTextI18n")
    private void botMove() {
        //crear una lista de posiciones vacías
        ArrayList<Object> emptyPositions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getTag() == null) {
                    emptyPositions.add(new int[]{i, j});
                }
            }
        }

        //elegir una posición aleatoria de la lista
        if (!emptyPositions.isEmpty()) {
            Random random = new Random();
            int[] position = (int[]) emptyPositions.get( random.nextInt(emptyPositions.size()));

            //realizar el movimiento del bot
            buttons[position[0]][position[1]].setImageResource(R.drawable.symbo0);
            buttons[position[0]][position[1]].setTag("O");

            roundCount++;
            botTextView.setText("Turno del Humano");
            humanTextView.setText("");
            isHumanTurn = true;

            //verificar si el bot gana
            if (checkForWin()) {
                showWinner("Bot");
            } else if (roundCount == 9) {
                showWinner("Empate");
            }
        }
    }


    private boolean checkForWin() {
        //comprobar filas, columnas y diagonales
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getTag() != null &&
                    buttons[i][0].getTag().equals(buttons[i][1].getTag()) &&
                    buttons[i][0].getTag().equals(buttons[i][2].getTag())) {
                return true;
            }
            if (buttons[0][i].getTag() != null &&
                    buttons[0][i].getTag().equals(buttons[1][i].getTag()) &&
                    buttons[0][i].getTag().equals(buttons[2][i].getTag())) {
                return true;
            }
        }
        if (buttons[0][0].getTag() != null &&
                buttons[0][0].getTag().equals(buttons[1][1].getTag()) &&
                buttons[0][0].getTag().equals(buttons[2][2].getTag())) {
            return true;
        }
        return buttons[0][2].getTag() != null &&
                buttons[0][2].getTag().equals(buttons[1][1].getTag()) &&
                buttons[0][2].getTag().equals(buttons[2][0].getTag());
    }

    private void showWinner(String winner) {
        Toast.makeText(this, "Ganador: " + winner, Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    @SuppressLint("SetTextI18n")
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setImageDrawable(null);
                buttons[i][j].setTag(null);
            }
        }
        roundCount = 0;
        isHumanTurn = true;
        humanTextView.setText("Tu turno");
        botTextView.setText("");
    }
}