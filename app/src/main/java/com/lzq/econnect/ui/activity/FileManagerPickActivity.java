package com.lzq.econnect.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lzq.econnect.R;
import com.lzq.econnect.base.BaseActivity;
import com.lzq.econnect.ui.fragment.FileFragment;
import com.lzq.econnect.utils.FileTypeUtils;
import com.lzq.econnect.utils.FileUtils;
import com.lzq.econnect.utils.UIUtil;


import java.io.File;
import java.lang.reflect.Field;

import butterknife.Bind;

@SuppressWarnings("ALL")
public class FileManagerPickActivity extends BaseActivity implements FileFragment.FileClickListener{
    //需要从一个界面跳转到文件目录列表的时候，可以传递起始的根目录
    public static final String ARG_PATH_START = "arg_path_start";
    public static final String ARG_PATH_CURRENT = "arg_path_current";
    public static final String ARG_TITLE = "arg_title";
    public static final String ARG_STATE_PATH_START = "arg_state_path_start";
    public static final String ARG_STATE_PATH_CURRENT = "arg_state_path_current";
    public static final String RESULT_FILE_PATH = "result_file_path";
    private static final int HANDLE_CLICK_DELAY = 160;

    private String mTitle;
    private String mStartPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String mCurrentPath = mStartPath;

    private String oldPath = "";

    private boolean isCopy = false;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_file_manager_pick;
    }

    @Override
    protected void doBusiness() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgs();
        initToolbar();

        if (savedInstanceState != null){
            mStartPath = getIntent().getStringExtra(ARG_PATH_START);
            mCurrentPath = getIntent().getStringExtra(ARG_PATH_CURRENT);
        }else{
            getSupportFragmentManager()
                    .beginTransaction().add(R.id.file_list_container,
                     FileFragment.getInstance(mStartPath)).commit();
        }
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        try {
            Field f = toolbar.getClass().getDeclaredField("mTitleTextView"); //反射获取toolbar的标题域
            f.setAccessible(true);
            TextView textView = (TextView) f.get(toolbar);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(!TextUtils.isEmpty(mTitle)){
            setTitle(mTitle);
        }else{
            updateTitle();
        }
    }

    /**
     * 初始化传递过来的参数
     */
    private void initArgs(){
        if (getIntent().hasExtra(ARG_TITLE)){
            mTitle = getIntent().getStringExtra(ARG_TITLE);
        }
        if(getIntent().hasExtra(ARG_PATH_START)){
            mStartPath = getIntent().getStringExtra(ARG_PATH_START);
            mCurrentPath = mStartPath;
        }
        if (getIntent().hasExtra(ARG_PATH_CURRENT)){
            String currentPath = getIntent().getStringExtra(ARG_PATH_CURRENT);
            if (currentPath.startsWith(mStartPath)){
                mCurrentPath = currentPath;
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_STATE_PATH_START, mStartPath);
        outState.putString(ARG_STATE_PATH_CURRENT, mCurrentPath);
    }

    private void updateTitle() {
        if (getSupportActionBar() != null) {
            String titlePath = mCurrentPath.isEmpty() ? "/" : mCurrentPath;
            if (titlePath.startsWith(mStartPath)) {
                titlePath = titlePath.replaceFirst(mStartPath, "根目录");
            }
            if (TextUtils.isEmpty(mTitle)) {
                getSupportActionBar().setTitle(titlePath);
            }
        }
    }

    /**
     * 点击返回home键的按钮，如果当前不是根目录，则返回到上一层目录；否则退出当前activity
     * @param menuItem
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    public void onBackPressed() {
        if ( getSupportActionBar().getTitle().equals("根目录")){
            super.onBackPressed();
        }
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0){
            fm.popBackStack();
            mCurrentPath = FileUtils.cutLastSegmentOfPath(mCurrentPath);
            updateTitle();
        }else{
            setResult(RESULT_CANCELED);
            super.onBackPressed();
        }
    }

    private void addFragmentToBackStack(String path) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.file_list_container, FileFragment.getInstance(path))
                .addToBackStack(null)
                .commit();
    }


    private void handleClickFile(File file){
        if (file.isDirectory()) {
            addFragmentToBackStack(file.getPath());
            mCurrentPath = file.getPath();
            updateTitle();
        } else {
            openFile(file);
        }
    }

    private void refreshCurrentPath(String path){
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0){
            fm.popBackStack();
        }
        addFragmentToBackStack(path);
    }

    @Override
    public void onFileClicked(final File clickedFile) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                handleClickFile(clickedFile);
            }
        }, HANDLE_CLICK_DELAY);
    }

    @Override
    public void onFileLongClick(final File longClickFile) {

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: //新建文件夹
                        makedirs(mCurrentPath);
                        break;
                    case 1://重命名
                        renameFile(longClickFile.getAbsolutePath());
                        break;
                    case 2://复制
                        oldPath = longClickFile.getPath();
                        isCopy = true;
                        break;
                    case 3://剪切
                        oldPath = longClickFile.getPath();
                        isCopy = false;
                        break;
                    case 4: //粘贴
                        if (oldPath != null) {
                            File oldFile = new File(oldPath);
                            if (oldFile.exists())//如果文件还存在
                            {
                                if (oldFile.isFile())
                                    com.lzq.econnect.utils.FileUtils.copyFile(oldPath, mCurrentPath + "/" + oldFile.getName());
                                else
                                    com.lzq.econnect.utils.FileUtils.copyFolder(oldPath, mCurrentPath + "/" + oldFile.getName());
                                if (!isCopy)//如果是剪切
                                {
                                    com.lzq.econnect.utils.FileUtils.deleteFile(oldFile);//删除原文件
                                    isCopy = true;
                                }
                                refreshCurrentPath(mCurrentPath);
                            }
                        }
                        break;
                    case 5://删除
                        com.lzq.econnect.utils.FileUtils.deleteFile(longClickFile);
                        refreshCurrentPath(mCurrentPath);
                        break;
                    case 6://选择发送
                        setResultAndFinish(longClickFile.getAbsolutePath());
                        break;

                }
            }
        };
        String[] menu = {"新建文件夹", "重命名", "复制", "剪切","粘贴", "删除", "选择发送"};
        new AlertDialog.Builder(FileManagerPickActivity.this)
                .setTitle("请选择要进行的操作")
                .setItems(menu, listener)
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();

    }


    //打开文件
    private void openFile(File file) {
        Uri fileUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            fileUri = FileProvider.getUriForFile(UIUtil.getContext(), "com.lzq.econnect.fileprovider", file);
        }else{
            fileUri = Uri.fromFile(file);
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        String type = FileTypeUtils.getMIMEType(file);
        Log.e("type", type);
        //设置intent的data和Type属性。
        intent.setDataAndType(fileUri, type);
        //跳转
        startActivity(intent);
    }


    private void setResultAndFinish(String filePath) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_FILE_PATH, filePath);
        setResult(RESULT_OK, intent);
        finish();
    }

    //文件的重命名
    public void renameFile( String filepath) {
        final File file = new File(filepath);
        LayoutInflater factory = LayoutInflater.from(this);
        View view = factory.inflate(R.layout.file_manager_dialog, null);
        final EditText editText = (EditText) view.findViewById(R.id.editText);
        editText.setText(file.getName());
        android.app.AlertDialog renameDialog = new android.app.AlertDialog.Builder(this).create();
        renameDialog.setView(view);
        renameDialog.setButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String modifyName = editText.getText().toString();
                final String fpath = file.getParentFile().getPath();
                final File newFile = new File(fpath + "/" + modifyName);
                if (newFile.exists()) {
                    //排除没有修改情况
                    if (!modifyName.equals(file.getName())) {
                        new android.app.AlertDialog.Builder(FileManagerPickActivity.this)
                                .setTitle("注意!")
                                .setMessage("文件名已存在，是否覆盖？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (file.renameTo(newFile)) {
                                            refreshCurrentPath(mCurrentPath);
                                            Toast.makeText(FileManagerPickActivity.this, "重命名成功！", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(FileManagerPickActivity.this, "重命名失败！", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                } else {
                    if (file.renameTo(newFile)) {
                        refreshCurrentPath(mCurrentPath);
                        Toast.makeText(FileManagerPickActivity.this, "重命名成功！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FileManagerPickActivity.this, "重命名失败！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        renameDialog.setButton2("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        renameDialog.show();
    }


    //新建文件夹
    public void makedirs(final String filepath)//参数为当前路径
    {
        LayoutInflater factory = LayoutInflater.from(FileManagerPickActivity.this);
        View view = factory.inflate(R.layout.file_manager_new_dialog, null);
        final EditText editText = (EditText) view.findViewById(R.id.editText);
        editText.setText("新建文件夹");//初始显示内容
        AlertDialog.Builder newFileDialog = new AlertDialog.Builder(this);
        newFileDialog.setView(view);
        newFileDialog.setPositiveButton("确定", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String modifyName = editText.getText().toString();//获取EditText的内容
                File newFile = new File(filepath + "/" + modifyName);//当前目录+EditText既新的文件路径
                if (!newFile.exists())//如果同名文件不存在
                {
                    newFile.mkdir();
                    refreshCurrentPath(mCurrentPath);
                } else {
                }
            }
        }).setNegativeButton("取消", null);
        newFileDialog.create();
        newFileDialog.show();
        refreshCurrentPath(mCurrentPath);
    }


}