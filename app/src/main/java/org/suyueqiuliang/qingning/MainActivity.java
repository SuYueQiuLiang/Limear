package org.suyueqiuliang.qingning;


import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    mainfragment mainfragment;
    settingfragment settingfragment;
    historyfragment historyfragment;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    int WindowsWidth;
    int WindowsHeight;
    //务必在读取完message后重置message至null
    Boolean opened=false;
    String message;
    Boolean logged=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("2019年2月");
        toolbar.setTitleTextColor(Color.parseColor("#1f4921"));
        setSupportActionBar(toolbar);


        //添加并初始化fragment为mainfragment
        mainfragment = new mainfragment();
        settingfragment = new settingfragment();
        historyfragment = new historyfragment();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.right_layout, mainfragment);
        transaction.add(R.id.right_layout, settingfragment);
        transaction.add(R.id.right_layout, historyfragment);
        transaction.commit();
        changefragment(mainfragment);

        //初始化mainfragment内控件并对控件进行屏幕比例自适配
        //获取屏幕大小
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        WindowsWidth = outMetrics.widthPixels;
        WindowsHeight = outMetrics.heightPixels;


        //从服务器获取公告
        new Thread(new Runnable() {
            public void run() {
                getdata(toolbar, "gettitle");
            }}).start();

        //读取用户数据，如果不为空则尝试登陆
        final String[] user=readuserdata();
        if(!user[0].equals("")) {
            new Thread(new Runnable() {
                public void run() {
                    if(getdata(toolbar, "logging\n"+user[0]+"\n"+user[1]).equals("true")){
                        logged=true;
                    }
                    else
                        logged=false;
                }
            }).start();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle

                = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void onStart() {
        super.onStart();

        //FrameLayout f1=(FrameLayout)findViewById(R.id.framelayout1);
        //LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) f1.getLayoutParams();
        // 取控件aaa当前的布局参数
        //linearParams.height = (int)(WindowsHeight*0.22);
        //f1.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new Thread(new Runnable() {
                    public void run() {
                        getdata(view, "gettitle");
                    }}).start();
            }
        });

    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        FrameLayout t2=(FrameLayout) findViewById(R.id.fram1);
        TextView t3=(TextView)findViewById(R.id.t3);
        LinearLayout l1=(LinearLayout)findViewById(R.id.Linear1);
        LinearLayout l2=(LinearLayout)findViewById(R.id.Linear2);
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        if(!opened) {
            opened=true;
            //第二层linear
            LinearLayout.LayoutParams li1 = (LinearLayout.LayoutParams) l1.getLayoutParams();
            li1.topMargin = t2.getHeight();
            l1.setLayoutParams(li1);
            //排列cardview的view
            FrameLayout.LayoutParams li2 = (FrameLayout.LayoutParams) l2.getLayoutParams();
            li2.topMargin = 60 + t3.getHeight();
            l2.setLayoutParams(li2);
            //fab的位置设定
            FrameLayout.LayoutParams li3=(FrameLayout.LayoutParams) fab.getLayoutParams();
            li3.topMargin = (int)(t2.getHeight() - fab.getHeight()*0.5);
            fab.setLayoutParams(li3);

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mainaction) {
            changefragment(mainfragment);
        } else if (id == R.id.settingaction) {
            changefragment(settingfragment);
        } else if (id == R.id.historyaction) {
            changefragment(historyfragment);
        } else if (id == R.id.shareaction) {

        } else if (id == R.id.aboutaction) {
            Intent i=new Intent(MainActivity.this,AboutActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void changefragment(Fragment frag){
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.hide(settingfragment);
        transaction.hide(mainfragment);
        transaction.hide(historyfragment);
        transaction.show(frag);
        transaction.commit();
    }



    //将应用数据保存、读取，修改
    public void saveuserdata(String username,String userpassword){
        SharedPreferences.Editor editor = getSharedPreferences("USER_DATA", MODE_PRIVATE).edit();
        editor.putString("username", username);
        editor.putString("userpassword",userpassword);
        editor.commit();
    }
    public String[] readuserdata(){
        SharedPreferences read = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        String username = read.getString("username", "");
        String userpassword = read.getString("userpassword", "");
        String[] user;
        user = new String[]{username,userpassword};
        return user;
    }


    //分割从servers传来的数据
    public List<String> cutstring(String s) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(s.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
        String line;
        List<String> list=new ArrayList<String>();
        while ((line = br.readLine()) != null) {
            if (!line.trim().equals("")) {
                list.add(line);
            }
        }
        return list;
    }


//服务器参数
    public String getdata(final View v, final String data){

        servers s=new servers();
        final String message;
        message=s.getData("222.212.239.135", data);
        if(message.equals("Failed")){
            Snackbar.make(v, "获取数据失败", Snackbar.LENGTH_SHORT)
                    .setAction("收到", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
        }
        return message;
    }

}