import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class ToDoApp extends JFrame {
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskField;
    private JButton addButton = new JButton("Add Task");
    private JButton removeButton = new JButton("Remove Task");
    private static final String FILE_NAME = "Tasks.txt";
    private static JPanel inputPanel = new JPanel();

    public ToDoApp() {
        setTitle("To-Do List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLayout(new BorderLayout(10, 10));

        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setFont(new Font("Verdana", Font.PLAIN, 18));
        JScrollPane scrollPane = new JScrollPane(taskList);
        taskField = new JTextField();
        taskField.setFont(new Font("Verdana",Font.PLAIN, 18));
        taskField.setPreferredSize(new Dimension(100, 40));

        loadTasks();

        Font buttonFont= new Font("Verdana", Font.PLAIN, 14);
        addButton.setFont(buttonFont);
        removeButton.setFont(buttonFont);
        addButton.setPreferredSize(new Dimension(120, 40));
        removeButton.setPreferredSize(new Dimension(150, 40));

        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
        inputPanel.add(addButton);
        inputPanel.add(removeButton);

        add(taskField, BorderLayout.NORTH);
        add(new JScrollPane(taskList), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String task = taskField.getText().trim();
            if (!task.isEmpty()) {
                taskListModel.addElement(task);
                taskField.setText("");
                saveTasks();
            }
        });
        removeButton.addActionListener(e -> {
            int[] selectedIndices = taskList.getSelectedIndices();
            for (int i = selectedIndices.length - 1; i >= 0; i--) {
                taskListModel.remove(selectedIndices[i]);
            }
            saveTasks();
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void loadTasks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error creating tasks file: " + e.getMessage());
            }
        } 
        if (file.exists()) {
            try (Scanner scan=new Scanner(file)) {
                while (scan.hasNextLine()) {
                    taskListModel.addElement(scan.nextLine());
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading tasks: " + e.getMessage());
            }
            
        }
    }
    private void saveTasks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < taskListModel.getSize(); i++) {
                bw.write(taskListModel.getElementAt(i));
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving tasks: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoApp::new);
    }

}