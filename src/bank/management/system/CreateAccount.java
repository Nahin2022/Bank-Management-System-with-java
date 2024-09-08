package bank.management.system;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccount extends JPanel {
    private JTextField usernameField, fatherNameField, motherNameField, presentAddressField, permanentAddressField,
            dobField, phoneNumberField, emailField, uniqueIdField;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JLabel usernameLabel, fatherNameLabel, motherNameLabel, presentAddressLabel, permanentAddressLabel,
            dobLabel, phoneNumberLabel, emailLabel, uniqueIdLabel, passwordLabel;
    private DatabaseHandler dbHandler;

    public CreateAccount(DatabaseHandler dbHandler) {
        initComponents();
        this.dbHandler = dbHandler;
    }

    private void initComponents() {
        usernameField = new JTextField(20);
        fatherNameField = new JTextField(20);
        motherNameField = new JTextField(20);
        presentAddressField = new JTextField(20);
        permanentAddressField = new JTextField(20);
        dobField = new JTextField(20);
        phoneNumberField = new JTextField(20);
        emailField = new JTextField(20);
        uniqueIdField = new JTextField(20);
        passwordField = new JPasswordField(20);

        usernameLabel = new JLabel("Username:");
        fatherNameLabel = new JLabel("Father's Name:");
        motherNameLabel = new JLabel("Mother's Name:");
        presentAddressLabel = new JLabel("Present Address:");
        permanentAddressLabel = new JLabel("Permanent Address:");
        dobLabel = new JLabel("Date of Birth:");
        phoneNumberLabel = new JLabel("Phone Number:");
        emailLabel = new JLabel("Email:");
        uniqueIdLabel = new JLabel("Unique ID:");
        passwordLabel = new JLabel("Password:");

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitButtonActionPerformed(e);
            }
        });

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(usernameLabel)
                        .addComponent(usernameField)
                        .addComponent(fatherNameLabel)
                        .addComponent(fatherNameField)
                        .addComponent(motherNameLabel)
                        .addComponent(motherNameField)
                        .addComponent(presentAddressLabel)
                        .addComponent(presentAddressField)
                        .addComponent(permanentAddressLabel)
                        .addComponent(permanentAddressField)
                        .addComponent(dobLabel)
                        .addComponent(dobField)
                        .addComponent(phoneNumberLabel)
                        .addComponent(phoneNumberField)
                        .addComponent(emailLabel)
                        .addComponent(emailField)
                        .addComponent(uniqueIdLabel)
                        .addComponent(uniqueIdField)
                        .addComponent(passwordLabel)
                        .addComponent(passwordField)
                        .addComponent(submitButton))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(usernameLabel)
                .addComponent(usernameField)
                .addComponent(fatherNameLabel)
                .addComponent(fatherNameField)
                .addComponent(motherNameLabel)
                .addComponent(motherNameField)
                .addComponent(presentAddressLabel)
                .addComponent(presentAddressField)
                .addComponent(permanentAddressLabel)
                .addComponent(permanentAddressField)
                .addComponent(dobLabel)
                .addComponent(dobField)
                .addComponent(phoneNumberLabel)
                .addComponent(phoneNumberField)
                .addComponent(emailLabel)
                .addComponent(emailField)
                .addComponent(uniqueIdLabel)
                .addComponent(uniqueIdField)
                .addComponent(passwordLabel)
                .addComponent(passwordField)
                .addComponent(submitButton)
        );

        clearFields();
    }

    private void clearFields() {
        usernameField.setText("");
        fatherNameField.setText("");
        motherNameField.setText("");
        presentAddressField.setText("");
        permanentAddressField.setText("");
        dobField.setText("");
        phoneNumberField.setText("");
        emailField.setText("");
        uniqueIdField.setText("");
        passwordField.setText("");
    }

    private void submitButtonActionPerformed(ActionEvent evt) {
        String username = usernameField.getText();
        String fatherName = fatherNameField.getText();
        String motherName = motherNameField.getText();
        String presentAddress = presentAddressField.getText();
        String permanentAddress = permanentAddressField.getText();
        String dob = dobField.getText();
        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();
        String uniqueId = uniqueIdField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if (username.isEmpty() || uniqueId.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
            return;
        }

        boolean success = dbHandler.insertUser(username, fatherName, motherName, presentAddress, permanentAddress, dob, phoneNumber, email, uniqueId, password);

        if (success) {
            JOptionPane.showMessageDialog(this, "Data inserted successfully!");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to insert data!");
        }
    }
}