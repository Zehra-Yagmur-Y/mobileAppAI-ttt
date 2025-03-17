package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tictactoe.data.UserAdapter;
//import com.example.tictactoe.UserViewModel;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String selectedDifficulty = "Kolay"; // Varsayılan zorluk seviyesi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner difficultySpinner = findViewById(R.id.difficultySpinner);

        // Spinner için adapter oluştur
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.difficulty_levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(adapter);

        // Kullanıcı seçim yaparsa zorluk seviyesini kaydet
        difficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDifficulty = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Boş seçim yapılırsa varsayılan "Kolay" olarak kalır
            }
        });
    }

    // Oyuna Başla Butonu
    public void startGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("difficulty", selectedDifficulty); // Seçilen zorluk seviyesini GameActivity'ye gönder
        startActivity(intent);
    }
}

