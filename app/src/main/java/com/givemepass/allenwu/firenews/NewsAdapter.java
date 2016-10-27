package com.givemepass.allenwu.firenews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Admin on 2016/8/22.
 */
public class NewsAdapter  extends BaseAdapter {
    private Context context;
    private ArrayList<News> items;

    public NewsAdapter(Context context, ArrayList<News> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if(view==null){

            holder=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.list_item,null);
            //
            holder.date= (TextView) view.findViewById(R.id.news_date);
holder.title= (TextView) view.findViewById(R.id.news_title);
            holder.url= (TextView) view.findViewById(R.id.news_url);
            holder.image= (ImageView) view.findViewById(R.id.news_image);
            view.setTag(holder);
        }else {

           holder= (ViewHolder) view.getTag();
        }
News  data=items.get(position);

       // holder.image.setImageResource();
        holder.date.setText(data.getDate());
        holder.title.setText(data.getTitle());
        holder.url.setText(data.getUrl());
//Glide
        Glide.with(context).load(data.getPng()).into(holder.image);

        return view;
    }

    class ViewHolder{

        public ImageView image;
        public TextView title;
        public TextView date;
        public TextView url;

    }
}
