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

public class MathActivity extends AppCompatActivity {
    public static final String markMath="markMath";
    ImageView backHome;
    Button saveBtn;

    TextView question1, question2, question3;
    RadioGroup radioGroup1, radioGroup2, radioGroup3;

    int amount =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        Random rd = new Random();
        question1 = findViewById(R.id.rdQ1);
        question2 = findViewById(R.id.rdQ2);
        question3 = findViewById(R.id.rdQ3);

        radioGroup1=findViewById(R.id.currentGroup1);
        radioGroup2=findViewById(R.id.currentGroup2);
        radioGroup3=findViewById(R.id.currentGroup3);

        generateQuestion(question1,rd);
        generateQuestion(question2,rd);
        generateQuestion(question3,rd);

        backHome = findViewById(R.id.backHome1);
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mark1=checkAnswer(question1,radioGroup1);
                int mark2=checkAnswer(question2,radioGroup2);
                int mark3=checkAnswer(question3,radioGroup3);

                amount=mark1+mark2+mark3;

                Intent replyIntent = new Intent();
                replyIntent.putExtra(markMath,amount);
                setResult(RESULT_OK,replyIntent);

                // Finish this activity
                finish();
                // Start the main activity
                startActivity(new Intent(MathActivity.this, MainActivity.class));

            }

            private int checkAnswer(TextView question, RadioGroup radioGroup) {
                int correctAnswer = (int) question.getTag();

                int selectId = radioGroup.getCheckedRadioButtonId();
                if(selectId == -1){
                    Toast.makeText(MathActivity.this,"Please select an answer for "+question.getText(),Toast.LENGTH_SHORT).show();
                    return 0;
                }else{
                    RadioButton selectedRadioButton = findViewById(selectId);
                    boolean userSelectedTrue = selectedRadioButton.getText().toString().equals("True");

                    if(userSelectedTrue == (correctAnswer == 1)){
                        return 1;
                    }else{
                        return 0;
                    }
                }
            }
        });
}
    private void generateQuestion(TextView question, Random rd) {
        int operand1 = rd.nextInt(10);
        int operand2 = rd.nextInt(10);
        int operator = rd.nextInt(4);// 0: +, 1: -, 2: *, 3: /

        String operatorSymbol;
        int correctAnswer;

        switch (operator){
            case 0:
                operatorSymbol ="+";
                correctAnswer = operand1 +operand2;
                break;
            case 1:
                operatorSymbol = "-";
                correctAnswer = operand1 - operand2;
                break;
            case 2:
                operatorSymbol = "*";
                correctAnswer = operand1 * operand2;
                break;
            case 3:
                operatorSymbol= "/";
                if (operand2 ==0){
                    operand2++;
                }
                correctAnswer = operand1 / operand2;
                break;
            default:
                operatorSymbol = "+";
                correctAnswer = operand1 + operand2;
                break;
        }

        // Set the question with a possibly wrong answer
        int possibleWrongAnswer = correctAnswer + rd.nextInt(5) - 2; // Generate a number close to the correct answer
        question.setText(operand1 + " " + operatorSymbol + " " + operand2 + " = " + possibleWrongAnswer);

        // Set tag to indicate if the correct answer is true (1) or false (0)
        question.setTag(correctAnswer == possibleWrongAnswer ? 1 : 0);
    }
    }