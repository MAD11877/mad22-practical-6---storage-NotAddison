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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User user1 = new User("Addison", "Y2 IT Student", 1, false);

        // -- Getting Data from ListActivity --
        Intent receive = getIntent();
        String randNum = receive.getStringExtra("Number");
        Log.v(TAG, "(ListActivity) Number: " + randNum);
        TextView header = findViewById(R.id.header);
        header.setText("MAD: " + randNum);


        // --- Previous Wk2 Codes ---
        // Get button(s)
        Button followButton = findViewById(R.id.followButton);
        Button messageButton = findViewById(R.id.messageButton);

        // Check if user is following
        if (user1.followed){
            followButton.setText("Unfollow");
        }

        // Set onClickListener for Follow Button to update text
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user1.followed){
                    user1.followed = false;
                    followButton.setText("Follow");
                    Toast.makeText(getApplicationContext(), "Unfollowed", Toast.LENGTH_SHORT).show();
                } else {
                    user1.followed = true;
                    followButton.setText("Unfollow");
                    Toast.makeText(getApplicationContext(), "Followed", Toast.LENGTH_SHORT).show();
                }
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