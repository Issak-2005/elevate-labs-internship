package com.admission;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) throws Exception {
        // Ensure DB & tables exist
        new DBConnection();

        // Launch GUI directly
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AdmissionGUI(new Student());
            }
        });
    }
}
