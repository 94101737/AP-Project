import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoadDialog extends JDialog {
    JPanel DialogPanel;
    GridLayout DialogLayout;
    JLabel DatabaseLabel;
    JComboBox<String> DatabaseComboBox;
    JLabel usernameLabel;
    JTextField usernameTextField;
    JLabel passwordLabel;
    JPasswordField passwordTextField;
    JLabel FetchLabel;
    JButton FetchButton;
    JButton CancelButton;
    JButton OKButton;

    LoadDialog(JFrame MyFrame, String title) {
        super(MyFrame, title, true);
        this.setSize(GameFrame.MyFrame.getBounds().width / 5, GameFrame.MyFrame.getBounds().height / 3);
        this.setLocation(4 * GameFrame.MyFrame.getBounds().width / 10, 4 * GameFrame.MyFrame.getBounds().height / 10);
        DialogPanel = new JPanel();
        DialogPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        DialogLayout = new GridLayout(5, 2, 20, 20);
        DialogPanel.setLayout(DialogLayout);

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

        FetchLabel = new JLabel();
        DialogPanel.add(FetchLabel);

        FetchButton = new JButton("Fetch Names");
        FetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((String.valueOf(passwordTextField.getPassword())!="")&&(usernameTextField.getText()!="")){
                    GameFrame.UpdateGameNames(GameFrame.GameNames,usernameTextField.getText(),String.valueOf(passwordTextField.getPassword()));
                    DatabaseComboBox.removeAllItems();
                    for (int i = 0; i < GameFrame.GameNames.size(); i++) {
                        DatabaseComboBox.addItem(GameFrame.GameNames.get(i));
                    }
                }
            }
        });
        FetchButton.setFont(GameFrame.f);
        DialogPanel.add(FetchButton);

        DatabaseLabel = new JLabel("Choose a Name : ");
        DatabaseLabel.setFont(GameFrame.f);
        DialogPanel.add(DatabaseLabel);

        DatabaseComboBox = new JComboBox<>();
        DatabaseComboBox.setFont(GameFrame.f);
        for (int i = 0; i < GameFrame.GameNames.size(); i++) {
            DatabaseComboBox.addItem(GameFrame.GameNames.get(i));
        }
        DatabaseComboBox.setSelectedItem(null);
        DialogPanel.add(DatabaseComboBox);

        CancelButton = new JButton("Cancel");
        CancelButton.setFont(GameFrame.f);
        CancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadDialog.this.setVisible(false);
            }
        });
        CancelButton.setFont(GameFrame.f);
        DialogPanel.add(CancelButton);

        OKButton = new JButton("OK");
        OKButton.setFont(GameFrame.f);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((DatabaseComboBox.getSelectedItem()!=null)&&(String.valueOf(passwordTextField.getPassword())!="")&&(usernameTextField.getText()!="")){
                    GameFrame.LoadGame((String)DatabaseComboBox.getSelectedItem(),usernameTextField.getText(),passwordTextField.getText());
                    LoadDialog.this.setVisible(false);
                }
            }
        });
        OKButton.setFont(GameFrame.f);
        DialogPanel.add(OKButton);
        this.add(DialogPanel);
    }
}
