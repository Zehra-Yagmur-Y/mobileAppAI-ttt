/*package com.example.tictactoe;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tictactoe.data.User;
import com.example.tictactoe.data.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private final LiveData<List<User>> allUsers;

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
        allUsers = userRepository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }


    public void addUser(String username) {
        userRepository.addUser(username);
    }
}
*/