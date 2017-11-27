package com.rbmhz.projectassignment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rbmhz.projectassignment.DTO.CommentDto;
import com.rbmhz.projectassignment.DTO.PostDto;
import com.rbmhz.projectassignment.adapter.RecycleViewAdapter;
import com.rbmhz.projectassignment.adapter.RecycleViewCommentAdapter;
import com.rbmhz.projectassignment.callback.ApiInterface;
import com.rbmhz.projectassignment.callback.RestManager;
import com.rbmhz.projectassignment.helper.DatabaseHelper;
import com.rbmhz.projectassignment.helper.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rbmhz.projectassignment.helper.Constants.HTTP.BASE_URL;

public class DetailViews extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    private static final String TAG = DetailViews.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeComment;
    private TextView tvPostTitle;
    private TextView tvPostBody;

    private RecycleViewCommentAdapter recycleViewCommentAdapter;
    private DatabaseHelper myDb;

    private ApiInterface apiInterface;

    private List<CommentDto> commentDtos = new ArrayList<>();
    private String postId;
    private String postTitle;
    private String postBody;
    private String getUrl;
    private LinearLayout lnConnectionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_showcase_main);
        myDb = new DatabaseHelper(DetailViews.this);
        initilizationUI();
        getIntentData();
//        loadPostFeed();
        Refresh();
        myDb.close();
    }

    private void getIntentData() {

        Intent i = getIntent();
        postId = i.getStringExtra("postId");
        postTitle = i.getStringExtra("postTitle");
        postBody = i.getStringExtra("postBody");
        getUrl = BASE_URL + postId + "/comments";
        Log.d("rv_url-data", getUrl);
    }

    private void initilizationUI() {
        tvPostTitle = (TextView) findViewById(R.id.tv_title_post);
        tvPostBody = (TextView) findViewById(R.id.tv_body_post);
        lnConnectionStatus = (LinearLayout) findViewById(R.id.ln_connection);
        swipeComment = (SwipeRefreshLayout) findViewById(R.id.swipe_comment);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


    }

    public boolean getNetworkAvailability() {
        return Utils.isNetworkAvailable(getApplicationContext());
    }


    private void loadPostFeed() {
        tvPostTitle.setText(postTitle);
        tvPostBody.setText(postBody);

        if (getNetworkAvailability()) {
            getFeed();
        } else {
            loadFromDb();
            Toast.makeText(getApplicationContext(), "Cache Data Loading......", Toast.LENGTH_SHORT).show();
        }
    }

    public void getFeed() {

        apiInterface = RestManager.getApiClient().create(ApiInterface.class);

        commentDtos.clear();
        Call<List<CommentDto>> listCall = apiInterface.getComments(getUrl);

        listCall.enqueue(new Callback<List<CommentDto>>() {
            @Override
            public void onResponse(Call<List<CommentDto>> call, Response<List<CommentDto>> response) {
                swipeComment.setRefreshing(false);
                Log.d(TAG, "Total number of questions fetched : " + response.body());
                lnConnectionStatus.setVisibility(View.GONE);

                commentDtos = response.body();
                String aa = String.valueOf(commentDtos.size());
                Log.d("asdfadsfaf", aa);
                Log.d("asdfadsfaf", commentDtos.toString());

                recycleViewCommentAdapter = new RecycleViewCommentAdapter(commentDtos, DetailViews.this);
                mRecyclerView.setAdapter(recycleViewCommentAdapter);

            }

            @Override
            public void onFailure(Call<List<CommentDto>> call, Throwable t) {
                swipeComment.setRefreshing(false);
                Log.e(TAG, "Got error : " + t.getLocalizedMessage());
                Toast.makeText(DetailViews.this, "Error Data Loading...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFromDb() {
        try {

            swipeComment.setRefreshing(false);
            Cursor cursor = myDb.getNamesComment();
            if (cursor.moveToFirst()) {
                do {
                    CommentDto getterSetter = new CommentDto();
                    String idget = cursor.getString(0);
                    String postid = cursor.getString(1);
                    String name = cursor.getString(2);
                    String email = cursor.getString(3);
                    String body = cursor.getString(4);

                    getterSetter.setId(idget);
                    getterSetter.setName(name);
                    getterSetter.setEmail(email);
                    getterSetter.setBody(body);

                    if (postid.equals(postId)) {
                        commentDtos.add(getterSetter);
                        lnConnectionStatus.setVisibility(View.GONE);

                    } else if (commentDtos.size() == 0) {
                        lnConnectionStatus.setVisibility(View.VISIBLE);

                    }
                } while (cursor.moveToNext());
            }

            recycleViewCommentAdapter = new RecycleViewCommentAdapter(commentDtos, DetailViews.this);
            mRecyclerView.setAdapter(recycleViewCommentAdapter);


        } catch (Exception e) {
            swipeComment.setRefreshing(false);
            Log.e("Errordatabase==>Db ", e.toString());
        }
    }

    public void Refresh() {
        swipeComment.setOnRefreshListener(DetailViews.this);
        swipeComment.post(new Runnable() {
                              @Override
                              public void run() {
                                  swipeComment.setRefreshing(true);
                                  loadPostFeed();
                              }
                          }
        );

    }


    @Override
    public void onRefresh() {
        swipeComment.setOnRefreshListener(DetailViews.this);
        swipeComment.post(new Runnable() {
                              @Override
                              public void run() {
                                  swipeComment.setRefreshing(true);
                                  loadPostFeed();
                              }
                          }
        );
    }
}
