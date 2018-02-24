package com.lzq.econnect.ui.helper;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

import com.lzq.econnect.base.BaseHelper;
import com.lzq.econnect.utils.UIUtil;

import java.util.List;

/**
 *
 * 处理反馈意见的业务逻辑
 * Created by luozhenqin on 2017/4/7.
 */

public class FeedbackHelper extends BaseHelper{
    private FeedbackView mView;
    public FeedbackHelper(FeedbackView view){
        this.mView = view;
    }

    public void postData(int type, String msg){
        Uri uri = Uri.parse("mailto:L1773259309@qq.com");
        final Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_SUBJECT, "E连 意见反馈"); // 主题
        intent.putExtra(Intent.EXTRA_TEXT, type + " " +  msg); // 正文
        PackageManager pm = UIUtil.getContext().getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (infos == null || infos.size() <= 0){
            Toast.makeText(UIUtil.getContext(), "no email", Toast.LENGTH_SHORT).show();
        }
        UIUtil.getContext().startActivity(intent);
    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
