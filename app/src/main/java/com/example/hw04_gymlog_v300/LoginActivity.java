package com.example.hw04_gymlog_v300;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw04_gymlog_v300.database.GymLogRepository;
import com.example.hw04_gymlog_v300.database.entities.User;
import com.example.hw04_gymlog_v300.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private User user = null;

    private GymLogRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verifyUser()) {
                    ToastMaker("Invalid credentials");
                } else {
                    Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId());
                    startActivity(intent);
                }
            }
        });
    }

    private boolean verifyUser(){
        String username = binding.userNameLoginEditText.getText().toString();
        if(username.isEmpty()){
            ToastMaker("Username should not be blank");
            return false;
        }
        user = repository.getUserByUserName(username);
        if(user != null){
            String password = binding.passwordLoginEditText.getText().toString();
            if(password.equals(user.getPassword())){
                return true;
            }else{
                ToastMaker("Invalid password");
                return false;
            }
        }
        ToastMaker(String.format("No %s found", username));
        return false;
    }

    private void ToastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }
}