package com.example.hangmangame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hangmangame.databinding.ActivityGameBinding;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    ActivityGameBinding binding;
    String words[] = {};
    String gen_word,cur_word;
    Random rand;
    String[] Status;
    int Status_num = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Resources res = getResources();
        Status = res.getStringArray(R.array.Status);
        words = res.getStringArray(R.array.words);
        rand = new Random();
        binding.userInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if(validateInput()){
                        String curr_char = binding.userInput.getText().toString();
                        Toast.makeText(GameActivity.this, curr_char, Toast.LENGTH_SHORT).show();
                        if(check_char(curr_char)) {
                            Toast.makeText(GameActivity.this, "You guessed it right!!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Status_num++;
                            String x = Status[Status_num];
                            ImageView iv= (ImageView)findViewById(R.id.hangman_img);
                            iv.setImageResource(getResources().getIdentifier(x, "drawable", getPackageName()));
                        }
                    }
                    return true;
                }
                return false;
            }
        });
        binding.wordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cur_word="";
                gen_word = words[rand.nextInt(words.length)];
                Toast.makeText(getBaseContext(), gen_word, Toast.LENGTH_SHORT).show();
                for(int i=0;i<gen_word.length();i++) {
                    cur_word += "-";
                }
                binding.space.setText(cur_word);
            }
        });
    }
    public boolean validateInput() {
        String var = binding.userInput.getText().toString().trim();
        if(var.length() > 1) {
            Toast.makeText(this, "Enter Single Character", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(var.isEmpty()) {
            Toast.makeText(this, "Please enter a character", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!(var.matches("[a-zA-Z]+"))) {
            Toast.makeText(this, "Please enter only aplhabets", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean check_char(String s) {
        boolean flag = false;
        for(int i=0;i<gen_word.length();i++) {
            if(gen_word.charAt(i) == s.charAt(0)) {
                StringBuilder pos = new StringBuilder(cur_word);
                pos.setCharAt(i,s.charAt(0));
                flag = true;
            }
        }
        if(flag==true)
            return true;
        return false;
    }
}
