package com.example.mydramas.repository.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

// Room format for data entity
@Entity(tableName = "dramas")
public class DramaEntity {
    @PrimaryKey
    @ColumnInfo(name = "drama_id")
    public int dramaId;         // 1

    @ColumnInfo(name = "name")
    public String name;         // 致我們單純的小美好

    @ColumnInfo(name = "total_views")
    public String totalViews;   // 23562274

    @ColumnInfo(name = "created_at")
    public String createdAt;    // 2017-11-23T02:04:39.000Z

    @ColumnInfo(name = "thumb")
    public String thumb;        // https://i.pinimg.com/originals/61/d4/be/61d4be8bfc29ab2b6d5cab02f72e8e3b.jpg

    @ColumnInfo(name = "rating")
    public String rating;       // 4.4526
}
