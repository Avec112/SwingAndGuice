import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by avec on 14/03/2018.
 */
public class Application {

    private static Injector injector;

    @Inject
    private DoStuff doStuff;


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Guice.createInjector(new AbstractModule() {
                @Override
                protected void configure() {
                    bind(DoStuff.class).asEagerSingleton();
                }
            }).getInstance(Application.class).start();

            //Application app = new Application();
            //app.buildAndDisplayGui();
        });
        //new Application().start();
        /*injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(DoStuff.class).asEagerSingleton();
            }
        });*/

    }

    private void start() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // do heavy work here
                assert doStuff != null: "Jævla dritt i doInBackground";
                doStuff.showSplashScreen();
                return null;
            }

            @Override
            protected void done() {
                buildAndDisplayGui();
            }
        }.execute();
    }


    private void buildAndDisplayGui(){
        JFrame frame = new JFrame("Test Frame");
        buildContent(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    private void buildContent(JFrame aFrame){
        JPanel panel = new JPanel();
        panel.add(new JLabel("Hello"));
        JButton ok = new JButton("blocker");
        ok.addActionListener(new ShowDialog(aFrame));
        panel.add(ok);
        JButton worker = new JButton("Worker");
        worker.addActionListener(new ShowDialog(aFrame));
        panel.add(worker);
        aFrame.getContentPane().add(panel);
    }

    private final class ShowDialog implements ActionListener {
        /** Defining the dialog's owner JFrame is highly recommended. */
        ShowDialog(JFrame aFrame){
            fFrame = aFrame;
        }
        @Override public void actionPerformed(ActionEvent aEvent) {
            if(aEvent.getActionCommand().equals("blocker")) {
                computeBlocker();
                JOptionPane.showMessageDialog(fFrame, "This is a blocker dialog");
            } else if (aEvent.getActionCommand().equals("Worker")) {
                computeWorker();
                JOptionPane.showMessageDialog(fFrame, "This is a worker dialog");
            }

        }
        private JFrame fFrame;
    }

    private void computeBlocker() {

        try {
            System.out.println("Inside blocker");
            Thread.sleep(3000);
            System.out.println("Blocker finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void computeWorker() {
        new MyWorker().execute();
    }

    private class MyWorker extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            try {
                System.out.println("Inside worker");
                Thread.sleep(3000);
                System.out.println("Worker finished");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }



}
