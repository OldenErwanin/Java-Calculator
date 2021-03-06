import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator implements ActionListener {
    JFrame frame;
    JTextField textField;
    JButton[] numberButtons = new JButton[10];
    JButton[] functionButtons = new JButton[8];
    JButton addButton, subButton, mulButton, divButton;
    JButton decButton, equButton, delButton, clrButton;
    JPanel panel;
    Font myFont = new Font("Ink Free", Font.BOLD, 30);

    Calculator() {
        // Create frame, set the parameters
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 550);
        frame.setLayout(null);

        // Create textfield, set the parameters
        textField = new JTextField();
        textField.setBounds(50, 25, 300, 50);
        textField.setFont(myFont);
        textField.setEditable(false);

        // Create function buttons, add them to the array
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("Delete");
        clrButton = new JButton("Clear");
        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = divButton;
        functionButtons[4] = decButton;
        functionButtons[5] = equButton;
        functionButtons[6] = delButton;
        functionButtons[7] = clrButton;

        // Set function buttons functionality
        for (int i = 0; i < functionButtons.length; i++) {
            functionButtons[i].addActionListener(this);
            functionButtons[i].setFont(myFont);
            functionButtons[i].setFocusable(false);
        }
        // Set number buttons functionality
        for (int i = 0; i < numberButtons.length; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
        }

        delButton.setBounds(50, 430, 145, 50);
        clrButton.setBounds(205, 430, 145, 50);

        // Create grid panels, add the buttons to the grid in order
        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);
        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(equButton);
        panel.add(divButton);

        // Add elements to the frame
        frame.add(panel);
        frame.add(delButton);
        frame.add(clrButton);
        frame.add(textField);
        frame.setVisible(true);
    }

    // Constructor, generate calculator
    public static void main(String[] args) {
        Calculator calc = new Calculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Number buttons
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                textField.setText(textField.getText().concat(String.valueOf(i)));
            }
        }
        // Decimalize button
        if (e.getSource() == decButton) {
            if (!checkIfValid())
                return;
            textField.setText(textField.getText().concat("."));
        }
        // Add button
        if (e.getSource() == addButton) {

            if (!checkIfValid())
                return;
            textField.setText(textField.getText().concat("+"));
        }
        // Subtraction button
        if (e.getSource() == subButton) {
            if (!checkIfValid())
                return;
            textField.setText(textField.getText().concat("-"));
        }
        // Multiple button
        if (e.getSource() == mulButton) {
            if (!checkIfValid())
                return;
            textField.setText(textField.getText().concat("*"));
        }
        // Divide button
        if (e.getSource() == divButton) {
            if (!checkIfValid())
                return;
            textField.setText(textField.getText().concat("/"));
        }
        // Equivalent button
        if(e.getSource() == equButton) {
            if (!checkIfValid())
                DeleteLastCharacter();
            textField.setText(Double.toString(computeAnother(textField.getText())));
        }
        // Clear button (all the textfield)
        if (e.getSource() == clrButton) {
            textField.setText("");
        }
        // Delete button (last character)
        if (e.getSource() == delButton) {
            DeleteLastCharacter();
        }
    }

    // 3rd party code for convert a string containing math expression into a double
    private static double computeAnother(String equation) {
        double result = 0.0;
        String noMinus = equation.replace("-", "+-");
        String[] byPluses = noMinus.split("\\+");

        for (String multipl : byPluses) {
            String[] byMultipl = multipl.split("\\*");
            double multiplResult = 1.0;
            for (String operand : byMultipl) {
                if (operand.contains("/")) {
                    String[] division = operand.split("\\/");
                    double divident = Double.parseDouble(division[0]);
                    for (int i = 1; i < division.length; i++) {
                        divident /= Double.parseDouble(division[i]);
                    }
                    multiplResult *= divident;
                } else {
                    multiplResult *= Double.parseDouble(operand);
                }
            }
            result += multiplResult;
        }

        return result;
    }

    // These are just for checking for the last character. If it's a special one, delete it
    private void DeleteLastCharacter() {
        String string = textField.getText();
        textField.setText("");
        for (int i = 0; i < string.length() - 1; i++) {
            textField.setText(textField.getText() + string.charAt(i));
        }
    }
    private String getLastChar() {
        return Character.toString(textField.getText().charAt(textField.getText().length() - 1));

    }
    private boolean checkIfValid() {
        for (int i = 0; i < functionButtons.length; i++) {
            if (getLastChar().equals(functionButtons[i].getText())) {
                return false;
            }
        }
        return true;
    }
}
