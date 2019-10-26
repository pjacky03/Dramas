package com.example.mydramas.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.mydramas.R;
import com.example.mydramas.repository.db.DramaEntity;
import com.example.mydramas.viewmodel.DramaListViewModel;

public class DramaActivity extends AppCompatActivity
    implements DramaItemRecyclerViewAdapter.OnItemClickListener {
    //==============================
    // Static Fields
    //==============================
    static final String TAG = "DramaActivity";

    //==============================
    // Member Fields
    //==============================
    DramaListViewModel mDramaListViewModel;

    //==============================
    // Member Methods
    //==============================
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDramaListViewModel = ViewModelProviders.of(this).get(DramaListViewModel.class);

        if (savedInstanceState == null) {
            DramaListFragment fragment = new DramaListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, DramaListFragment.TAG).commit();
        }
    }

    @Override
    public void onItemClick(View view, DramaEntity entity) {
        Log.d(TAG, "Click="+entity.name);
        show(entity);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    DramaListViewModel getDramaListViewModel() {
        return mDramaListViewModel;
    }

    void show(DramaEntity entity) {
        DramaDetailFragment fragment = DramaDetailFragment.getInstance(entity);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("drama")
                .replace(R.id.fragment_container, fragment, null)
                .commit();
    }
}
