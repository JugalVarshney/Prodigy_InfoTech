import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Task_02 extends JFrame {
    private int randomNumber;
    private JTextField guessField;
    private JLabel feedbackLabel;
    private int attempts;

    public Task_02() {
        setTitle("Number Guessing Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));
        setPreferredSize(new Dimension(350, 200));

        JLabel titleLabel = new JLabel("Guess the Number!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        add(titleLabel);

        guessField = new JTextField();
        guessField.setToolTipText("Enter your guess between 1 and 100");
        add(guessField);

        JButton guessButton = new JButton("Guess");
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });
        add(guessButton);

        feedbackLabel = new JLabel();
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(feedbackLabel);

        pack();
        setLocationRelativeTo(null); 

        generateRandomNumber();
    }

    private void generateRandomNumber() {
        Random random = new Random();
        randomNumber = random.nextInt(100) + 1; 
        attempts = 0;
    }

    private void checkGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());
            attempts++;

            int difference = randomNumber - guess;

            if (difference == 0) {
                feedbackLabel.setText("Congratulations! You guessed the number in " + attempts + " attempts.");
                generateRandomNumber(); // Start a new game
            } else if (difference > -3 && difference < 3) {
                feedbackLabel.setText("Almost there!!");
            } else if (difference >= -5 && difference <= -3) {
                feedbackLabel.setText("Slightly high.");
            } else if (difference <= 5 && difference >= 3) {
                feedbackLabel.setText("Slightly low.");
            } else if (difference >= -10 && difference < -5) {
                feedbackLabel.setText("High.");
            } else if (difference > 5 && difference <= 10) {
                feedbackLabel.setText("Low.");
            } else if (difference <= -10) {
                feedbackLabel.setText("Too high!");
            } else {
                feedbackLabel.setText("Too low!");
            }
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Invalid input. Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Task_02().setVisible(true);
            }
        });
    }
}
