import java.awt.*;
import javax.swing.*;

public class Chat extends JFrame implements ActionListener {
    private JButton sendButton;
    private JTextField textField;
    private DefaultListModel<String> conversationModel;
    private JList<String> conversationList;

    public Chat() {
        setupUI();
    }

    private void setupUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setTitle("Chat");

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(405, 50));

        JLabel title = new JLabel("Chat");

        JPanel header = new JPanel();
        header.setPreferredSize(new Dimension(500, 50));
        header.setLayout(new BorderLayout());
        header.add(title, BorderLayout.CENTER);

        conversationModel = new DefaultListModel<>();
        conversationList = new JList<>(conversationModel);
        JScrollPane scrollPane = new JScrollPane(conversationList);

        JPanel text = new JPanel();
        text.setPreferredSize(new Dimension(500, 50));
        text.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

        sendButton = new JButton("Enviar");
        sendButton.addActionListener(this);
        sendButton.setPreferredSize(new Dimension(80, 50));

        text.add(textField);
        text.add(sendButton);

        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(text, BorderLayout.SOUTH);
      
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            String message = textField.getText();
            if (!message.isEmpty()) {
                appendMessage("Yo: " + message);
            }
            textField.setText("");
        }
    }

    private void appendMessage(String message) {
        conversationModel.addElement(message);
    }

}
