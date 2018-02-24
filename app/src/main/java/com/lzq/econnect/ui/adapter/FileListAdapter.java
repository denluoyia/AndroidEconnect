package com.lzq.econnect.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzq.econnect.R;
import com.lzq.econnect.utils.FileTypeUtils;
import com.lzq.econnect.utils.ImgLoadUtils;

import java.io.File;
import java.util.List;

/**
 * function: 文件列表数据适配器
 * Created by lzq on 2017/4/11.
 */

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ItemViewHolder>{

    private List<File> mFileList;

    public FileListAdapter(List<File> files){
        this.mFileList = files;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder,final int position) {
        final File item = getItem(position);
        if (item == null) return;
        FileTypeUtils.FileType fileType = FileTypeUtils.getFileType(item);
        holder.mFileTitle.setText(item.getName());
        holder.mFileSubtitle.setText(fileType.getDescription());

        if (fileType.getIcon() == R.drawable.icon_jpg){ //对图片加载显示处理
            holder.mFileImage.setImageResource(fileType.getIcon());
            ImgLoadUtils.loadImgByPath(item.getAbsolutePath(), holder.mFileImage, R.drawable.icon_jpg);
        }else {
            holder.mFileImage.setImageResource(fileType.getIcon());
        }

        holder.mFileRightIcon.setVisibility(item.isDirectory()? View.VISIBLE : View.GONE);
        holder.itemView.setTag(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(view, position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                boolean flag = itemLongClickListener.onItemLongClick(view, position);
                notifyDataSetChanged();
                return flag;

            }
        });
    }

    @Override
    public int getItemCount() {
        return mFileList.size();
    }

    public File getItem(int position){
        return mFileList.get(position);
    }

     class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView mFileImage;
        TextView mFileTitle;
        TextView mFileSubtitle;
        ImageView mFileRightIcon;

        private ItemViewHolder(View itemView) {
            super(itemView);
            mFileImage = (ImageView) itemView.findViewById(R.id.file_img);
            mFileTitle = (TextView) itemView.findViewById(R.id.file_name);
            mFileSubtitle = (TextView) itemView.findViewById(R.id.file_describe);
            mFileRightIcon = (ImageView) itemView.findViewById(R.id.file_img_next_right);
        }
    }

    /**
     * 设置列表条目的点击监听接口
     */
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener{
        boolean onItemLongClick(View view, int position);
    }

    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        this.itemLongClickListener = listener;
    }

}
