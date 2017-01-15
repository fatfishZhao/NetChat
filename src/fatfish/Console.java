package fatfish;


/**
 * Created by Administrator on 2017/1/14.
 */
import javax.swing.*;
import java.awt.event.*;
public class Console {
    public static String title(Object o){
        String t = o.getClass().toString();
        if (t.indexOf("class")!=-1)
            t = t.substring(6);
        return t;
    }
    public static void setupClosing(JFrame frame){
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    public static void run(JFrame frame, int width,int height){
        setupClosing(frame);
        frame.setSize(width,height);
        frame.setVisible(true);
    }
    public static void run(JApplet jApplet, int width, int height){
        JFrame frame = new JFrame(title(jApplet));
        setupClosing(frame);
        frame.getContentPane().add(jApplet);
        frame.setSize(width, height);
        jApplet.init();
        jApplet.start();

        frame.setVisible(true);
    }
    public static void run(JPanel jPanel, int width, int height){
        JFrame frame = new JFrame(title(jPanel));
        setupClosing(frame);
        frame.getContentPane();
        frame.setSize(width, height);
        frame.setVisible(true);
    }

}
