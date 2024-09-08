package bank.management.system;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class BankManagementSystem {

    public static void main(String[] args) {
        
        URL url = BankManagementSystem.class.getResource("ddot.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        
        JFrame frame = new JFrame("Bank Management System - LoginTo");
        LoginTo loginPanel = new LoginTo();
        frame.setIconImage(img);
        // Add the loginPanel to the frame
        frame.getContentPane().add(loginPanel);

        frame.setSize(693, 436);
        // Center the frame on the screen
        frame.setLocationRelativeTo(null);
        // Set default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Make it open
        frame.setVisible(true);
    }
}
