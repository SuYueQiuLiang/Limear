package dsdssd.suyueqiulian.qingning.serversjava;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyClass {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {

            // 创建一个ServerSocket对象，并让这个Socket在1045端口监听
            serverSocket = new ServerSocket(1046);

            // 调用ServerSocket的accept()方法，接受客户端所发送的请求，
            // 如果客户端没有发送数据，那么该线程就停滞不继续
            System.out.println("open the ServerSocket");
            Socket socket = serverSocket.accept();
            // 从Socket当中得到InputStream对象

            System.out.println("connected to the phone,try exchange datas soon.");
            datawork(socket);
            //关闭serverSocket
            serverSocket.close();
            main(null);
        } catch (IOException e) {
            e.printStackTrace();

        }

    }


    public static void datawork(Socket socket){
        try{
            InputStream is = socket.getInputStream();

            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            //通过输入流读取器对象 接客户端发送过来的数据
            String ui=br.readLine();
            System.out.println("Get message form phone:"+ui);
            if(ui.equals("gettitle")){
                System.out.println("sending the title to phone");
                OutputStream ou = socket.getOutputStream();
                ou.write((readtext("C:/limear/title.txt")+"\n").getBytes("utf-8"));
                ou.flush();
            }
            else if(ui.equals("logging")){
                System.out.println("an user tried logging");
                OutputStream ou = socket.getOutputStream();
                String username = null;
                String userpassword;
                Boolean equal=false;
                //分别读取BufferedReader第二三行中的用户名以及密码
                username=br.readLine();
                userpassword=br.readLine();

                if (userpassword.equals(readtext("C:/limear/user/"+username+".txt")+"\n")) {
                    ou.write("true".getBytes("utf-8"));
                }
                else
                    ou.write("false".getBytes("utf-8"));
                ou.flush();
            }
            else if(ui.equals("register")){
                System.out.println("an user tried register");
                OutputStream ou = socket.getOutputStream();
                String username = null;
                String userpassword;
                Boolean equal=false;
                username=br.readLine();
                userpassword=br.readLine();
                File dir = new File("C:/limear/user/"+username+".txt");
                if(dir.exists()){
                    ou.write("false".getBytes("utf-8"));
                    ou.flush();
                }
                else{
                    writetext("C:/limear/user/"+username+".txt",userpassword);
                    ou.write("true".getBytes("utf-8"));
                    ou.flush();
                }
            }
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }



    //文件读写内部类
    public static String readtext(String road) {
        String charset = "utf-8";
        File file = new File(road);
        long fileByteLength = file.length();
        byte[] content = new byte[(int) fileByteLength];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String str = null;
        try {
            str = new String(content, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }finally {
            return str;
        }
    }

    public static void writetext(String road,String str){
        try{
            File file=new File(road);
            FileWriter fw=new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(str);
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}



