package com.example.tictactoe;

import java.util.Random;

public class TicTacToeAI {
    private Random random = new Random();

    public int[] getAIMove(int[][] board, String difficulty) {
        if (difficulty.equals("Kolay")) {
            return getRandomMove(board);
        } else if (difficulty.equals("Orta")) {
            return getSmartMove(board);
        } else {
            return getBestMove(board); // Zor seviye (Minimax)
        }
    }

    // Kolay Seviye: Rastgele hamle yapar
    private int[] getRandomMove(int[][] board) {
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != 0);
        return new int[]{row, col};
    }

    // Orta Seviye: Kazanabileceği veya rakibi engelleyebileceği hamle yapar
    private int[] getSmartMove(int[][] board) {
        // Önce kazanma hamlesi var mı kontrol et
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = 2; // AI deneme amaçlı O oynuyor
                    if (checkWinner(board) == 2) {
                        board[i][j] = 0; // Geri al
                        return new int[]{i, j};
                    }
                    board[i][j] = 0; // Geri al
                }
            }
        }

        // Rakibin kazanmasını engelle
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = 1; // Rakibin X oynadığını varsay
                    if (checkWinner(board) == 1) {
                        board[i][j] = 0; // Geri al
                        return new int[]{i, j};
                    }
                    board[i][j] = 0; // Geri al
                }
            }
        }

        // Eğer kazanma veya engelleme yoksa rastgele hamle yap
        return getRandomMove(board);
    }

    // Kazananı kontrol eden fonksiyon
    private int checkWinner(int[][] board) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0];
            }
            if (board[0][i] != 0 && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return board[0][i];
            }
        }
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        }
        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2];
        }
        return -1;
    }

    // Zor Seviye: Minimax Algoritması (En iyi hamleyi seçer)
    private int[] getBestMove(int[][] board) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[]{-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = 2; // AI oynuyor
                    int score = minimax(board, false);
                    board[i][j] = 0; // Geri al

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, j};
                    }
                }
            }
        }
        return bestMove;
    }

    // Minimax Algoritması
    private int minimax(int[][] board, boolean isMaximizing) {
        int winner = checkWinner(board);
        if (winner == 1) return -10;
        if (winner == 2) return 10;
        if (isBoardFull(board)) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = 2;
                        int score = minimax(board, false);
                        board[i][j] = 0;
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = 1;
                        int score = minimax(board, true);
                        board[i][j] = 0;
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    // Tahta tamamen dolu mu?
    private boolean isBoardFull(int[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) return false;
            }
        }
        return true;
    }
}
