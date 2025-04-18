import java.awt.*;
import javax.swing.*;

public class TicTacToe {
    JFrame frame = new JFrame("Tic-Tac-Toe");
    JPanel boardPanel = new JPanel(new GridLayout(3, 3));
    JPanel headerPanel = new JPanel(new BorderLayout());
    JPanel footerPanel = new JPanel(new GridLayout(1, 3));
    JButton[][] buttons = new JButton[3][3];
    JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
    JLabel scoreXLabel = new JLabel();
    JLabel scoreOLabel = new JLabel();
    JButton restartBtn = new JButton("Restart");

    String playerX = "Player X", playerO = "Player O", currentPlayer = playerX;
    int scoreX = 0, scoreO = 0, turn = 0;
    boolean gameEnded = false;

    public static void main(String[] args) {
        new TicTacToe().showPlayerInputScreen();
    }

    void showPlayerInputScreen() {
        JTextField xInput = new JTextField();
        JTextField oInput = new JTextField();
        Object[] fields = {
            "Enter Player X Name:", xInput,
            "Enter Player O Name:", oInput
        };
        int result = JOptionPane.showConfirmDialog(null, fields, "Start Game", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (!xInput.getText().trim().isEmpty()) playerX = xInput.getText().trim();
            if (!oInput.getText().trim().isEmpty()) playerO = oInput.getText().trim();
        }
        currentPlayer = playerX;
        setupGame();
    }

    void setupGame() {
        frame.setSize(600, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE);

        // Header label
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        statusLabel.setForeground(Color.DARK_GRAY);
        statusLabel.setText(currentPlayer + "'s Turn");
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(statusLabel, BorderLayout.CENTER);

        // Game board
        boardPanel.setBackground(Color.LIGHT_GRAY);
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++) {
                JButton btn = new JButton("");
                btn.setFont(new Font("Segoe UI", Font.BOLD, 60));
                btn.setFocusPainted(false);
                btn.setBackground(Color.WHITE);
                btn.setForeground(Color.BLACK);
                btn.addActionListener(e -> handleMove(btn));
                buttons[r][c] = btn;
                boardPanel.add(btn);
            }

        // Footer (scores + restart)
        restartBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        restartBtn.addActionListener(e -> resetBoard());
        updateScores();

        footerPanel.add(scoreXLabel);
        footerPanel.add(restartBtn);
        footerPanel.add(scoreOLabel);
        footerPanel.setBackground(Color.WHITE);

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(footerPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    void handleMove(JButton btn) {
        if (!btn.getText().equals("") || gameEnded) return;

        btn.setText(currentPlayer.equals(playerX) ? "X" : "O");
        btn.setForeground(currentPlayer.equals(playerX) ? Color.BLUE : Color.ORANGE);
        turn++;

        if (checkWinner()) {
            gameEnded = true;
            if (currentPlayer.equals(playerX)) scoreX++;
            else scoreO++;
            statusLabel.setText(currentPlayer + " Wins!");
            updateScores();
        } else if (turn == 9) {
            statusLabel.setText("It's a Tie!");
            gameEnded = true;
        } else {
            currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
            statusLabel.setText(currentPlayer + "'s Turn");
        }
    }

    boolean checkWinner() {
        // Row check
        for (int i = 0; i < 3; i++)
            if (match(buttons[i][0], buttons[i][1], buttons[i][2]))
                return highlight(buttons[i][0], buttons[i][1], buttons[i][2]);

        // Column check
        for (int i = 0; i < 3; i++)
            if (match(buttons[0][i], buttons[1][i], buttons[2][i]))
                return highlight(buttons[0][i], buttons[1][i], buttons[2][i]);

        // Diagonal check
        if (match(buttons[0][0], buttons[1][1], buttons[2][2]))
            return highlight(buttons[0][0], buttons[1][1], buttons[2][2]);

        if (match(buttons[0][2], buttons[1][1], buttons[2][0]))
            return highlight(buttons[0][2], buttons[1][1], buttons[2][0]);

        return false;
    }

    boolean match(JButton b1, JButton b2, JButton b3) {
        String t1 = b1.getText(), t2 = b2.getText(), t3 = b3.getText();
        return !t1.equals("") && t1.equals(t2) && t2.equals(t3);
    }

    boolean highlight(JButton b1, JButton b2, JButton b3) {
        Color winColor = new Color(144, 238, 144); // Light green
        b1.setBackground(winColor);
        b2.setBackground(winColor);
        b3.setBackground(winColor);
        return true;
    }

    void updateScores() {
        scoreXLabel.setText(playerX + ": " + scoreX);
        scoreOLabel.setText(playerO + ": " + scoreO);
        scoreXLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreOLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreXLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        scoreOLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        scoreXLabel.setForeground(Color.BLUE);
        scoreOLabel.setForeground(Color.ORANGE);
    }

    void resetBoard() {
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++) {
                buttons[r][c].setText("");
                buttons[r][c].setBackground(Color.WHITE);
            }
        turn = 0;
        gameEnded = false;
        currentPlayer = playerX;
        statusLabel.setText(currentPlayer + "'s Turn");
    }
}
