package com.example.MyApp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.MyApp.model.Record;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Record.class}, version = 2, exportSchema = false)
public abstract class RecordRoomDatabase extends RoomDatabase {
    public abstract RecordDao recordDao();

    private static volatile RecordRoomDatabase INSTANCE;
    // We've created an ExecutorService with a fixed thread pool that you will use to run database
    // operations asynchronously on a background thread.
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // getDatabase returns the singleton.
    // It'll create the database the first time it's accessed, using Room's database builder to
    // create a RoomDatabase object in the application context from the NoteRoomDatabase class
    // and names it "note_database".

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Perform the migration, such as adding a new column
            database.execSQL("ALTER TABLE Record ADD COLUMN newColumn TEXT");
        }
    };
    public static RecordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RecordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    RecordRoomDatabase.class, "record_database").addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
