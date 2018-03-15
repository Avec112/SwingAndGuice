import javax.swing.*;
import java.awt.*;

/**
 * Created by avec on 15/03/2018.
 */
public class DoStuff {

    private JFrame frame;

    public DoStuff() {
        System.out.println("Creating DoStuff");
    }

    public void showSplashScreen() {
        JFrame frame = new JFrame("DoStuff");
        JLabel label = new JLabel("DoStuff...");
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(label);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 200);
        frame.setVisible(true);
        try {
            System.out.println("Inside DoStuff");
            Thread.sleep(3000);
            System.out.println("DoStuff finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frame.setVisible(false);
        frame.dispose();
    }
}
