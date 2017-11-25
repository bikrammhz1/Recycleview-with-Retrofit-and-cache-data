//package com.rbmhz.projectassignment;
//
//import android.app.ProgressDialog;
//import android.database.Cursor;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.rbmhz.projectassignment.DTO.CommentDto;
//import com.rbmhz.projectassignment.DTO.PostDto;
//import com.rbmhz.projectassignment.adapter.RecycleViewAdapter;
//import com.rbmhz.projectassignment.callback.ApiInterface;
//import com.rbmhz.projectassignment.callback.RestManager;
//import com.rbmhz.projectassignment.helper.DatabaseHelper;
//import com.rbmhz.projectassignment.helper.Utils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import static com.rbmhz.projectassignment.helper.Constants.HTTP.BASE_URL;
//
//public class MainActivity2 extends AppCompatActivity {
//
//
//    private static final String TAG = MainActivity2.class.getSimpleName();
//    private RecyclerView mRecyclerView;
//    private RecycleViewAdapter recycleViewAdapter;
//    private ProgressDialog mDialog;
//    private DatabaseHelper myDb;
//
//    private List<PostDto> postDtos = new ArrayList<>();
//    private List<CommentDto> commentDtos = new ArrayList<>();
//    private ApiInterface apiInterface;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        myDb = new DatabaseHelper(MainActivity2.this);
//        initilizationUI();
//        loadPostFeed();
//        myDb.close();
//    }
//
//    private void initilizationUI() {
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//
//
//    }
//
//    public boolean getNetworkAvailability() {
//        return Utils.isNetworkAvailable(getApplicationContext());
//    }
//
//
//    private void loadPostFeed() {
//
//        mDialog = new ProgressDialog(MainActivity2.this);
//        mDialog.setMessage("Loading Data...");
//        mDialog.setCancelable(true);
//        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        mDialog.setIndeterminate(true);
//
//        mDialog.show();
//
//        if (getNetworkAvailability()) {
//            getFeed();
//        } else {
//
//            mDialog.cancel();
//            mDialog.dismiss();
//            loadFromDb();
//            Toast.makeText(getApplicationContext(), "Error in network Connection ", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void getFeed() {
//        apiInterface = RestManager.getApiClient().create(ApiInterface.class);
//
//        Call<List<PostDto>> listCall = apiInterface.getPosts(BASE_URL);
//
//        listCall.enqueue(new Callback<List<PostDto>>() {
//            @Override
//            public void onResponse(Call<List<PostDto>> call, Response<List<PostDto>> response) {
//                mDialog.cancel();
//                mDialog.dismiss();
//                Log.d(TAG, "Total number of questions fetched : " + response.body());
//
//                postDtos = response.body();
//                String aa = String.valueOf(postDtos.size());
//                Log.d("asdfadsfaf", aa);
//                Log.d("asdfadsfaf", postDtos.toString());
//
//                recycleViewAdapter = new RecycleViewAdapter(postDtos, MainActivity2.this);
//                mRecyclerView.setAdapter(recycleViewAdapter);
//                myDb.deleteAll();
//                for (int i = 0; i < postDtos.size(); i++) {
//                    Log.d("asdfadsfaf", postDtos.get(i).getId());
//
//                    PostDto postDto = postDtos.get(i);
//                    SaveIntoDatabase task = new SaveIntoDatabase();
//                    task.execute(postDto);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<PostDto>> call, Throwable t) {
//                mDialog.cancel();
//                mDialog.dismiss();
//                Log.e(TAG, "Got error : " + t.getLocalizedMessage());
//                Toast.makeText(MainActivity2.this, "Error Data Loading...", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void loadFromDb() {
//        try {
//            Cursor cursor = myDb.getNames();
//            if (cursor.moveToFirst()) {
//                do {
//                    PostDto getterSetter = new PostDto();
//                    String id = cursor.getString(0);
//                    String title = cursor.getString(2);
//                    String body = cursor.getString(3);
//                    getterSetter.setId(id);
//                    getterSetter.setTitle(title);
//                    getterSetter.setBody(body);
//                    postDtos.add(getterSetter);
//
//                    Log.d("asdfasdf-id", id);
//                } while (cursor.moveToNext());
//            }
//            recycleViewAdapter = new RecycleViewAdapter(postDtos, MainActivity2.this);
//            mRecyclerView.setAdapter(recycleViewAdapter);
//
//        } catch (Exception e) {
//            Log.d("Error==>Db ", e.toString());
//        }
//    }
//
//
//    public class SaveIntoDatabase extends AsyncTask<PostDto, Void, Void> {
//
//
//        private final String TAG = SaveIntoDatabase.class.getSimpleName();
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(PostDto... params) {
//
//            PostDto postDto = params[0];
//
//            try {
//
//                myDb.addPost(postDto);
//
//            } catch (Exception e) {
//                Log.d(TAG, e.getMessage());
//            }
//            try {
//                String url = BASE_URL + postDto.getId() + "/comments";
//                Log.d("urlllllll====", url);
//
//                getFeedComment(url);
//
//
//            } catch (Exception e) {
//
//                Log.d("Erroorrrr==>", e.getMessage());
//            }
//            return null;
//        }
//    }
//
//
//    public class SaveIntoDatabaseComment extends AsyncTask<CommentDto, Void, Void> {
//
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(CommentDto... params) {
//            CommentDto commentDto = params[0];
//            String tableName = "Comment"+commentDto.getPostId();
//            Log.d("comment==->postId",tableName);
//            Log.d("comment==>dto",commentDto.getEmail());
//
//            try {
//
//                myDb.CreateDynamicTables(tableName,commentDto);
//
//            } catch (Exception e) {
//                Log.d(TAG, e.getMessage());
//            }
//
//            return null;
//        }
//    }
//
//
//
//    public void getFeedComment(String commentURL) {
//        apiInterface = RestManager.getApiClient().create(ApiInterface.class);
//
//
//        Call<List<CommentDto>> listCall = apiInterface.getComments(commentURL);
//
//        listCall.enqueue(new Callback<List<CommentDto>>() {
//            @Override
//            public void onResponse(Call<List<CommentDto>> call, Response<List<CommentDto>> response) {
//                mDialog.cancel();
//                mDialog.dismiss();
//                Log.d(TAG, "Total number of questions fetched : " + response.body());
//
//                commentDtos = response.body();
//                String aa = String.valueOf(commentDtos.size());
//                for (int i = 0; i < commentDtos.size(); i++) {
//
//                    Log.d("asdfadsfaf_comm-id", commentDtos.get(i).getId());
//                    Log.d("asdfadsfaf_comm-postId", commentDtos.get(i).getPostId());
//                    Log.d("asdfadsfaf_comm-email", commentDtos.get(i).getEmail());
//                    Log.d("asdfadsfaf_comm-name", commentDtos.get(i).getName());
//                    Log.d("asdfadsfaf_comm-body", commentDtos.get(i).getBody());
//
//                    CommentDto commentDto = commentDtos.get(i);
//                    SaveIntoDatabaseComment task = new SaveIntoDatabaseComment();
//                    task.execute(commentDto);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<CommentDto>> call, Throwable t) {
//                mDialog.cancel();
//                mDialog.dismiss();
//                Log.d("Error==> " , t.getLocalizedMessage());
//            }
//        });
//    }
//
//}
