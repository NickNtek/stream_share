package gr.nick.stream_service_share.RecycleAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gr.nick.stream_service_share.Models.User;
import gr.nick.stream_service_share.R;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    List<User> mUsers;

    private LayoutInflater mInflater;

    private ItemClickListener mItemClickListener;

    public UsersAdapter(Context context, List<User> users) {
        this.mInflater = LayoutInflater.from(context);
        this.mUsers = users;
    }

    public UsersAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setAllUsers(List<User> allUsers) {
        this.mUsers = allUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, int position) {
        User user = mUsers.get(position);
        String username = user.getUsername();
        holder.user.setText(username);
    }

    @Override
    public int getItemCount() {
        if (mUsers != null) {
            return mUsers.size();
        } else {
            return 0;
        }
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.RecyclerItemTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }



    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public User getItem(int id) {
        return mUsers.get(id);
    }

}
