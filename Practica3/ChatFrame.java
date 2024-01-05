import java.awt.*;
import javax.swing.*;

public class ChatFrame extends JFrame {
  private JButton sendButton;
  private JTextField textField;
  private JPanel header;
  private JTextArea convers;
  private JPanel text;
  private JLabel title;

  private final CardLayout contentCardLayout;
  private final JPanel contentPanel;
  private ChatPanel chatPanel;
  private LoginPanel loginPanel;

  public ChatFrame(){
    contentCardLayout = new CardLayout();
    contentPanel = new JPanel(contentCardLayout);
    add(contentPanel);
    setupLoginPanel();
    setVisible(true);
  }

  public void setupLoginPanel(){
    loginPanel = new LoginPanel();
    contentPanel.add(loginPanel, "1");
    contentCardLayout.show(contentPanel, "1");

    setTitle("Swing Chat");
    contentPanel.validate();
    contentPanel.repaint();
    setResizable(false);
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    dim = new Dimension(400,200);
    setSize(dim);
    setLocationRelativeTo(null);
    contentPanel.setPreferredSize(dim);
    loginPanel.setPreferredSize(dim);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public void setupChatPanel(String nickname){
    chatPanel = new ChatPanel("Bienvenido "+/* nickname +*/"!\n");
    contentPanel.add(chatPanel, "3");
    contentCardLayout.show(contentPanel, "3");

    setTitle("Chat SAD");
    contentPanel.validate();
    contentPanel.repaint();
    setResizable(true);
    Dimension dim = new Dimension(700,700);
    setSize(dim);
    setLocationRelativeTo(null);
    contentPanel.setPreferredSize(dim);
    chatPanel.setPreferredSize(dim);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
  }

  public ChatPanel getChatPanel() {
    return chatPanel;
  }
  
  public LoginPanel getLoginPanel() {
    return loginPanel;
  }
}
