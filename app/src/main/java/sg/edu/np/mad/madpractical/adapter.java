package sg.edu.np.mad.madpractical;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder>{
    private ArrayList<User> userList;
    Context context;

    // -- Constructor (If want to make an instance of recycler, need to pass a list of users) --
    public adapter(Context context,ArrayList<User> userList){
        this.userList = userList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTxt;
        private TextView descriptionTxt;
        private ImageView image;

        public MyViewHolder(final View view){
            super(view);
            nameTxt = view.findViewById(R.id.nameTxt);
            descriptionTxt = view.findViewById(R.id.descriptionTxt);
            image = view.findViewById(R.id.imageView5);
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        String name = userList.get(position).getName();
        String lastNumber = name.substring(name.length()-1);
        if (lastNumber.equals("7"))
        {
            return 1;
        }
        return 0;
    }

    @NonNull
    @Override
    public adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = null;
        if (viewType == 1)
        {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_alt, parent, false);
        }
        else{
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);
        }


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter.MyViewHolder holder, int position) {
        User user = userList.get(position);

        String name = userList.get(position).name;
        holder.nameTxt.setText("name-" + name);

        String description = userList.get(position).description;
        holder.descriptionTxt.setText("description: "+ description);

        ImageView icon = holder.image;
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Profile");
                builder.setMessage(name);
                builder.setCancelable(true);

                builder.setPositiveButton("View", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent mainActivity = new Intent(context, MainActivity.class);
                        mainActivity.putExtra("user", user);    // Class must implement serializable
                        context.startActivity(mainActivity);
                    }
                });
                builder.setNegativeButton("Close", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // Do something else...
                    }
                });
                AlertDialog imageClick = builder.create();
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
