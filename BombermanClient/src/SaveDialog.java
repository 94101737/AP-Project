import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveDialog extends JDialog{
    
    JPanel DialogPanel;
    GridLayout DialogLayout;
    JLabel DatabaseLabel;
    JTextField DatabaseTextField;
    JLabel usernameLabel;
    JTextField usernameTextField;
    JLabel passwordLabel;
    JPasswordField passwordTextField;
    JButton CancelButton;
    JButton OKButton;
    SaveDialog(JFrame MyFrame, String title) {
        super(MyFrame, title, true);
        this.setSize(GameFrame.MyFrame.getBounds().width / 5, GameFrame.MyFrame.getBounds().height / 3);
        this.setLocation(4 * GameFrame.MyFrame.getBounds().width / 10, 4 * GameFrame.MyFrame.getBounds().height / 10);
        DialogPanel = new JPanel();
        DialogPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        DialogLayout = new GridLayout(4, 2, 20, 20);
        DialogPanel.setLayout(DialogLayout);

        DatabaseLabel = new JLabel("Choose a Name : ");
        DatabaseLabel.setFont(GameFrame.f);
        DialogPanel.add(DatabaseLabel);

        DatabaseTextField = new JTextField();
        DatabaseTextField.setFont(GameFrame.f);
        DatabaseTextField.setBorder(new EmptyBorder(0,10,0,10));
        DialogPanel.add(DatabaseTextField);

        usernameLabel = new JLabel("Username of DB : ");
        usernameLabel.setFont(GameFrame.f);
        DialogPanel.add(usernameLabel);

        usernameTextField = new JTextField();
        usernameTextField.setFont(GameFrame.f);
        usernameTextField.setBorder(new EmptyBorder(0,10,0,10));
        DialogPanel.add(usernameTextField);

        passwordLabel = new JLabel("Password of DB : ");
        passwordLabel.setFont(GameFrame.f);
        DialogPanel.add(passwordLabel);

        passwordTextField = new JPasswordField();
        passwordTextField.setFont(GameFrame.f);
        passwordTextField.setBorder(new EmptyBorder(0,10,0,10));
        DialogPanel.add(passwordTextField);
        
        CancelButton = new JButton("Cancel");
        CancelButton.setFont(GameFrame.f);
        CancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveDialog.this.setVisible(false);
            }
        });
        CancelButton.setFont(GameFrame.f);
        DialogPanel.add(CancelButton);

        OKButton = new JButton("OK");
        OKButton.setFont(GameFrame.f);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((DatabaseTextField.getText()!="")&&(String.valueOf(passwordTextField.getPassword())!="")&&(usernameTextField.getText()!="")){
                    GameFrame.SaveGame(DatabaseTextField.getText(),usernameTextField.getText(),passwordTextField.getText());
                    SaveDialog.this.setVisible(false);
                }
            }
        });
        OKButton.setFont(GameFrame.f);
        DialogPanel.add(OKButton);
        this.add(DialogPanel);
    }
}
