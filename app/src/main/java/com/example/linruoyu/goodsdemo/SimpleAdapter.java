package com.example.linruoyu.goodsdemo;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * recylerview对应的适配器
 * Created by linruoyu on 2016/11/18.
 */

public class SimpleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM=0;
    private static final int TYPE_FOOTER=1;
    private LayoutInflater mInflater;
    private Context mContext;
    protected List<Goods.DataBean.BooksBean> mDatas;
    boolean flag=true;
    private Goods goods;
    public SimpleAdapter(Context context, Goods datas) {
        this.mContext=context;
        this.goods=datas;
        this.mDatas=datas.getData().getBooks();
        mInflater=LayoutInflater.from(context);
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 获取item的类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        //最后一个item设置为footerView
        if (position+1 == getItemCount()) {

                return TYPE_FOOTER;

        }else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_simple_textview, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }else if(viewType==TYPE_FOOTER) {
            View view = mInflater.inflate(R.layout.item_footerview, parent, false);
            FooterViewHolder viewHolder1=new FooterViewHolder(view);
            return viewHolder1;
            //这里可以自定义加载组件的样式

        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        /**
         * 判断是否为正常的item，如果是，则为其注册自定义监听
         */
        if(holder instanceof MyViewHolder) {
            MyViewHolder holder_item=(MyViewHolder)holder;
            holder_item.goods_title.setText(mDatas.get(position).getAuthor());
            holder_item.goods_text.setText(mDatas.get(position).getTitle());
            holder_item.goods_rating.setRating(mDatas.get(position).getRating());
            Uri uri=Uri.parse(mDatas.get(position).getAuthor_image());
            holder_item.goods_img.setImageURI(uri);
           /* holder_item.goods_img.
            holder_item.tv.setText(mDatas.get(position));*/

            setUpItemEvent(holder_item);//注册监听
        }else if(holder instanceof FooterViewHolder){
            FooterViewHolder holder_footer = (FooterViewHolder) holder;
            if (mDatas.size()!=goods.getData().getCount()) {
                holder_footer.tv.setText("正在加载中。。。。");
            }else{
                Toast.makeText(mContext, "已加载完所有", Toast.LENGTH_SHORT).show();
                holder_footer.tv.setHeight(0);
            }


        }
    }

    /**
     * 自己创建item的监听
     * @param holder
     */
    protected void setUpItemEvent(final MyViewHolder holder) {
        if(mOnItemClickListener!=null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int layoutPosition=holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, layoutPosition);

                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int layoutPosition=holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView,layoutPosition);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size()+1;
    }

    /**
     * 正常item的viewholder
     */
    class MyViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView goods_img;
        TextView goods_title;
        TextView goods_text;
        RatingBar goods_rating;
        public MyViewHolder(View itemView) {
            super(itemView);
            goods_img= (SimpleDraweeView) itemView.findViewById(R.id.goods_image);
            goods_title= (TextView) itemView.findViewById(R.id.goods_title);
            goods_text= (TextView) itemView.findViewById(R.id.goods_text);
            goods_rating= (RatingBar) itemView.findViewById(R.id.goods_rating);

        }
    }

    /**
     * 最后一个item的view holder
     * 在这里能自定义自己想要的加载更多的界面
     */
    class FooterViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        public FooterViewHolder(View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.footer_tv);
        }
    }
    public void addData(int pos){
       /* mDatas.add(pos,"Insert One");
        notifyItemInserted(pos);*/
    }
    public void deleteData(int pos){
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }

}
