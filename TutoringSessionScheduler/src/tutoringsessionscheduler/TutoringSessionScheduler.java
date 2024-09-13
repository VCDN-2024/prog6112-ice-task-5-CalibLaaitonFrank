package tutoringsessionscheduler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TutoringSessionScheduler {
    private JFrame frame;
    private JTextField studentNameField;
    private JComboBox<String> dayComboBox;
    private JComboBox<String> timeComboBox;
    private JButton submitButton;
    private DefaultListModel<String> listModel;
    private JList<String> sessionList;

    public TutoringSessionScheduler() {
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Tutoring Session Scheduler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create student name field
        studentNameField = new JTextField(20);

        // Create day combo box
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        dayComboBox = new JComboBox<>(days);

        // Create time combo box
        String[] times = new String[6];
        for (int i = 0; i < 6; i++) {
            times[i] = String.format("%d:00 PM", 3 + i);
        }
        timeComboBox = new JComboBox<>(times);

        // Create submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitButtonListener());

        // Create session list
        listModel = new DefaultListModel<>();
        sessionList = new JList<>(listModel);

        // Add components to frame
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Student Name:"));
        inputPanel.add(studentNameField);
        inputPanel.add(new JLabel("Day:"));
        inputPanel.add(dayComboBox);
        inputPanel.add(new JLabel("Time:"));
        inputPanel.add(timeComboBox);
        inputPanel.add(submitButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(sessionList), BorderLayout.CENTER);

        // Load data from file
        loadDataFromFile();

        frame.pack();
        frame.setVisible(true);
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String studentName = studentNameField.getText();
            String day = (String) dayComboBox.getSelectedItem();
            String time = (String) timeComboBox.getSelectedItem();

            // Save session details to file
            saveSessionToFile(studentName, day, time);

            // Add session to list
            listModel.addElement(studentName + " - " + day + " - " + time);

            // Clear input fields
            studentNameField.setText("");
        }
    }

    private void saveSessionToFile(String studentName, String day, String time) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("tutoring_sessions.txt", true))) {
            writer.println(studentName + "," + day + "," + time);
        } catch (IOException e) {
            System.err.println("Error saving session to file: " + e.getMessage());
        }
    }

    private void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tutoring_sessions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                listModel.addElement(parts[0] + " - " + parts[1] + " - " + parts[2]);
            }
        } catch (IOException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TutoringSessionScheduler();
            }
        });
    }
}

//REFERENCES
//Farell, J, 2019. Java Programming, 9th edition. Cengage Learning

