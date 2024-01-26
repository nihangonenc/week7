import Business.UserManager;
import Core.Helper;
import Entity.User;
import View.AdminView;
import View.LoginView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    public static void main(String[] args) {
        Helper.setTheme();
        UserManager userManager = new UserManager();
        AdminView adminView = new AdminView(userManager.findByLogin("admin", "1234"));
       // LoginView loginView = new LoginView();
    }
}
