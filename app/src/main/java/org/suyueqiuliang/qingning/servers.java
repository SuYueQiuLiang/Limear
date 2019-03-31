package org.suyueqiuliang.qingning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

//服务器处理外部类

public class servers {
    String message;
    public String getData(final String ip, final String data)  {
        new Thread(new Runnable(){
            public void run() {
                try {
                    Socket socket;
                    // 创建Socket对象 & 指定服务端的IP及端口号
                    System.out.println("Services began...");
                    socket = new Socket(ip, 1046);
                    OutputStream ou = socket.getOutputStream();
                    ou.write((data+"\n").getBytes("utf-8"));
                    ou.flush();
                    System.out.println("connected to services,try to get message...");
                    InputStream is = socket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String input = "";
                    String line="checkdata";
                    do{
                        line = br.readLine();
                        if(line!=null) {
                            input = input + line + "\n";
                        }
                        else {
                            //System.out.println(input);
                            message=input;
                        }
                    }
                    while (line!= null);
                    socket.close();
                    ou.close();
                    is.close();
                    isr.close();
                    br.close();

                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //在title获取到值之前阻塞进程，否则会因为新进程连接服务器导致延迟致使返回null
        //设置一个时间器定时结束while
        Timer timer=new Timer();
        timer.schedule(new TimerTask(){
            public void run(){
                message="Failed";
            }},2000);//2000毫秒后，终止阻塞，返回failed
        while (message==null){

        }
        return message;
    }
}
