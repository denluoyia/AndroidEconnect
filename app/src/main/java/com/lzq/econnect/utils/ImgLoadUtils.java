package com.lzq.econnect.utils;

import android.text.TextUtils;
import android.widget.ImageView;

import com.lzq.econnect.R;
import com.squareup.picasso.Picasso;


/**
 * function : 图片加载工具类.
 * <p></p>
 */
@SuppressWarnings("unused")
public class ImgLoadUtils {
    private static final String TAG = ImgLoadUtils.class.getSimpleName();

    private static final int defaultPlaceholderForImg = R.drawable.toolbar_home_selected;

    private static Picasso getPicasso() {
        return Picasso.with(UIUtil.getContext());
    }

    /**
     * 加载图片(加载完整地址包括网络地址和本地地址)
     *
     * @param imgPath          图片地址 eg: http://www.ypwl.com/up_files/picture.jpg , /storage0/DCIM/picture.jpg
     * @param targetImageView  目标ImageView
     * @param placeholderResId 默认图片资源id
     */
    public static void loadImgByPath(String imgPath, ImageView targetImageView, int placeholderResId) {
        if (TextUtils.isEmpty(imgPath)) {
            getPicasso().load(placeholderResId > 0 ? placeholderResId : defaultPlaceholderForImg).into(targetImageView);
            return;
        }
        if (!imgPath.startsWith("http") && !imgPath.startsWith("file://")) {
            imgPath = "file://" + imgPath;
        }
        getPicasso().load(imgPath).placeholder(placeholderResId > 0 ? placeholderResId : defaultPlaceholderForImg).into(targetImageView);
    }

}
