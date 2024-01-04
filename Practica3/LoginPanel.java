import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private JLabel nickLabel;
    private JLabel info;
    private JTextField nickField;
    private JButton entrar;
    private GridBagConstraints c;

    public LoginPanel() {
        super(new GridBagLayout());
        setupGUI();
    }

    public void setupGUI() {
        c = new GridBagConstraints();

        info = new JLabel("Benvingut, Introdueixi un nom d'usuari:");
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.SOUTHWEST;
        c.insets = new Insets(40, 0, 0, 40);
        c.gridx = 1;
        c.gridy = 0;
        add(info,c);

        nickLabel = new JLabel("Nom:");
        nickLabel.setFont(new Font("", Font.PLAIN, 15));
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.EAST;
        c.insets = new Insets(0, 40, 0, 0);
        c.gridx = 0;
        c.gridy = 1;
        add(nickLabel,c);
        
        nickField = new JTextField();
        c.weightx = 1.0;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(5, 0, 0, 40);
        c.gridx = 1;
        c.gridy = 1;
        add(nickField,c);

        entrar = new JButton("Entrar");
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.SOUTHEAST;
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(15, 0, 40, 40);
        add(entrar,c);
    }

    public JTextField getNicknameField(){
        return nickField;
    }

    public JButton getJoinButton(){
        return entrar;
    }
}
