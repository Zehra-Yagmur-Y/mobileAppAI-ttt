package com.example.tictactoe;

import java.util.ArrayList;

public class GameLogic {
    private final int[][] board; // Oyun tahtası (3x3)
    private int currentPlayer; // Şu anki oyuncu (1 = X, 2 = O)
    private int movesCount; // Kaç hamle yapıldı

    private int playerXScore = 0; // X oyuncusunun skoru
    private int playerOScore = 0; // O oyuncusunun skoru

    public GameLogic() {
        board = new int[3][3]; // 3x3 boş oyun tahtası
        currentPlayer = 1; // X başlar
        movesCount = 0;
    }

    // Oyuncunun hamlesini yapmasını sağlar
    public boolean makeMove(int row, int col) {
        if (board[row][col] == 0) { // Eğer kare boşsa
            board[row][col] = currentPlayer; // Şu anki oyuncunun hamlesini yerleştir
            movesCount++; // Hamle sayısını artır
            return true; // Hamle başarılı
        }
        return false; // Hamle yapılamadı (Kare dolu)
    }

    // Kazananı kontrol eder
    public int checkWinner() {
        // Satırları kontrol et
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                updateScore(board[i][0]); // Skor güncelle
                return board[i][0]; // Kazanan oyuncu
            }
        }

        // Sütunları kontrol et
        for (int i = 0; i < 3; i++) {
            if (board[0][i] != 0 && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                updateScore(board[0][i]);
                return board[0][i]; // Kazanan oyuncu
            }
        }

        // Çaprazları kontrol et
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            updateScore(board[0][0]);
            return board[0][0]; // Sol üstten sağ alta çapraz
        }

        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            updateScore(board[0][2]);
            return board[0][2]; // Sağ üstten sol alta çapraz
        }

        // Beraberlik kontrolü (tüm kareler dolu)
        if (movesCount == 9) {
            return 0; // Beraberlik
        }

        return -1; // Oyun devam ediyor
    }

    // 🏆 Kazananın skorunu güncelle
    private void updateScore(int winner) {
        if (winner == 1) {
            playerXScore++;
        } else if (winner == 2) {
            playerOScore++;
        }
    }

    // 🎯 Boş hücreleri döndür (AI için)
    public ArrayList<int[]> getEmptyCells() {
        ArrayList<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }
        return emptyCells;
    }

    // 🤖 AI için kazanma hamlesini bul (Orta zorluk)
    public int[] findWinningMove() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {  // Boşsa deneyelim
                    board[i][j] = currentPlayer;
                    if (checkWinner() == currentPlayer) {
                        board[i][j] = 0; // Geri al
                        return new int[]{i, j};
                    }
                    board[i][j] = 0; // Geri al
                }
            }
        }
        return null;  // Kazanma hamlesi yoksa null döndür
    }

    // 🔥 Minimax Algoritması (Zor AI için)
    public int[] getBestMinimaxMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;

        for (int[] move : getEmptyCells()) {
            board[move[0]][move[1]] = currentPlayer;
            int score = minimax(false);
            board[move[0]][move[1]] = 0; // Hamleyi geri al

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    // 📊 Minimax Hesaplama
    private int minimax(boolean isMaximizing) {
        int winner = checkWinner();
        if (winner == 1) return -10;
        if (winner == 2) return 10;
        if (getEmptyCells().isEmpty()) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int[] move : getEmptyCells()) {
                board[move[0]][move[1]] = 2;
                int score = minimax(false);
                board[move[0]][move[1]] = 0;
                bestScore = Math.max(score, bestScore);
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int[] move : getEmptyCells()) {
                board[move[0]][move[1]] = 1;
                int score = minimax(true);
                board[move[0]][move[1]] = 0;
                bestScore = Math.min(score, bestScore);
            }
            return bestScore;
        }
    }

    // 🏆 Skorları döndür
    public int getPlayerXScore() {
        return playerXScore;
    }

    public int getPlayerOScore() {
        return playerOScore;
    }

    // Oyuncu değiştir
    public void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    // Şu anki oyuncuyu döndür
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    // Oyun tahtasını döndür
    public int[][] getBoard() {
        return board;
    }

    // 🛑 Oyunu sıfırla
    public void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }
        currentPlayer = 1; // X başlar
        movesCount = 0;
    }
}
