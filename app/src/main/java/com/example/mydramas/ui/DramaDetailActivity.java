package com.example.mydramas.ui;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mydramas.R;
import com.example.mydramas.repository.db.DramaEntity;
import com.example.mydramas.viewmodel.DramaListViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DramaDetailActivity extends AppCompatActivity {
    //==============================
    // Static Fields
    //==============================
    static final String TAG = "DramaDetailActivity";

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

        Uri data = getIntent().getData();
        if (data != null) {
            Pattern p = Pattern.compile("/dramas/:(\\d+)(.*)");
            Matcher m = p.matcher(data.getPath());
            if (m.matches()) {
                Log.d(TAG, "deep link match: drama id=" + m.group(1));
                final int dramaId = Integer.valueOf(m.group(1));
                getDramaListViewModel().postLoadDeepLinkDramaFromDB(dramaId).observe(this
                        , new Observer<DramaEntity>() {
                            @Override
                            public void onChanged(DramaEntity dramaEntity) {
                                Log.d(TAG, "onChanged(deep link drama):" + dramaEntity.dramaId);
                                if (dramaId == dramaEntity.dramaId) {
                                    DramaDetailFragment fragment = DramaDetailFragment.getInstance(dramaEntity);
                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .add(R.id.fragment_container, fragment, DramaDetailFragment.TAG)
                                            .commit();
                                }
                            }
                        }
                );
            }
        } else {
            finish();
        }
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
}
