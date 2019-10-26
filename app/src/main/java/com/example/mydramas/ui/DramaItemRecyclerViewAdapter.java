package com.example.mydramas.ui;

import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydramas.R;
import com.example.mydramas.repository.DataConverter;
import com.example.mydramas.repository.db.DramaEntity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class DramaItemRecyclerViewAdapter
        extends RecyclerView.Adapter<DramaItemRecyclerViewAdapter.ViewHolder> {
    //==============================
    // Types
    //==============================
    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView mImageView;
        final TextView mNameView;
        final TextView mRatingView;
        final TextView mDateView;
        final View mSelf;

        ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.imageView);
            mNameView = (TextView) view.findViewById(R.id.title);
            mRatingView = (TextView) view.findViewById(R.id.rating);
            mDateView = (TextView) view.findViewById(R.id.date);
            mSelf = view;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, DramaEntity entity);
    }

    //==============================
    // Member Fields
    //==============================
    List<DramaEntity> mDramaEntities;
    OnItemClickListener mOnItemClickListener;

    //==============================
    // Member Methods
    //==============================
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drama_item_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mNameView.setText(mDramaEntities.get(position).name);

        holder.mDateView.setText(DataConverter.DateReferenceTimeZone(mDramaEntities.get(position).createdAt));

        holder.mRatingView.setText(mDramaEntities.get(position).rating);
        Picasso.get()
                .load(mDramaEntities.get(position).thumb)
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .into(holder.mImageView);

        holder.mSelf.setTag(mDramaEntities.get(position));
        holder.mSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, (DramaEntity)v.getTag());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mDramaEntities == null) ? 0 : mDramaEntities.size();
    }

    public void setOnItemClickListener(OnItemClickListener lister) {
        mOnItemClickListener = lister;
    }

    public void setDramaEntities(final List<DramaEntity> entities) {
        if (mDramaEntities  == null) {
            mDramaEntities = entities;
            this.notifyDataSetChanged();
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mDramaEntities.size();
                }

                @Override
                public int getNewListSize() {
                    return entities.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mDramaEntities.get(oldItemPosition).dramaId
                            == entities.get(newItemPosition).dramaId;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    DramaEntity newDrama = entities.get(newItemPosition);
                    DramaEntity oldDrama = mDramaEntities.get(oldItemPosition);
                    return newDrama.dramaId == oldDrama.dramaId
                            && newDrama.name.equals(oldDrama.name)
                            && newDrama.rating.equals(oldDrama.rating)
                            && newDrama.createdAt.equals(oldDrama.createdAt)
                            && newDrama.thumb.equals(oldDrama.thumb);
                }
            });
            mDramaEntities = entities;
            result.dispatchUpdatesTo(this);
        }
    }
}
