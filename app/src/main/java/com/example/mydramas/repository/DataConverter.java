package com.example.mydramas.repository;

import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mydramas.repository.db.DramaEntity;
import com.example.mydramas.repository.network.DramaRemote;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class DataConverter {
    //==============================
    // Static Fields
    //==============================
    public static final String TAG = "DataConverter";

    //==============================
    // Static Methods
    //==============================
    // Translate newwork data format to local DB data format.
    public static void DramaRemoteToDramaEntity(@NonNull List<DramaRemote> dramaRemotes, @NonNull List<DramaEntity> dramaEntities) {
        dramaEntities.clear();

        for (DramaRemote remote: dramaRemotes) {
            DramaEntity entity = new DramaEntity();
            entity.dramaId = remote.getDramaId();
            entity.name = remote.getName();
            entity.totalViews = remote.getTotalViews();
            entity.createdAt = remote.getCreatedAt();
            entity.thumb = remote.getThumb();
            entity.rating = remote.getRating();
            dramaEntities.add(entity);
        }
    }

    // Translate GMT time to local time.
    // Return local time.
    public static String DateReferenceTimeZone(String gmt) {
        String localtime = gmt;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = sdf.parse(gmt);
            SimpleDateFormat tdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            tdf.setTimeZone(TimeZone.getDefault());
            localtime = tdf.format(date);
        } catch (ParseException e) {
            localtime = gmt;
            Log.w(TAG, "DateReferenceTimeZone: Translatoin Fail");
        }
        return localtime;
    }
}
