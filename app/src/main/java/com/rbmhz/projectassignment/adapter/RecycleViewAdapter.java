package com.rbmhz.projectassignment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rbmhz.projectassignment.DTO.PostDto;
import com.rbmhz.projectassignment.DetailViews;
import com.rbmhz.projectassignment.R;

import java.util.List;

/**
 * @author Bikram Maharjan
 * @version 1.1
 * @Date 11/22/2017.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter <RecycleViewAdapter.MyViewHolder>{

    private List<PostDto> postDtos;
    private Context c;

    public RecycleViewAdapter(List<PostDto> postDtos, Context c){
        this.postDtos = postDtos;
        this.c = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.id.setText(postDtos.get(position).getId()+")");
        holder.title.setText(postDtos.get(position).getTitle());
        holder.body.setText(postDtos.get(position).getBody());
        holder.lnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(c,""+position,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(c, DetailViews.class);
                i.putExtra("postId",postDtos.get(position).getId());
                i.putExtra("postTitle",postDtos.get(position).getTitle());
                i.putExtra("postBody",postDtos.get(position).getBody());
                c.startActivity(i);
            }
        });


    }


    @Override
    public int getItemCount() {
        return postDtos.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id;
        TextView title;
        TextView body;
        LinearLayout lnPost;
        public MyViewHolder(View itemView) {
            super(itemView);
            id = (TextView)itemView.findViewById(R.id.tv_id);
            title = (TextView)itemView.findViewById(R.id.tv_title);
            body = (TextView)itemView.findViewById(R.id.tv_body);
            lnPost = (LinearLayout) itemView.findViewById(R.id.ln_post);
        }
    }
}
