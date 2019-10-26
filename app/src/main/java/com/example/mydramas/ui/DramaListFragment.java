package com.example.mydramas.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydramas.R;
import com.example.mydramas.repository.db.DramaEntity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DramaListFragment extends Fragment {
    //==============================
    // Static Fields
    //==============================
    public static final String TAG = "DramaListFragment";

    //==============================
    // Member Fields
    //==============================
    DramaActivity mDramaActivity;

    DramaItemRecyclerViewAdapter mDramaItemRecyclerViewAdapter;
    SearchView mSearchView;
    RecyclerView mRecyclerView;

    String mSearchKeyword = "";

    //==============================
    // Member Methods
    //==============================
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater
            , @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        Log.d(TAG,"onCreateView");


        View rootView = inflater.inflate(R.layout.fragment_drama_item_list, container, false);

        mSearchView = (SearchView) rootView.findViewById(R.id.search);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.item_list);
        mDramaItemRecyclerViewAdapter = new DramaItemRecyclerViewAdapter();
        mRecyclerView.setAdapter(mDramaItemRecyclerViewAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG,"onActivityCreated");

        mDramaActivity = (DramaActivity) getActivity();

        // Request loading data from network, and then updating drama list.
        mDramaActivity.getDramaListViewModel().postLoadDramasFromServer().observe(getViewLifecycleOwner()
                , new Observer<Long>() {
                    @Override
                    public void onChanged(Long networkDataUpdateTime) {
                        Log.d(TAG, "onChanged(network data update):" + networkDataUpdateTime);
                        mDramaActivity.getDramaListViewModel().postLoadDramasFromDB(mSearchKeyword);
                    }
                }
        );

        // Setup recyclerView
        mDramaActivity.getDramaListViewModel().postLoadDramasFromDB("").observe(getViewLifecycleOwner()
                , new Observer<List<DramaEntity>>() {
                    @Override
                    public void onChanged(List<DramaEntity> dramaEntities) {
                        Log.d(TAG, "onChanged(drama list update):" + dramaEntities.size());
                        mDramaItemRecyclerViewAdapter.setDramaEntities(dramaEntities);
                    }
                }
        );
        mDramaItemRecyclerViewAdapter.setOnItemClickListener((DramaActivity) getActivity());

        // Setup search bar
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit="+query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange="+newText);
                mSearchKeyword = newText;
                mDramaActivity.getDramaListViewModel().postLoadDramasFromDB(newText);
                return false;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG,"onStart");

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        mSearchKeyword = sharedPref.getString(getString(R.string.key_searchkeyword), "");

        mDramaActivity.getDramaListViewModel().postLoadDramasFromDB(mSearchKeyword);
        mSearchView.setQuery(mSearchKeyword,false);
        if (!TextUtils.isEmpty(mSearchKeyword)) {
            mSearchView.setIconified(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG,"onStop");

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.key_searchkeyword), mSearchKeyword);
        editor.commit();
    }
}
