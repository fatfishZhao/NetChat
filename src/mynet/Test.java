package mynet;
//<applet code=Test width=100 height=100>
//</applet>
/**
 * Created by Administrator on 2017/1/13.
 */
import javax.swing.*;
import java.awt.*;

import fatfish.Console;
import fatfish.*;

public class Test extends JApplet{
    JButton
        b1 = new JButton("button1"),
        b2 = new JButton("button2");
    public void init(){
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());
        cp.add(b1);
        cp.add(b2);
    }
    public static void main(String args[])throws Exception{
        /*byte[] bs = new byte[] { (byte) 192, (byte) 168, 1, 1 };
        InetAddress routeradd = InetAddress.getByAddress(bs);
        InetAddress address=InetAddress.getByName("www.fatfish.space");
        System.out.println(address);
        System.out.print(routeradd.getHostName());*/
        Console.run(new Test(),300,250);
    }

}
