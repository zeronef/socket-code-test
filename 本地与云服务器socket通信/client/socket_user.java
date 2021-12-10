package 网络编程.本地与云服务器socket通信.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class socket_user {
    Socket client;
    DataOutputStream out;
    DataInputStream in;
    BufferedReader stdin;
    String fromserve="";
    String fromuser="";
    public static void main(String[] args) {
        socket_user l=new socket_user();
        read_thread r=new read_thread(l);
        write_thread w=new write_thread(l);
        r.start();
        w.start();
    }
    public socket_user(){
        try {
            client=new Socket("47.96.88.6",12301);
            System.out.println("连接成功，开始对话");
            out =new DataOutputStream(client.getOutputStream());
            in =new DataInputStream(client.getInputStream());
            stdin=new BufferedReader(new InputStreamReader(System.in));
        } catch (Exception e) {
        }
    }
    public void close(){
        try{
            System.out.println("客户端:对话关闭");
            out.close();
            in.close();
            stdin.close();
            client.close();
        } catch (Exception e) {
        }
    }
}
class read_thread extends Thread{
    socket_user l;
    public read_thread(socket_user s){
        l=s;
    }
    public void run(){
        try {
            while(true){
                l.fromserve=l.in.readUTF();
                System.out.println(l.fromserve);
            }
        } catch (Exception e) {
        }
    }
}
class write_thread extends Thread{
    socket_user l;
    public write_thread(socket_user s){
        l=s;
    }
    public void run(){
        try {
            while(true){
                
                l.fromuser=l.stdin.readLine();
                l.out.writeUTF("user:"+l.fromuser);
                if(l.fromuser.equals("断开连接")){
                    l.close();
                    break;
                }
            }
        } catch (Exception e) {
        }
    }
}
