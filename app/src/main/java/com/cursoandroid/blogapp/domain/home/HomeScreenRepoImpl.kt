package com.cursoandroid.blogapp.domain.home

import com.cursoandroid.blogapp.core.Result
import com.cursoandroid.blogapp.data.model.Post
import com.cursoandroid.blogapp.data.remote.home.HomeScreenDataSource

class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource) : HomeScreenRepo {

    override suspend fun getLastestPosts(): Result<List<Post>> = dataSource.getLatestPosts()
}

