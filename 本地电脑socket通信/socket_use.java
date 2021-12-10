package 网络编程.本地电脑socket通信;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
class server extends Thread{
    public void run(){
        try{
            ServerSocket serverSocket=null;
            
            String str="1";
            serverSocket=new ServerSocket(5555);
            System.out.println(Thread.currentThread().getName()+":"+"服务器开始运行，等待客户端连接");
            Socket clientSocket=null;
            clientSocket=serverSocket.accept();
            System.out.println(Thread.currentThread().getName()+":"+"连接成功，开始接收数据");
            DataInputStream in =new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out =new DataOutputStream(clientSocket.getOutputStream());
            while(true){
                str=in.readUTF();
                if(str.equals("-1")){
                    break;
                }
                try {
                    str=String.valueOf(Integer.parseInt(str)*2);
                } catch (Exception e) {
                    str="无效数据";
                }
                
                out.writeUTF(str);
                
            }
            System.out.println(Thread.currentThread().getName()+":"+"服务端:数据传输关闭");
            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
class client extends Thread{
    public void run(){
        try {
            Socket client=null;
            String fromserve=null;
            String fromuser="1";
            client=new Socket("localhost",5555);
            DataOutputStream out =new DataOutputStream(client.getOutputStream());
            DataInputStream in =new DataInputStream(client.getInputStream());
            BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
            while(true){
                System.out.println(Thread.currentThread().getName()+":"+"输入一个数字:");
                fromuser=stdin.readLine();
                out.writeUTF(fromuser);
                if(fromuser.equals("-1")){
                    break;
                }
                System.out.println(Thread.currentThread().getName()+":"+"服务器返回:");
                fromserve=in.readUTF();
                System.out.println(fromserve);
            }
            System.out.println(Thread.currentThread().getName()+":"+"客户端:数据传输关闭");
            out.close();
            in.close();
            stdin.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
public class socket_use {
    public static void main(String[] args) {
        server s1=new server();
        s1.setPriority(10);
        s1.start();
        new client().start();
    }
}
