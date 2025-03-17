package com.example.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;

public class ScoreManager {
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "TicTacToeScores";  // Skorları saklamak için SharedPreferences adı
    private static final String KEY_SCORE_X = "scoreX";
    private static final String KEY_SCORE_O = "scoreO";

    public ScoreManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // X veya O'nun skorunu artır
    public void incrementScore(String player) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (player.equals("X")) {
            int currentScore = sharedPreferences.getInt(KEY_SCORE_X, 0);
            editor.putInt(KEY_SCORE_X, currentScore + 1);
        } else if (player.equals("O")) {
            int currentScore = sharedPreferences.getInt(KEY_SCORE_O, 0);
            editor.putInt(KEY_SCORE_O, currentScore + 1);
        }

        editor.apply();  // Değişiklikleri kaydet
    }

    // X veya O'nun skorunu al
    public int getScore(String player) {
        if (player.equals("X")) {
            return sharedPreferences.getInt(KEY_SCORE_X, 0);
        } else if (player.equals("O")) {
            return sharedPreferences.getInt(KEY_SCORE_O, 0);
        }
        return 0;  // Geçersiz oyuncu girilirse 0 döndür
    }

    // Skorları sıfırla
    public void resetScores() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_SCORE_X, 0);
        editor.putInt(KEY_SCORE_O, 0);
        editor.apply();
    }
}
