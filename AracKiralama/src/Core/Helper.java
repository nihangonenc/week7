package Core;
import javax.swing.*;

public class Helper {
    public static void setTheme(){

        for (UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()){
            if("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            }
        }

    }


}
