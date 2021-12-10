package 网络编程.本地与云服务器socket通信.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class socket_server {
    public static void main(String[] args) {
        new socket_server().run();
    }
    public void run(){
        try{
            ServerSocket serverSocket=null;
            
            String str="";
            serverSocket=new ServerSocket(12301);
            System.out.println("服务器开始运行，等待客户端连接");
            Socket clientSocket=null;
            clientSocket=serverSocket.accept();
            System.out.println("连接成功，开始对话");
            DataInputStream in =new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out =new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
            while(true){
                System.out.print("客户端:");
                str=in.readUTF();
                System.out.println(str);
                if(str.equals("结束对话")){
                    break;
                }
                System.out.print("输入:");
                str=sin.readLine();
                out.writeUTF(str);
                
            }
            System.out.println("服务端:数据传输关闭");
            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
