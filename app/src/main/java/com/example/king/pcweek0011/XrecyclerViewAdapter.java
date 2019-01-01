package com.example.king.pcweek0011;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.king.pcweek0011.beans.ProductBean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class XrecyclerViewAdapter extends XRecyclerView.Adapter<XrecyclerViewAdapter.XrecyclerViewVH> {

    private Context context;
    private List<ProductBean.DataBean> list;

    public XrecyclerViewAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    /**
     * 刷新
     * @param list
     */
    public void setList(List<ProductBean.DataBean> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

    /**
     * 加载
     * @param list
     */
    public void addList(List<ProductBean.DataBean> list) {
        if (list != null) {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public XrecyclerViewVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.xrecycler_item,viewGroup,false);
        XrecyclerViewVH xrecyclerViewVH = new XrecyclerViewVH(view);
        return xrecyclerViewVH;
    }

    @Override
    public void onBindViewHolder(@NonNull XrecyclerViewVH xrecyclerViewVH, int i) {

        ProductBean.DataBean dataBean = list.get(i);

        xrecyclerViewVH.tv_title.setText(dataBean.getTitle());
        //获取图片
        String images = dataBean.getImages();
        String[] img_split = images.split("\\|");
        //设置图片
        if (img_split.length > 0 && img_split!= null){
            Glide.with(context).load(img_split[0]).into(xrecyclerViewVH.iv_productIcon);
        } else {
            xrecyclerViewVH.iv_productIcon.setImageResource(R.drawable.ic_launcher_background);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class XrecyclerViewVH extends XRecyclerView.ViewHolder{
        ImageView iv_productIcon;
        TextView tv_title;
        public XrecyclerViewVH(@NonNull View itemView) {
            super(itemView);
            this.iv_productIcon = itemView.findViewById(R.id.iv_productIcon);
            this.tv_title = itemView.findViewById(R.id.tv_title);
        }
    }
}
