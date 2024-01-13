package gr.nick.stream_service_share.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import gr.nick.stream_service_share.Models.User;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();

    @Query("Select * from user where username = :username")
    LiveData<List<User>> getUserByID(String username);

    @Insert
    void insert(User user);


    @Delete
    void delete(User user);
}
