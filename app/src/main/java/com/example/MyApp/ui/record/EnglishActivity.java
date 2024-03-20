package com.example.MyApp.ui.record;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MyApp.R;
import com.example.MyApp.model.Record;
import com.example.MyApp.ui.home.MainActivity;

import java.util.Random;

public class EnglishActivity extends AppCompatActivity {
    public static final String markEng = "markEng";
    Button saveBtn;
    ImageView backHome;
    TextView question1, question2, question3;
    RadioGroup radioGroup1, radioGroup2, radioGroup3;

    int amount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);



        Random rd = new Random();

        question1 = findViewById(R.id.rdEQ1);
        question2 = findViewById(R.id.rdEQ2);
        question3 = findViewById(R.id.rdEQ3);

        radioGroup1 = findViewById(R.id.currentGroupE1);
        radioGroup2 = findViewById(R.id.currentGroupE2);
        radioGroup3 = findViewById(R.id.currentGroupE3);

        generateQuestion(question1, rd);
        generateQuestion(question2, rd);
        generateQuestion(question3, rd);

        backHome = findViewById(R.id.backHome1);
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveBtn = findViewById(R.id.saveEBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int mark1 = checkAnswer(question1, radioGroup1);
                int mark2 = checkAnswer(question2, radioGroup2);
                int mark3 = checkAnswer(question3, radioGroup3);

                amount = mark1 + mark2 + mark3;

                Intent replyIntent = new Intent();
                replyIntent.putExtra(markEng, amount);
                setResult(RESULT_OK, replyIntent);

                // Finish this activity
                finish();

                // Start the main activity
                startActivity(new Intent(EnglishActivity.this, MainActivity.class));

            }
        });

    }

    private int checkAnswer(TextView question, RadioGroup radioGroup) {
        int correctAnswerIndex = (int) question.getTag();

        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(EnglishActivity.this, "Please select an answer for " + question.getText(), Toast.LENGTH_SHORT).show();
            return 0;
        } else {
            RadioButton selectedRadioButton = findViewById(selectedId);
            boolean userSelectedTrue = selectedRadioButton.getText().toString().equals("True");

            if((correctAnswerIndex == getColorIndex(question.getText().toString())) && userSelectedTrue){
                return 1;
            }else if((correctAnswerIndex != getColorIndex(question.getText().toString())) && !userSelectedTrue){
                return 1;
            }else{
                return 0;
            }
        }
    }
    private int getColorIndex(String color) {
        switch (color) {
            case "Red":
                return 0;
            case "Blue":
                return 1;
            case "Green":
                return 2;
            case "Yellow":
                return 3;
            case "Black":
                return 4;
            default:
                return -1;
        }
    }
    private void generateQuestion(TextView question, Random rd) {
        String[] colors = {"Red","Blue","Green","Yellow","Black"};
        int colorIndex = rd.nextInt(colors.length);
        String color = colors[colorIndex];

        question.setText(color);
        question.setTag(colorIndex);
    }
    }