package sg.edu.np.mad.madpractical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button LoginButton = findViewById(R.id.LoginButton);
        TextView Username = findViewById(R.id.Username);
        TextView Password = findViewById(R.id.Password);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mad-practical-b89a6-default-rtdb.asia-southeast1.firebasedatabase.app/");


        // Login button onclick
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read from firebase
                String username = Username.getText().toString();
                String password = Password.getText().toString();

                Log.v(TAG, "Username: " + username);
                Log.v(TAG, "Password: " + password);
                DatabaseReference ref = database.getReference("Users");

                // Read data from firebase DB
                ref.child("mad").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String FBusername = dataSnapshot.child("username").getValue().toString();
                        String FBpassword = dataSnapshot.child("password").getValue().toString();

                        if (username.equals(FBusername) && password.equals(FBpassword)) {
                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, ListActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.v(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });

    }
}