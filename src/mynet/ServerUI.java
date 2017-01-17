package mynet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/13.
 */
public class ServerUI extends Thread{
    ServerSocket serverSocket;
    JFrame serverFrame;
    JPanel serverPanel;
    JTextArea serverHistory;
    JTextField serverInput;
    Socket server;
    ArrayList<Socket> serverList = new ArrayList<>();
    boolean pause = false;
    public ServerUI(){
    }
    public void init(JFrame frame){
        serverFrame = frame;
    }
    public void run(){
        try{
            while(true) {
                server = serverSocket.accept();
                serverList.add(server);
                (new serverThread(this)).start();
            }
        }catch (IOException e){
            e.printStackTrace();
            serverHistory.append("未建立连接，请重启");
        }
    }
    public static void main(String[] args) {
        ServerUI serverUI = new ServerUI();
        try {
            serverUI.serverSocket = new ServerSocket(6066);
        }catch (IOException e){
            e.printStackTrace();
        }
        serverUI.serverFrame = new JFrame("Server");
        serverUI.serverFrame.setSize(500, 300);
        serverUI.serverFrame.setLocation(800,200);
        serverUI.serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverUI.serverPanel = new JPanel();
        serverUI.serverPanel.setLayout(null);

        serverUI.serverHistory = new JTextArea("history\n");serverUI.serverHistory.setBounds(10,10,350,200);
        serverUI.serverInput = new JTextField("input",20);serverUI.serverInput.setLocation(10,220);serverUI.serverInput.setSize(350,30);
        JButton serverStop = new JButton("暂停");serverStop.setBounds(360,10,90,30);
        serverStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==serverStop){
                        serverUI.pause = !serverUI.pause;
                        if (serverStop.getText().equals("暂停"))
                            serverStop.setText("继续");
                        else
                            serverStop.setText("暂停");
                }
            }
        });
        JButton serverSend = new JButton("发送");serverSend.setBounds(360,220,90,30);
        serverSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==serverSend&&!serverUI.pause) {
                    try {
                        for (Socket tmpServer : serverUI.serverList) {
                            OutputStream outToServer = tmpServer.getOutputStream();
                            DataOutputStream out =
                                    new DataOutputStream(outToServer);
                            out.writeUTF(serverUI.serverInput.getText());
                        }
                    } catch (IOException x) {
                        x.printStackTrace();
                    }
                }
            }
        });


        serverUI.serverPanel.add(serverUI.serverHistory);serverUI.serverPanel.add(serverUI.serverInput);serverUI.serverPanel.add(serverStop);
        serverUI.serverPanel.add(serverSend);
        serverUI.serverFrame.add(serverUI.serverPanel);
        serverUI.serverFrame.setVisible(true);
        serverUI.start();
    }
}
class serverThread extends Thread{
    ServerUI serverUI;
    Socket server;
    public serverThread(ServerUI serverUI){
        this.serverUI = serverUI;
        server = serverUI.server;
    }
    public void run(){
        try{
            serverUI.serverHistory.append("与"+server.getRemoteSocketAddress()+"连接已建立\n");
            while(true){
                DataInputStream in =
                        new DataInputStream(server.getInputStream());
                String tmpIn = in.readUTF();
                if (!serverUI.pause)
                    serverUI.serverHistory.append(tmpIn+" from "+server.getRemoteSocketAddress()+"\n");
            }
        }catch (IOException e){
            e.printStackTrace();
            serverUI.serverHistory.append("lost"+server.getRemoteSocketAddress()+"\n");
            serverUI.serverList.remove(server);

        }
    }
}
