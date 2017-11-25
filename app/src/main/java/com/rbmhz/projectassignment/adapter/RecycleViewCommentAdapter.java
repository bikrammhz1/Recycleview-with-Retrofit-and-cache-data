package com.rbmhz.projectassignment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rbmhz.projectassignment.DTO.CommentDto;
import com.rbmhz.projectassignment.R;

import java.util.List;

/**
 * @author Bikram Maharjan
 * @version 1.1
 * @Date 11/22/2017.
 */

public class RecycleViewCommentAdapter extends RecyclerView.Adapter <RecycleViewCommentAdapter.MyViewHolder>{

    private List<CommentDto> commentDtos;
    private Context c;

    public RecycleViewCommentAdapter(List<CommentDto> commentDtos, Context c){
        this.commentDtos = commentDtos;
        this.c = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_name.setText(commentDtos.get(position).getName());
        holder.tv_id.setText(commentDtos.get(position).getId()+")");
        holder.tv_email.setText(commentDtos.get(position).getEmail());
        holder.body.setText(commentDtos.get(position).getBody());
        holder.lnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,""+position,Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return commentDtos.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name;
        TextView tv_email;
        TextView tv_id;
        TextView body;
        LinearLayout lnComment;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView)itemView.findViewById(R.id.tv_name);
            tv_id = (TextView)itemView.findViewById(R.id.tv_id);
            tv_email = (TextView)itemView.findViewById(R.id.tv_email);
            body = (TextView)itemView.findViewById(R.id.tv_body);
            lnComment = (LinearLayout) itemView.findViewById(R.id.ln_comment);
        }
    }
}
