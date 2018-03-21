package org.example.jluzio.playground.data.local.database;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * Created by jluzio on 21/03/2018.
 */

public class AppDatabaseManager {
    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "app-db")
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
