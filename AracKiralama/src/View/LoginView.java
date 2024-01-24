package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private javax.swing.JPanel container;
    private javax.swing.JPanel w_top;
    private javax.swing.JLabel lbl_welcome;
    private javax.swing.JLabel lbl_welcome2;
    private javax.swing.JPanel w_bottom;
    private javax.swing.JTextField fld_username;
    private javax.swing.JPasswordField fld_password;
    private javax.swing.JButton btn_login;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JLabel lbl_password;

    public LoginView() {
        this.add(container);
        this.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Rent a Car");
        this.setSize(400,400);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x,y);
        this.setVisible(true);
        btn_login.addActionListener(e -> {

        });
    }

}
/*
package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends Layout {
    private javax.swing.JPanel container;
    private javax.swing.JPanel w_top;
    private JLabel lbl_welcome;
    private javax.swing.JLabel lbl_welcome2;
    private JPanel w_bottom;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JButton btn_login;
    private JLabel lbl_username;
    private JLabel lbl_password;
    private final UserManager userManager;

    public LoginView() {
        userManager = new UserManager();
        this.add(container);
        this.guiInitialize(400, 400);
        btn_login.addActionListener(e -> {
            JTextField[] checkFieldList = {this.fld_username, this.fld_password};
            if (Helper.isFieldListEmpty(checkFieldList)) {
                Helper.showMessage("fill");
            } else {
                User loginUser = this.userManager.findByLogin(this.fld_username.getText(), this.fld_password.getText());
                if (loginUser == null) {
                    Helper.showMessage("notFound");
                } else {
                    AdminView adminView = new AdminView(loginUser);
                    dispose(); // login ekranının login işlemi sonrası kapanması için
                }
            }

        });
    }
}*/