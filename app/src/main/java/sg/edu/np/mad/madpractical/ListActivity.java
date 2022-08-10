package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class ListActivity extends AppCompatActivity {
    private static final String TAG = "ListActivity";
    private ImageView img;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //img = (ImageView) findViewById(R.id.imageLogo);
        DBHandler dbHandler = new DBHandler(this,null,null,1);

        // -- On Image click --
        //ImageClick();

        // --- Generate 20 Random Users  ---
        //ArrayList<User> userList = new ArrayList<User>();
        //userList = GenerateUsers(20,dbHandler);
        Log.v(TAG, String.valueOf(dbHandler.getUser().size()));

        for (User user:dbHandler.getUser()){
            Log.v(TAG,"" + user.getName());
        }

        // --- Setup Adapter ---
        RecyclerAdapterSetup(dbHandler.getUser());
    }

    public void ImageClick(){
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "Clicked on the image");

                // Defining Content in Alert Dialogue
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("Profile");
                builder.setMessage("MADNess");
                builder.setCancelable(false);
                builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.v(TAG, "Clicked on View");
                        // --- Generating Random Number ---
                        Random random = new Random();
                        int randomNumber = random.nextInt();
                        while (randomNumber < 0) {               // Prevent Negative Numbers
                            randomNumber = random.nextInt();
                        }
                        Log.v(TAG, "randomNumber: " + randomNumber);

                        Intent intent = new Intent(ListActivity.this, MainActivity.class);
                        intent.putExtra("Number", String.valueOf(randomNumber));  // Must pass a string value (Not Int)
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.v(TAG, "Clicked on Close");
                    }
                });

                AlertDialog alert = builder.create();   // Create the Alert Dialogue
                alert.show();                           // Show the Alert Dialogue
            }
        });
    }
    public ArrayList<User> GenerateUsers(int number, DBHandler dbHandler){
        Random rand = new Random();
        ArrayList<Boolean> boolList = new ArrayList<Boolean>();
        boolList.add(true);
        boolList.add(false);

        ArrayList<User> userList = new ArrayList<User>();

        for (int i = 0; i < number; i++) {
            String name = String.valueOf(rand.nextInt(100000));
            String description = String.valueOf(rand.nextInt(100000));
            boolean followed = boolList.get(rand.nextInt(2));

            User user = new User(name, description, i, followed);
            userList.add(user);
            dbHandler.addUser(user);
            Log.v(TAG, "Bool :"+ followed + " Name :"+ user.name);
        }
        return userList;
    }
    public void RecyclerAdapterSetup(ArrayList<User> userList ){
        recyclerview = findViewById(R.id.recycler);
        adapter adapter = new adapter(ListActivity.this,userList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(adapter);
    }
}