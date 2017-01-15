package mynet;

/**
 * Created by Administrator on 2017/1/13.
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class ClientUI extends Thread{
    Socket client;
    JTextArea clientHistory;
    JTextField clientInput;
    boolean pause = false;
    public ClientUI(){
    }
    public void run(){
        try {
            while(true) {
                DataInputStream in =
                        new DataInputStream(client.getInputStream());
                String tmpIn = in.readUTF();
                if (!pause)
                    clientHistory.append(tmpIn+"\n");
            }
        }catch (IOException e){
            e.printStackTrace();
            clientHistory.append("未建立连接，请重启");
        }
    }
    public static void main(String[] args) {
        ClientUI clientUI;
        clientUI = new ClientUI();
        JFrame clientFrame = new JFrame("Client");
        clientFrame.setSize(500, 300);
        clientFrame.setLocation(200,200);
        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(null);
        clientFrame.add(clientPanel);

        clientUI.clientHistory = new JTextArea("history\n");clientUI.clientHistory.setBounds(10,10,350,200);
        clientUI.clientInput = new JTextField("input",20);clientUI.clientInput.setLocation(10,220);clientUI.clientInput.setSize(350,30);
        //serverInput.setBounds(10,220,350,40);
        JButton clientStop = new JButton("暂停");clientStop.setBounds(360,10,90,30);
        clientStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==clientStop){
                        clientUI.pause =!clientUI.pause;
                        if (clientStop.getText().equals("暂停"))
                            clientStop.setText("继续");
                        else
                            clientStop.setText("暂停");

                }
            }
        });
        JButton clientConnect = new JButton("连接");clientConnect.setBounds(360,50,90,30);
        clientConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==clientConnect){
                    try {
                        clientUI.client = new Socket("localhost", 6066);
                            clientUI.start();
                            clientUI.clientHistory.append("连接成功\n");
                    }catch (IOException x){
                        x.printStackTrace();
                        clientUI.clientHistory.append("未建立新连接\n");
                    }
                }

            }
        });
        JButton clientSend = new JButton("发送");clientSend.setBounds(360,220,90,30);
        clientSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==clientSend&&!clientUI.pause) {
                    try {
                        OutputStream outToServer = clientUI.client.getOutputStream();
                        DataOutputStream out =
                                new DataOutputStream(outToServer);

                        out.writeUTF(clientUI.clientInput.getText());
                    } catch (IOException x) {
                        x.printStackTrace();
                    }
                }
            }
        });

        clientPanel.add(clientUI.clientHistory);clientPanel.add(clientUI.clientInput);clientPanel.add(clientStop);clientPanel.add(clientConnect);clientPanel.add(clientSend);
        clientFrame.setVisible(true);


    }
}
