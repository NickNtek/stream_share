package gr.nick.stream_service_share.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import gr.nick.stream_service_share.DAO.UserDAO;
import gr.nick.stream_service_share.Databases.AppDatabase;
import gr.nick.stream_service_share.Models.User;

public class UserRepository {

    private UserDAO mUserDAO;
    private LiveData<List<User>> mAllUsers;

    public UserRepository(Application application) {
        AppDatabase userDatabase = AppDatabase.getUserDatabase(application);
        mUserDAO = userDatabase.userDAO();
        mAllUsers = mUserDAO.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public void insert (User users) {

        AppDatabase.databaseWriteExecutor.execute(() -> {
            mUserDAO.insert(users);
        });
    }

}
