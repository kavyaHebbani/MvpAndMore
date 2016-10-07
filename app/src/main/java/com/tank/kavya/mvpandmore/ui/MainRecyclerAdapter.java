package com.tank.kavya.mvpandmore.ui;

import com.squareup.picasso.Picasso;
import com.tank.kavya.mvpandmore.R;
import com.tank.kavya.mvpandmore.pojo.ImageItem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Kavya
 */
public class MainRecyclerAdapter
        extends RecyclerView.Adapter<MainRecyclerAdapter.MainRecyclerViewHolder> {

    @NonNull
    private Context mContext;

    @NonNull
    private List<ImageItem> mItems = new ArrayList<>();

    public MainRecyclerAdapter(@NonNull Context context) {
        assertNotNull(context);

        mContext = context;
    }

    public void updateItems(List<ImageItem> item) {
        mItems.addAll(item);
        notifyDataSetChanged();
    }

    @Override
    public MainRecyclerViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.main_image_item, parent, false);
        return new MainRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MainRecyclerViewHolder holder, final int position) {
        if (mItems.size() == 0) {
            return;
        }
        Picasso.with(mContext)
               .load(mItems.get(position).imageUrl())
               .fit()
               .placeholder(R.mipmap.ic_launcher)
               .error(R.mipmap.ic_launcher)
               .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class MainRecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;

        MainRecyclerViewHolder(final View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image_item);
        }
    }

}
