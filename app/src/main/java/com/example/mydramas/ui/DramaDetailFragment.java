package com.example.mydramas.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.mydramas.R;
import com.example.mydramas.repository.DataConverter;
import com.example.mydramas.repository.db.DramaEntity;
import com.squareup.picasso.Picasso;

public class DramaDetailFragment extends Fragment {
    //==============================
    // Static Fields
    //==============================
    public static final String TAG = "DramaDetailFragment";

    static final String NAME = "name";
    static final String THUMB = "thumb";
    static final String RATING = "rating";
    static final String TOTALVIEWS = "totalviews";
    static final String CREATEAT = "createAt";

    //==============================
    // Static Methods
    //==============================
    public static DramaDetailFragment getInstance(DramaEntity entity) {
        DramaDetailFragment fragment = new DramaDetailFragment();
        Bundle args = new Bundle();
        args.putString(NAME, entity.name);
        args.putString(THUMB, entity.thumb);
        args.putString(RATING, entity.rating);
        args.putString(TOTALVIEWS, entity.totalViews);
        args.putString(CREATEAT, entity.createdAt);
        fragment.setArguments(args);
        return fragment;
    }

    //==============================
    // Member Fields
    //==============================
    ImageView mThumb;
    TextView mRating;
    TextView mName;
    TextView mTotalViews;
    TextView mDate;

    //==============================
    // Member Methods
    //==============================
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG,"onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_drama_item_detail, container, false);
        mThumb = rootView.findViewById(R.id.imageView2);
        mRating = rootView.findViewById(R.id.rating2);
        mName = rootView.findViewById(R.id.name2);
        mTotalViews = rootView.findViewById(R.id.viewcount2);
        mDate = rootView.findViewById(R.id.date2);

        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.detail_toolbar);
        appCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionbar = appCompatActivity.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        //actionbar.setHomeButtonEnabled(true);
        //actionbar.setDefaultDisplayHomeAsUpEnabled(true);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG,"onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG,"onStart");

        Bundle args = getArguments();

        mRating.setText(args.getString(RATING,"Unknown"));
        mName.setText(args.getString(NAME,"Unknown"));
        mTotalViews.setText("Total views: " + args.getString(TOTALVIEWS,"Unknown"));
        String date = "Created at: " + DataConverter.DateReferenceTimeZone(args.getString(CREATEAT,"Unknown"));
        mDate.setText(date);

        Picasso.get()
                .load(args.getString(THUMB))
                .centerCrop()
                .fit()
                .into(mThumb);
    }
}
