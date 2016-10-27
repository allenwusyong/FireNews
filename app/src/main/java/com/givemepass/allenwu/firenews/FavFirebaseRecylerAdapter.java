package com.givemepass.allenwu.firenews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;


/**
 * Created by Admin on 2016/9/13.
 */
public class FavFirebaseRecylerAdapter extends FirebaseRecyclerAdapter<Photo,FavFirebaseRecylerAdapter.MyviewHolder> {
    static OnItemClickListener mItemClickListener;
    Context context;
DatabaseReference mref;  //never use
    public FavFirebaseRecylerAdapter(Class<Photo> modelClass, int modelLayout, Class<MyviewHolder> viewHolderClass, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context=context;
        mref=ref;
    }

    @Override
    protected void populateViewHolder(MyviewHolder viewHolder, Photo model, int position) {
        viewHolder.txtTitle.setText(model.getTitle());
        viewHolder.txtContent.setText(model.getContent());
        Glide.with(context)
                .load(model.getImgvUrl())
                .placeholder(R.drawable.holder_image)
                .into(viewHolder.image);
    }


    public   static class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView txtTitle, txtContent;


        public MyviewHolder(View itemView) {

            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.row_photo_image);
            txtContent = (TextView) itemView.findViewById(R.id.row_photo_content);
            txtTitle = (TextView) itemView.findViewById(R.id.row_photo_title);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mItemClickListener!=null){
                Log.d("test","not null");
                mItemClickListener.onItemClick(getPosition(),view);
            }
        }
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(int position, View newsType);
    }


}
