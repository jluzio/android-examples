package org.example.jluzio.playground.data.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import org.example.jluzio.playground.data.local.dao.UserDao;
import org.example.jluzio.playground.data.local.entity.User;

/**
 * Created by jluzio on 21/03/2018.
 */

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();

}
