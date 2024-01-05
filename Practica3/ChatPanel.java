import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JPanel {
    private final JTextArea chatText;
    private final JButton enviar;
    private final JButton desconectar;
    private final JTextField messageField;
    private final DefaultListModel usersListModel;
    private final JList<String> users;
    private final GridBagConstraints c;

    public ChatPanel(String connectionString) {
        super(new GridBagLayout());
        c = new GridBagConstraints();

        setBackground(Color.DARK_GRAY);

        desconectar = new JButton("Desconectar");
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        c.ipady = 10;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(10, 10, 0, 10);
        add(desconectar, c);

        users = new JList<>(usersListModel = new DefaultListModel<>());
        c.gridy = 1;
        c.gridx = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(10, 10, 10, 0);
        users.setLayoutOrientation(JList.VERTICAL);
        users.setBackground(Color.LIGHT_GRAY);
        add(new JScrollPane(users), c);

        chatText = new JTextArea(connectionString);
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(10, 10, 10, 10);
        chatText.setBackground(Color.WHITE);
        chatText.setEditable(false);
        chatText.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(chatText);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); 
        add(scrollPane, c);

        messageField = new JTextField();
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(0, 10, 10, 10);
        messageField.setBackground(Color.WHITE);
        add(messageField, c);

        enviar = new JButton("Enviar");
        c.gridx = 2;
        c.gridy = 2;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 10, 10, 10);
        add(enviar, c);
    }

    public JButton getSendButton(){ 
      return enviar;
    }
  
    public JButton getDisconnectButton(){ 
      return desconectar;
    }    
  
    public JTextArea getChatText(){ 
      return chatText; 
    }
  
    public JTextField getMessageField(){
      return messageField;
    }
  
    public DefaultListModel getUsersList(){
      return usersListModel;
    }
}
