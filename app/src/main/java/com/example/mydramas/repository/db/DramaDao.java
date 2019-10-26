package com.example.mydramas.repository.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DramaDao {
    @Query("SELECT * FROM dramas LIMIT 50")
    List<DramaEntity> loadAllDramas();

    @Query("SELECT * FROM dramas WHERE name LIKE '%'||:keyword||'%' LIMIT 50")
    List<DramaEntity> loadDramasWithKeyword(String keyword);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DramaEntity> dramas);

    @Query("SELECT * FROM dramas WHERE drama_id=:dramaId LIMIT 50")
    List<DramaEntity> loadDramasWithDramaId(int dramaId);
}
