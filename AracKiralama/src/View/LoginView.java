package View;
import Business.UserManager;
import Core.Helper;
import Entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends Layout {
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
    private final UserManager userManager;

    public LoginView() {
        this.userManager = new UserManager();

        this.add(container);
       this.guiInitilaze(400,400);
        btn_login.addActionListener(e -> {
            JTextField[] checkFieldList = {this.fld_username,this.fld_password};
            if (Helper.isFieldListEmpty(checkFieldList)){
                Helper.showMsg("fill");
            }else {
                User loginUser = this.userManager.findByLogin(this.fld_username.getText(), this.fld_password.getText());
                if( loginUser == null ){
                    Helper.showMsg("notFound");
                }else {
                    AdminView adminView = new AdminView(loginUser);
                    dispose(); // ilk pencere arkada gözükmez, kapatır
                }
            }
        });
    }

}