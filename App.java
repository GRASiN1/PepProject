import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;

public class App {
    static Scanner sc = new Scanner(System.in);
    final static int M = sc.nextInt();
    static JLabel[][] jLabel = new JLabel[M][M];
    static int board[][] = new int[M][M];
    static ImageIcon queenIcon;

    static void printSolution() {
        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < M; ++j) {
                System.out.printf("%d ", board[i][j]);
            }
            System.out.printf("\n");
        }
    }

    static boolean isSafe(int row, int col) {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < col; ++i)
            if (board[row][i] == 1)
                return false;

        for (int i = row, j = col; i >= 0 && j >= 0; --i, --j) {
            if (board[i][j] == 1)
                return false;
        }

        for (int i = row, j = col; i < M && j >= 0; ++i, --j) {
            if (board[i][j] == 1)
                return false;
        }

        return true;
    }

    static boolean findSolution(int col) {
        if (col >= M)
            return true;

        boolean foundSolution = false;

        for (int i = 0; i < M; ++i) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSafe(i, col)) {
                board[i][col] = 1;
                jLabel[i][col].setIcon(queenIcon);
                jLabel[i][col].setBackground(Color.ORANGE);

                foundSolution = findSolution(col + 1);

                if (foundSolution) {
                    return true;
                } else {
                    board[i][col] = 0;
                    jLabel[i][col].setIcon(null);
                    jLabel[i][col].setBackground((i + col) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
                }
            }
        }

        return foundSolution;
    }

    static void solveNQueen() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < M; ++j) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                board[i][j] = 0;
                jLabel[i][j].setIcon(null);
                jLabel[i][j].setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
            }
        }

        if (findSolution(0)) {

            JOptionPane.showMessageDialog(null, "Solution found!", "N-Queens Solution",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("No Solution.");
        }
    }

    App() {
        JFrame jFrame = new JFrame("NQueen Visualizer");
        jFrame.setSize(400, 400);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel chessboardPanel = new JPanel(new GridLayout(M, M));
        chessboardPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jFrame.add(chessboardPanel);

        try {
            URL imageUrl = new URL(
                    "https://imgs.search.brave.com/Z9wN86G-Ff35cfPjz_sF7E6eM3a5uHB4jhtV3WmvO30/rs:fit:500:0:0:0/g:ce/aHR0cHM6Ly93d3cu/cG5nYWxsLmNvbS93/cC1jb250ZW50L3Vw/bG9hZHMvMS9RdWVl/bi1QTkctRmlsZS5w/bmc");
            BufferedImage queenImage = ImageIO.read(imageUrl);
            if (queenImage != null) {
                queenIcon = new ImageIcon(queenImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            } else {
                System.err.println("Failed to load queen image from URL.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < M; ++j) {
                jLabel[i][j] = new JLabel();
                jLabel[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                jLabel[i][j].setOpaque(true);
                jLabel[i][j].setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);

                jLabel[i][j].setBorder(new LineBorder(Color.BLACK));

                chessboardPanel.add(jLabel[i][j]);
            }
        }

        jFrame.setVisible(true);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            new Thread(() -> {
                app.solveNQueen();
            }).start();
        });
    }
}
