package 网络编程.本地与云服务器socket通信.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class socket_server_2 {
    DataOutputStream out;
    DataInputStream in;
    BufferedReader stdin;
    String fromserve="";
    String fromuser="";
    ServerSocket serverSocket; 
    Socket clientSocket;

    public static void main(String[] args) {
        socket_server_2 l=new socket_server_2();
        read_thread r=new read_thread(l);
        write_thread w=new write_thread(l);
        r.start();
        w.start();
        
    }
    public socket_server_2(){
        try {
            serverSocket=new ServerSocket(12301);
            System.out.println("服务端开始运行，等待客户端连接");
            clientSocket=serverSocket.accept();
            System.out.println("连接成功，开始对话");
            in =new DataInputStream(clientSocket.getInputStream());
            out =new DataOutputStream(clientSocket.getOutputStream());
            stdin=new BufferedReader(new InputStreamReader(System.in));
        } catch (Exception e) {
        }
    }
    public void close(){
        try{
            System.out.println("服务端:对话关闭");
            out.close();
            in.close();
            notifyAll();
            stdin.close();
            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
        }
    }
}
class read_thread extends Thread{
    socket_server_2 l;
    public read_thread(socket_server_2 s){
        l=s;
    }
    public void run(){
        try {
            while(true){
                l.fromserve=l.in.readUTF();
                System.out.println(l.fromserve);
                if(l.fromserve.equals("user:断开连接")){
                    l.close();
                    break;
                }
            }
        } catch (Exception e) {
        }
    }
}
class write_thread extends Thread{
    socket_server_2 l;
    public write_thread(socket_server_2 s){
        l=s;
    }
    public void run(){
        try {
            while(true){
                l.fromuser=l.stdin.readLine();
                l.out.writeUTF("server:"+l.fromuser);
            }
        } catch (Exception e) {
        }
    }
}
