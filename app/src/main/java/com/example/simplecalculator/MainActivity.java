package com.example.simplecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.FirebaseApp;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;
    ShapeableImageView imageView;
    TextView name, mail;
    EditText et1, et2;
    TextView tvResult;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        try {
                            GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                            AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                            auth.signInWithCredential(authCredential)
                                    .addOnCompleteListener(MainActivity.this, task -> {
                                        if (task.isSuccessful()) {
                                            Glide.with(MainActivity.this).load(auth.getCurrentUser().getPhotoUrl()).into(imageView);
                                            name.setText(auth.getCurrentUser().getDisplayName());
                                            mail.setText(auth.getCurrentUser().getEmail());
                                            Toast.makeText(MainActivity.this, "Signed in successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(MainActivity.this, "Failed to sign in: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } catch (ApiException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        imageView = findViewById(R.id.profileImage);
        name = findViewById(R.id.nameTV);
        mail = findViewById(R.id.mailTV);
        et1 = findViewById(R.id.Num1);
        et2 = findViewById(R.id.Num2);
        //1tvResult = findViewById(R.id.tvResult);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();


        googleSignInClient = GoogleSignIn.getClient(MainActivity.this, googleSignInOptions);
        auth = FirebaseAuth.getInstance();

        SignInButton signInButton = findViewById(R.id.signIn);
        signInButton.setOnClickListener(view -> {
            Intent intent = googleSignInClient.getSignInIntent();
            activityResultLauncher.launch(intent);
        });
    }

    public void onBtnClicked(View view) {
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
        } else if (view.getId() == R.id.btnMinus) {
            result = num1 - num2;
        } else if (view.getId() == R.id.btnMult) {
            result = num1 * num2;
        } else if (view.getId() == R.id.btnDiv) {
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
