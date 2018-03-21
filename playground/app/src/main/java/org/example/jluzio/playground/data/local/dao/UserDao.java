package org.example.jluzio.playground.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.example.jluzio.playground.data.local.entity.User;

import java.util.List;

/**
 * Created by jluzio on 21/03/2018.
 */

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id = :userId")
    User get(String userId);

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(String... userIds);

    @Query("SELECT * FROM user WHERE email LIKE :email LIMIT 1")
    User findByEmail(String email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
