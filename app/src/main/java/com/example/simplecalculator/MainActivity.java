package com.example.simplecalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText et1, et2;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.Num1);
        et2 = findViewById(R.id.Num2);
        tvResult = findViewById(R.id.tvResult);
    }


    public void onBtnClicked(View view) {
        // קבלת הערכים מה-EditText
        String et1Text = et1.getText().toString();
        String et2Text = et2.getText().toString();


        if (et1Text.isEmpty() || et2Text.isEmpty()) {
            Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
            return;
        }


        Integer num1 = Integer.parseInt(et1Text);
        Integer num2 = Integer.parseInt(et2Text);
        Integer result = null;


        if (view.getId() == R.id.btnPlus) {
            result = num1 + num2;
        }
        if (view.getId() == R.id.btnMinus) {
            result = num1 - num2;
        }
        if (view.getId() == R.id.btnMult) {
            result = num1 * num2;
        }
        if (view.getId() == R.id.btnDiv) {
            if (num2 == 0) {
                Toast.makeText(this, "Can't divide by 0", Toast.LENGTH_LONG).show();
                return;
            }
            result = num1 / num2;
        }


        if (result != null) {
            tvResult.setText(result.toString());
        }
    }
}
