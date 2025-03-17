package com.example.tictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private GameLogic game;
    private TicTacToeAI ai;  // AI Nesnesi
    private Button[][] buttons = new Button[3][3];
    private TextView statusText;
    private TextView scoreText;
    private ScoreManager scoreManager;

    private String difficultyLevel; // Zorluk seviyesi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Skor yöneticisini başlat
        scoreManager = new ScoreManager(this);

        // Ana ekrandan gelen zorluk seviyesini al
        difficultyLevel = getIntent().getStringExtra("difficulty");

        game = new GameLogic();
        ai = new TicTacToeAI(); // AI'yı başlat

        statusText = findViewById(R.id.statusText);
        scoreText = findViewById(R.id.scoreText);
        statusText.setText("Zorluk: " + difficultyLevel);
        updateScoreDisplay(); // Skorları göster

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        // 3x3 butonları tanımla ve tıklama olaylarını ayarla
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int row = i, col = j;
                String buttonID = "button" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);

                buttons[i][j].setOnClickListener(v -> handleMove(row, col));
            }
        }

        // Reset Butonu
        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(v -> resetGame());
    }

    private void handleMove(int row, int col) {
        if (game.makeMove(row, col)) {
            buttons[row][col].setText(game.getCurrentPlayer() == 1 ? "X" : "O");
            int winner = game.checkWinner();

            if (winner == 1) {
                scoreManager.incrementScore("X");
                showWinnerDialog("X");
            } else if (winner == 2) {
                scoreManager.incrementScore("O");
                showWinnerDialog("O");
            } else if (winner == 0) {
                statusText.setText("Berabere!");
                showWinnerDialog("Kimse");
            } else {
                game.switchPlayer();
                statusText.setText("Sıra: " + (game.getCurrentPlayer() == 1 ? "X" : "O"));

                // Eğer AI'nın sırasıysa, hamlesini yap
                if (game.getCurrentPlayer() == 2) {
                    aiMakeMove();
                }
            }

            updateScoreDisplay(); // Skorları güncelle
        }
    }

    private void aiMakeMove() {
        int[] aiMove = ai.getAIMove(game.getBoard(), difficultyLevel);
        int row = aiMove[0];
        int col = aiMove[1];

        game.makeMove(row, col);
        buttons[row][col].setText("O");

        int winner = game.checkWinner();

        if (winner == 1) {
            showWinnerDialog("X");
        } else if (winner == 2) {
            showWinnerDialog("O");
        } else if (winner == 0) {
            statusText.setText("Berabere!");
            showWinnerDialog("Kimse");
        } else {
            game.switchPlayer();
            statusText.setText("Sıra: X");
        }
    }

    private void showWinnerDialog(String winner) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Oyun Bitti!");
        builder.setMessage(winner + " kazandı! 🎉");

        builder.setPositiveButton("Yeniden Oyna", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetGame();
            }
        });

        builder.setNegativeButton("Ana Menü", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    public void resetGame() {
        game.resetGame();
        statusText.setText("Sıra: X");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
    }

    private void updateScoreDisplay() {
        int scoreX = scoreManager.getScore("X");
        int scoreO = scoreManager.getScore("O");
        scoreText.setText("X: " + scoreX + " | O: " + scoreO);
    }
}
