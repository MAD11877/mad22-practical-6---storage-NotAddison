package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // -- Getting Data from ListActivity --
        Intent receive = getIntent();
        User user = (User) receive.getSerializableExtra("user");
        TextView header = findViewById(R.id.header);
        Log.v(TAG, "(ListActivity) Number: " + user);
        Log.v(TAG, "(ListActivity) isFollowed: " + user.isFollowed());
        header.setText("Name-" + user.getName());

        // -- Database
        DBHandler dbHandler = new DBHandler(this,null,null,1);

        // --- Previous Wk2 Codes ---
        // Get button(s)
        Button followButton = findViewById(R.id.followButton);
        Button messageButton = findViewById(R.id.messageButton);

        // Check if user is following
        if (user.isFollowed()){
            followButton.setText("Unfollow");
        }

        // Set onClickListener for Follow Button to update text
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.isFollowed()){
                    user.setFollowed(false);
                    followButton.setText("Follow");
                    Toast.makeText(getApplicationContext(), "Unfollowed", Toast.LENGTH_SHORT).show();
                } else {
                    user.setFollowed(true);
                    followButton.setText("Unfollow");
                    Toast.makeText(getApplicationContext(), "Followed", Toast.LENGTH_SHORT).show();
                }
                Log.v(TAG, "(ListActivity) isFollowed: " + user.isFollowed());
                dbHandler.updateUser(user); // Update User
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessageGroup.class);
                startActivity(intent);
            }
        });

    }
}