package com.example.MyApp.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.MyApp.R;
import com.example.MyApp.ui.record.MathActivity;
import com.example.MyApp.ui.record.EnglishActivity;
import com.example.MyApp.ui.record.RecordViewModel;
import com.example.MyApp.model.Record;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    TextView amountMath,amountEng;
    BottomNavigationView bottomBarView;

    public static final int NEW_RECORD_ACTIVITY_REQUEST_CODE = 1;
    private RecordViewModel recordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountMath=findViewById(R.id.amountMath);
        amountEng=findViewById(R.id.amountEng);

        bottomBarView = findViewById(R.id.bottomBarView);
        bottomBarView.setOnNavigationItemSelectedListener(navListener);

        //it will sum all the total star of math
        //it will sum all the total star of english subject

        //Setup viewModel
        recordViewModel = new ViewModelProvider(this).get(RecordViewModel.class);
        }

        protected void onResume(){
        super.onResume();
        updateStarAmounts();
        }

    private void updateStarAmounts() {
        int totalMathStars = recordViewModel.getTotalMathStars();
        int totalEngStars = recordViewModel.getTotalEngStars();

        amountMath.setText(String.valueOf(totalMathStars));
        amountEng.setText(String.valueOf(totalEngStars));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_RECORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                int mathStars = data.getIntExtra(MathActivity.markMath,0);
                int engStars = data.getIntExtra(EnglishActivity.markEng,0);
                Record record = new Record(mathStars, engStars);
                recordViewModel.insert(record);

                // Update UI after adding the new record
                updateStarAmounts();
            }
        }
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.math:
                    Intent intent = new Intent(getApplicationContext(), MathActivity.class);
                    startActivityForResult(intent, NEW_RECORD_ACTIVITY_REQUEST_CODE);
                    overridePendingTransition(0, 0);
                    break;
                case R.id.eng:
                    startActivity(new Intent(getApplicationContext(), EnglishActivity.class));
                    break;
                default:
                    return false;
            }
            return true;
        }
    };
}