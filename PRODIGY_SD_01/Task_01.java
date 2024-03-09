import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Task_01 extends JFrame {
    private JLabel inputLabel, resultLabel;
    private JTextField inputField, resultField;
    private JComboBox<String> unitComboBox;
    private JButton convertButton;

    public Task_01() {
        setTitle("Temperature Converter");
        setSize(600, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        inputLabel = new JLabel("   Enter Temperature:");
        inputField = new JTextField();
        unitComboBox = new JComboBox<>(new String[]{"Celsius", "Fahrenheit", "Kelvin"});
        resultLabel = new JLabel("   Converted Temperatures:");
        resultField = new JTextField();
        resultField.setEditable(false);
        convertButton = new JButton("Convert");

        add(inputLabel);
        add(inputField);
        add(new JLabel("   Select Unit:"));
        add(unitComboBox);
        add(resultLabel);
        add(resultField);
        add(new JLabel()); // Empty label for spacing
        add(convertButton);

        convertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                convertTemperature();
            }
        });
    }

    private void convertTemperature() {
        try {
            double temperature = Double.parseDouble(inputField.getText());
            String unit = (String) unitComboBox.getSelectedItem();
            String result = "";

            if (unit.equals("Celsius")) {
                double fahrenheit = (temperature * 9 / 5) + 32;
                double kelvin = temperature + 273.15;
                result = String.format("%.2f Fahrenheit, %.2f Kelvin", fahrenheit, kelvin);
            } else if (unit.equals("Fahrenheit")) {
                double celsius = (temperature - 32) * 5 / 9;
                double kelvin = (temperature + 459.67) * 5 / 9;
                result = String.format("%.2f Celsius, %.2f Kelvin", celsius, kelvin);
            } else if (unit.equals("Kelvin")) {
                double celsius = temperature - 273.15;
                double fahrenheit = (temperature * 9 / 5) - 459.67;
                result = String.format("%.2f Celsius, %.2f Fahrenheit", celsius, fahrenheit);
            }

            resultField.setText(result);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid temperature!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Task_01().setVisible(true);
            }
        });
    }
}
