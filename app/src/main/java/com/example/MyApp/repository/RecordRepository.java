package com.example.MyApp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.MyApp.database.RecordRoomDatabase;
import com.example.MyApp.database.RecordDao;
import com.example.MyApp.model.Record;

import java.util.List;

public class RecordRepository {
    private RecordDao recordDao;
    private LiveData<List<Record>> recordList;

    // The DAO is passed into the repository constructor as opposed to the whole database. This is
    // because you only need access to the DAO, since it contains all the read/write methods for
    // the database. There's no need to expose the entire database to the repository.
    public RecordRepository(Application application) {
        RecordRoomDatabase db = RecordRoomDatabase.getDatabase(application);
        recordDao = db.recordDao();
        recordList = recordDao.getAscendingRecord();
    }

    // The getAllNotes method returns the LiveData list of notes from Room; we can do this because
    // of how we defined the getAscendingNote() method to return LiveData in the MoodNoteDao.
    // Room executes all queries on a separate thread. Then observed LiveData will notify the
    // observer on the main thread when the data has changed.
    public LiveData<List<Record>> getAllRecords() {
        return recordList;
    }

    //We need to not run the insert on the main thread, so we use the ExecutorService we created
    // in the MoodNoteRoomDatabase to perform the insert on a background thread.
    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Record record) {
        RecordRoomDatabase.databaseWriteExecutor.execute(() -> {
            recordDao.insert(record);
        });
    }

    public void update(Record record) {
        RecordRoomDatabase.databaseWriteExecutor.execute(() -> {
            recordDao.update(record);
        });
    }
}
