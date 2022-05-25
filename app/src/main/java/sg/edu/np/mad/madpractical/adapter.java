package sg.edu.np.mad.madpractical;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder>{
    private ArrayList<User> userList;

    // -- Constructor (If want to make an instance of recycler, need to pass a list of users) --
    public adapter(ArrayList<User> userList){
        this.userList = userList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTxt;
        private TextView descriptionTxt;

        public MyViewHolder(final View view){
            super(view);
            nameTxt = view.findViewById(R.id.nameTxt);
            descriptionTxt = view.findViewById(R.id.descriptionTxt);
        }
    }

    @NonNull
    @Override
    public adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter.MyViewHolder holder, int position) {
        String name = userList.get(position).name;
        holder.nameTxt.setText("name-" + name);

        String description = userList.get(position).description;
        holder.descriptionTxt.setText("description: "+ description);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
