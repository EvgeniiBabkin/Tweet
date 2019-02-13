package com.example.tweet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tweet.R;
import com.example.tweet.pojo.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UsersAdapter extends  RecyclerView.Adapter<UsersAdapter.UsersViewHolder>{


    private List<User> userList = new ArrayList<>();

    private OnUserClickListener onUserClickListener;

    public UsersAdapter(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_view, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, int position) {

        holder.bind(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setItems(Collection<User> users){
        userList.addAll(users);
        notifyDataSetChanged();
    }

    public void clearItem(){
        userList.clear();
        notifyDataSetChanged();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder{

        private ImageView userImageView;
        private TextView nameTextView;
        private TextView nickTextView;


        public UsersViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.user_name_text_view);
            nickTextView = itemView.findViewById(R.id.user_nick_text_view);
            userImageView = itemView.findViewById(R.id.profile_image_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    User user = userList.get(getLayoutPosition());
                    onUserClickListener.onUserClick(user);
                }
            });

        }

        public void bind(User user){

            nameTextView.setText(user.getName());
            nickTextView.setText(user.getNick());

            Picasso.get().load(user.getImageUrl()).into(userImageView);
        }


    }

    public interface OnUserClickListener{
        void onUserClick(User user);
    }
}
