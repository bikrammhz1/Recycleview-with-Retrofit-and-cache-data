package com.rbmhz.projectassignment.callback;

import com.rbmhz.projectassignment.DTO.CommentDto;
import com.rbmhz.projectassignment.DTO.PostDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @author Bikram Maharjan
 * @version 1.1
 * @Date 11/22/2017.
 */

public interface ApiInterface   {
//
//    @GET("/posts")
//    Call<List<PostDto>> getPosts(@Query("id") String id);

    @GET
    Call<List<PostDto>>  getPosts(@Url String url);


    @GET
    Call<List<CommentDto>>  getComments(@Url String url);
}
