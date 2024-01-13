package gr.nick.stream_service_share.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import gr.nick.stream_service_share.Models.User;
import gr.nick.stream_service_share.Repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<List<User>> allUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        allUsers = userRepository.getAllUsers();
    }



    public LiveData<List<User>> getAllUsers() { return allUsers; }
    public void insert(User user) { userRepository.insert(user); }
}
