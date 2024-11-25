package com.example.simplecalculator;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

// Get view handle using the view id
            EditText et1 = findViewById(R.id.Num1);

// Get the view’s text
            String et1Text = et1.getText().toString();

// Cast the String into Integer
            Integer num1 = Integer.valueOf(et1Text);
            Integer result = null;
            if (view.getId() == R.id.btnPlus)
                result = num1 + num2;
            if (view.getId() == R.id.btnMinus)
                result = num1 - num2;
            if (view.getId() == R.id.btnMult)
                result = num1 * num2;
            if (view.getId() == R.id.btnDiv)
                result = num1 / num2;

            if (result != null) {
                TextView tvRes = findViewById(R.id.tvResult);
                tvRes.setText(result.toString());
            }
            Toast.makeText(this, "Cant divide by 0",Toast.LENGTH_LONG).show();
        });
    }
}