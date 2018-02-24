package com.lzq.econnect.ui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.lzq.econnect.MyApp;
import com.lzq.econnect.R;
import com.lzq.econnect.base.BaseActivity;
import com.lzq.econnect.bean.LeftMenuBean;
import com.lzq.econnect.event.HomeConnectPCEvent;
import com.lzq.econnect.event.HomeIVHelperEvent;
import com.lzq.econnect.ui.adapter.LeftDrawerAdapter;
import com.lzq.econnect.ui.adapter.MainPagerAdapter;
import com.lzq.econnect.ui.fragment.HomeFragment;
import com.lzq.econnect.utils.FileStorageUtils;
import com.lzq.econnect.utils.UIUtil;
import com.squareup.otto.Subscribe;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

@SuppressWarnings("ALL")
public class MainActivity extends BaseActivity {
    private static final int FILE_PICKER_REQUEST_CODE = 1;

    @Bind(R.id.main_toolbar)
    Toolbar toolbar;
    @Bind(R.id.draw_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.toolbar_btn1)
    ImageButton toolbarBtn1;
    @Bind(R.id.toolbar_btn2)
    ImageButton toolbarBtn2;
    @Bind(R.id.toolbar_btn3)
    ImageButton toolbarBtn3;
    @Bind(R.id.left_drawer_listview)
    ListView lvLeftMenu;
    @Bind(R.id.main_pager)
    ViewPager mainPager;
    @Bind(R.id.ld_layout)
    LinearLayout ldLayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private String[] lvs = UIUtil.getStringArray(R.array.ld_menu_list);
    private int[] licons = {R.drawable.left_about_us, R.drawable.left_contact_us, R.drawable.left_exist};
    private List<LeftMenuBean> leftMenuBeanList = new ArrayList<>();
    private LeftDrawerAdapter mLeftDrawerAdapter;


    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void doBusiness() {
        toolbar.setTitle("");
        toolbar.setTitleTextColor(UIUtil.getColor(R.color.toolbar_title_color));

        toolbarBtn1.setOnClickListener(tbListener);
        toolbarBtn2.setOnClickListener(tbListener);
        toolbarBtn3.setOnClickListener(tbListener);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //设置菜单列表
        initData();
        mLeftDrawerAdapter = new LeftDrawerAdapter(this, leftMenuBeanList);
        lvLeftMenu.setAdapter(mLeftDrawerAdapter);
        lvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mDrawerLayout.closeDrawer(ldLayout); //调试一下磕碰的感觉
                switch (i){
                    case 0:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                        break;
                    case 2:
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });

        mainPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        mainPager.setCurrentItem(0);
        mainPager.addOnPageChangeListener(new MainOnPageChangeListener());

        FileStorageUtils.initAppDir();
    }


    private void initData(){
        leftMenuBeanList.clear();
        for (int i = 0; i < lvs.length; i++){
            LeftMenuBean bean = new LeftMenuBean();
            bean.setImgId(licons[i]);
            bean.setMenuText(lvs[i]);
            leftMenuBeanList.add(bean);
        }
    }

    private View.OnClickListener tbListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.toolbar_btn1:
                    updateToolbarUi(0);
                    mainPager.setCurrentItem(0);
                    break;

                case R.id.toolbar_btn2:
                    updateToolbarUi(1);
                    mainPager.setCurrentItem(1);
                    break;

                case R.id.toolbar_btn3:
                    updateToolbarUi(2);
                    mainPager.setCurrentItem(2);
                    break;
                default:
                    break;
            }
        }
    };

    private class MainOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            updateToolbarUi(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void updateToolbarUi(int index){
        switch (index){
            case 0:
                toolbarBtn1.setBackgroundResource(R.drawable.toolbar_selected_home);
                toolbarBtn2.setBackgroundResource(R.drawable.toolbar_normal_pc);
                toolbarBtn3.setBackgroundResource(R.drawable.toolbar_normal_msg);
                break;
            case 1:
                toolbarBtn1.setBackgroundResource(R.drawable.toolbar_normal_home);
                toolbarBtn2.setBackgroundResource(R.drawable.toolbar_selected_pc);
                toolbarBtn3.setBackgroundResource(R.drawable.toolbar_normal_msg);
                break;
            case 2:
                toolbarBtn1.setBackgroundResource(R.drawable.toolbar_normal_home);
                toolbarBtn2.setBackgroundResource(R.drawable.toolbar_normal_pc);
                toolbarBtn3.setBackgroundResource(R.drawable.toolbar_selected_msg);
                break;
            default:
                break;
        }
    }

    private long touchTime = 0;
    /**按返回键做判断*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            long curTime = System.currentTimeMillis();
            long waitTime = 2000; //等待两秒
            if((curTime - touchTime) >= waitTime){
                Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                touchTime = curTime;
            }else{
                MyApp.getApplication().removeAll();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**订阅事件*/
    @Subscribe
    public void HomeViewConnectPC(HomeConnectPCEvent event){
        if(event.getConnectState()){
            mainPager.setCurrentItem(1);
        }
    }

    @Subscribe
    public void HomeViewOpenHelperCenter(HomeIVHelperEvent event){
        mainPager.setCurrentItem(2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String path = data.getStringExtra(FileManagerPickActivity.RESULT_FILE_PATH);
            if (path != null){
                File sendFile = new File(path);
                int rootPathLen = sendFile.getParent().length();
                //选择完成后进行传输到电脑端
                uploadFiles(new File(path), rootPathLen);
                Toast.makeText(this, "文件[" + sendFile.getName() +"]已发送到PC端", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //遍历某个目录下的所有文件上传
    private void uploadFiles(File file, int rootLen){
        if (file.isFile()){
            new Thread(new HomeFragment.TcpClient(file.getAbsolutePath(), rootLen)).start();
            return;
        }else{ //是目录
            File[] files = file.listFiles();
            if (files == null || files.length == 0) return;
            for (File f : files){
                uploadFiles(f, rootLen);
            }
        }
    }

}
