import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class DeliveryPackage extends JFrame {
    private JLabel label;
    private JLabel senderLabel;
    private JLabel recipientLabel;
    private JLabel addfeeLabel;
    private JLabel costLabel;
    private JTextField weightField;
    private JTextField addfeeField;
    private JTextField costField;
    private JTextField senderField;
    private JTextField recipientField;
    private JRadioButton standardRadio;
    private JRadioButton twoDayRadio;
    private JRadioButton overnightRadio;
    private JButton calculateButton;
    private ButtonGroup packageGroup;
    private JLabel platformFeeLabel;
    private JTextField platformFeeField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DeliveryPackage packagesCalculator = new DeliveryPackage();
            packagesCalculator.setVisible(true);
        });
    }

    public DeliveryPackage() {
        // Set the frame properties
        setTitle("Package Delivery Calculator");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

		// Customize font and color for labels
		    Font labelFont = new Font("Arial", Font.BOLD, 16);  // Adjust the font size based on your preference
    		Color labelColor = Color.BLUE;

        // Create components
        senderLabel = new JLabel("    Sender:");
        senderLabel.setForeground(Color.BLUE); // Text color
        recipientLabel = new JLabel("Recipient:");
        recipientLabel.setForeground(Color.BLUE); // Text color
        label = new JLabel("Weight (in pounds):");
        label.setForeground(Color.BLUE); // Text color
        costLabel = new JLabel("Cost:");
        costLabel.setForeground(Color.BLUE); // Text color
        addfeeLabel = new JLabel("Additional Fee:");
        addfeeLabel.setForeground(Color.BLUE); // Text color
        platformFeeLabel = new JLabel("Platform Fee:");
        platformFeeLabel.setForeground(Color.BLUE); // Text color
        weightField = new JTextField(10);
        addfeeField = new JTextField(10);
        platformFeeField = new JTextField(10);
        costField = new JTextField(10);
        senderField = new JTextField(10);
        recipientField = new JTextField(10);
        standardRadio = new JRadioButton("Standard Package");
        standardRadio.setForeground(Color.YELLOW); // Text color
        twoDayRadio = new JRadioButton("Two-Day Package");
        twoDayRadio.setForeground(Color.RED); // Text color
        overnightRadio = new JRadioButton("Overnight Package");
       	overnightRadio.setForeground(Color.GREEN); // Text color
        calculateButton = new JButton("Calculate");
        calculateButton.setBackground(Color.GREEN); // Button background color
		calculateButton.setForeground(Color.WHITE); // Button text color


        senderLabel.setFont(new Font("Arial", Font.BOLD, 12));
        recipientLabel.setFont(new Font("Arial", Font.BOLD, 12));
        label.setFont(new Font("Arial", Font.BOLD, 12));
        costLabel.setFont(new Font("Arial", Font.BOLD, 12));
        addfeeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        platformFeeLabel.setFont(new Font("Arial", Font.BOLD, 12));

        // Group the radio buttons
        packageGroup = new ButtonGroup();
        packageGroup.add(standardRadio);
        packageGroup.add(twoDayRadio);
        packageGroup.add(overnightRadio);

        // Set layout manager
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add components to the frame
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(weightField);
        costLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(costLabel);
        add(costField);
        platformFeeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(platformFeeLabel);
        add(platformFeeField);
        addfeeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(addfeeLabel);
        add(addfeeField);

        add(Box.createRigidArea(new Dimension(0, 10)));

        // Package type radio buttons
        JPanel packagesPanel = new JPanel();
        packagesPanel.add(standardRadio);
        packagesPanel.add(twoDayRadio);
        packagesPanel.add(overnightRadio);
        add(packagesPanel);

        add(Box.createRigidArea(new Dimension(0, 10)));

        // Sender information
        JPanel senderPanel = new JPanel();
        senderPanel.add(senderLabel);
        senderPanel.add(senderField);
        add(senderPanel);

        // Recipient information
        JPanel recipientPanel = new JPanel();
        recipientPanel.add(recipientLabel);
        recipientPanel.add(recipientField);
        add(recipientPanel);

		calculateButton.setFont(new Font("Arial", Font.BOLD, 14));  // Adjust the font size based on your preference
    	calculateButton.setBackground(Color.GREEN);  // Adjust the background color based on your preference
    	calculateButton.setForeground(Color.WHITE);  // Adjust the text color based on your preference
        add(calculateButton);

        // Add action listener to the button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateCost();
            }
        });

        // Add item listener to radio buttons
        standardRadio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Show/hide addfeeField and platformFeeField when Standard Package is selected
                boolean isSelected = standardRadio.isSelected();
                addfeeLabel.setVisible(!isSelected);
                addfeeField.setVisible(!isSelected);
                platformFeeLabel.setVisible(!isSelected);
                platformFeeField.setVisible(!isSelected);
            }
        });

        twoDayRadio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Show/hide addfeeField and platformFeeField when Two-Day Package is selected
                addfeeLabel.setVisible(!twoDayRadio.isSelected());
                addfeeField.setVisible(!twoDayRadio.isSelected());
                platformFeeLabel.setVisible(twoDayRadio.isSelected());
                platformFeeField.setVisible(twoDayRadio.isSelected());
            }
        });

        overnightRadio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Show/hide addfeeField and platformFeeField when Overnight Package is selected
                addfeeField.setVisible(overnightRadio.isSelected());
                platformFeeLabel.setVisible(!overnightRadio.isSelected());
                platformFeeField.setVisible(!overnightRadio.isSelected());
            }
        });

        platformFeeLabel.setVisible(false);
        platformFeeField.setVisible(false);
    }

    private void calculateCost() {
        try {
            // Get the weight from the text field
            double weight = Double.parseDouble(weightField.getText());
            double cost = Double.parseDouble(costField.getText());

            // Check if weight and cost are non-negative
            if (weight < 0 || cost < 0) {
                JOptionPane.showMessageDialog(this, "Weight and cost must be non-negative.", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            // Get sender and recipient information
            String sender = senderField.getText();
            String recipient = recipientField.getText();

            // Define the rate per pound based on the selected package type
            double CC;
            if (standardRadio.isSelected()) {
                CC = weight * cost;
            } else if (twoDayRadio.isSelected()) {
                // Parse addfeeField only when necessary
                double platformfee = platformFeeField.isVisible() ? Double.parseDouble(platformFeeField.getText()) : 0.0;
                CC = weight * cost + platformfee;
            } else if (overnightRadio.isSelected()) {
                // Parse addfeeField only when necessary
                double weightMultiplier = addfeeField.isVisible() ? Double.parseDouble(addfeeField.getText()) : 0.0;
                CC = weight * cost + weightMultiplier * weight;
            } else {
                JOptionPane.showMessageDialog(this, "Please select a package type.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JPanel resultPanel = new JPanel();
            resultPanel.setLayout(new GridLayout(5, 1));
            resultPanel.setBackground(Color.WHITE);  // Set background color
			resultPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));  // Add border

			 // Customize the font and color of labels in the result panel
			Font resultFont = new Font("Arial", Font.PLAIN, 14);  // Adjust font size based on your preference
        	Color resultColor = Color.RED;  // Adjust color based on your preference

        	 GridBagConstraints gbc = new GridBagConstraints();
			        gbc.gridx = 0;
			        gbc.gridy = 0;
        	gbc.anchor = GridBagConstraints.WEST;

        	  // Display Sender
			        resultPanel.add(createLabel("<html><b>Sender:</b> " + sender + "</html>", resultFont, resultColor), gbc);

			        // Display Recipient
			        gbc.gridy++;
			        resultPanel.add(createLabel("<html><b>Recipient:</b> " + recipient + "</html>", resultFont, resultColor), gbc);

			        // Display Package Type
			        gbc.gridy++;
			        resultPanel.add(createLabel("<html><b>Package Type:</b> " + getSelectedPackageType() + "</html>", resultFont, resultColor), gbc);

			        // Display Package Weight
			        gbc.gridy++;
			        resultPanel.add(createLabel("<html><b>Package Weight:</b> " + weight + " pounds</html>", resultFont, resultColor), gbc);

			        // Display Package Cost
			        gbc.gridy++;
			        resultPanel.add(createLabel("<html><b>Package Cost:</b> $" + cost + "</html>", resultFont, resultColor), gbc);

			        // Display Total Cost
			        gbc.gridy++;
			        resultPanel.add(createLabel("<html><b>Total Cost:</b> $" + CC + "</html>", resultFont, resultColor), gbc);

			        // Show the custom dialog
			        int result = JOptionPane.showConfirmDialog(this, resultPanel, "Package Information", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			        // Show the cost if the user clicks OK
			        if (result == JOptionPane.OK_OPTION) {
			            JOptionPane.showMessageDialog(this, "<html><b>Cost:</b> $" + CC + "</html>", "Cost Information", JOptionPane.INFORMATION_MESSAGE);
        }

           // resultPanel.add(new JLabel("<html><b>Sender:</b> " + sender + "</html>"));
           // resultPanel.add(new JLabel("<html><b>Recipient:</b> " + recipient + "</html>"));
           // resultPanel.add(new JLabel("<html><b>Package Type:</b> " + getSelectedPackageType() + "</html>"));
           // resultPanel.add(new JLabel("<html><b>Package Weight:</b> " + weight + " pounds</html>"));
           // resultPanel.add(new JLabel("<html><b>Package Cost:</b> " + cost + " </html>"));
           // resultPanel.add(new JLabel("<html><b>Total Cost:</b> $" + CC + "</html>"));

            // Show the custom dialog
           // JOptionPane.showMessageDialog(this, resultPanel, "Package Information", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            // Handle invalid input
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers for package weight and cost.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
private JLabel createLabel(String text, Font font, Color color) {
    JLabel label = new JLabel(text);
    label.setFont(font);
    label.setForeground(color);
    return label;
}
    private void resetState() {
        // Reset text fields
        weightField.setText("");
        addfeeField.setText("");
        costField.setText("");
        senderField.setText("");
        recipientField.setText("");

        // Reset radio buttons
        packageGroup.clearSelection();

        // Reset visibility of addfeeLabel and addfeeField
        addfeeLabel.setVisible(false);
        addfeeField.setVisible(false);
    }

    private String getSelectedPackageType() {
        if (standardRadio.isSelected()) {
            return "Standard Package";
        } else if (twoDayRadio.isSelected()) {
            return "Two-Day Package";
        } else {
            return "Overnight Package";
        }
    }
}