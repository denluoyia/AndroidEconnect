package com.lzq.econnect.bean;

import com.lzq.econnect.base.BaseBean;

/**
 *
 * Created by lzq on 2017/3/25.
 */

public class LeftMenuBean extends BaseBean{

    private String menuText;
    private int imageId;

    public String getMenuText() {
        return menuText;
    }

    public void setMenuText(String menuText) {
        this.menuText = menuText;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImgId(int imageId) {
        this.imageId = imageId;
    }
}
