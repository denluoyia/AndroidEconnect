package com.lzq.econnect.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Function: QA JavaBean
 * Created by lzq on 2017/4/9.
 */

public class QABean implements Parcelable{

    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    //无参构造函数
    public QABean(){}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(content);
    }

    public static final Creator<QABean> CREATOR = new Creator<QABean>() {
        public QABean createFromParcel(Parcel in) {
            return new QABean(in);
        }

        public QABean[] newArray(int size) {
            return new QABean[size];
        }
    };

    private QABean(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
    }
}
