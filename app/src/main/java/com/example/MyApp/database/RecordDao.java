package com.example.MyApp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.MyApp.model.Record;

import java.util.List;

@Dao
public interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Record record);

    @Update
    void update(Record record);

    // LiveData works with Room database to get instant update whenever there is any changes
    @Query("SELECT * FROM Record ORDER BY id ASC")
    LiveData<List<Record>> getAscendingRecord();

    @Query("SELECT * FROM Record LIMIT 1")
    Record getFirstRecord();
}
