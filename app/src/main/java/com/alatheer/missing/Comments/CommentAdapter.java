package com.alatheer.missing.Comments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alatheer.missing.Data.Remote.Comment.Comment;
import com.alatheer.missing.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    List<Comment>commentList;
    Context context;

    public CommentAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.comment_item,parent,false);
        return new CommentHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
       holder.setData(commentList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.user_name)
        TextView user_name;
        @BindView(R.id.comment_txt)
        TextView comment_txt;
        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(Comment comment) {
            user_name.setText(comment.getCommentName());
            comment_txt.setText(comment.getComment());
        }
    }
}
