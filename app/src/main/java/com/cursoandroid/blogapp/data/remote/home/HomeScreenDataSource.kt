package com.cursoandroid.blogapp.data.remote.home

import com.cursoandroid.blogapp.core.Result
import com.cursoandroid.blogapp.data.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource {

    suspend fun getLatestPosts(): Result<List<Post>> {
        val postList = mutableListOf<Post>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("posts").get().await()
        for (post in querySnapshot.documents) {
            post.toObject(Post::class.java)?.let {
                postList.add(it)
            }
        }
        return Result.Success(postList)
    }
}