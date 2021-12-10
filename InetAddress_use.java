package 网络编程;


import java.net.InetAddress;
public class InetAddress_use {
    public static void main(String[] args) {
        try {
            InetAddress localip=InetAddress.getLocalHost();
            System.out.println(localip);
            InetAddress ip2=InetAddress.getByName("www.baidu.com");
            System.out.println(ip2);
        } catch (Exception e) {
        }
        ;
    }
}
