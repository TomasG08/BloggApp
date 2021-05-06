package com.cursoandroid.blogapp.domain.home

import com.cursoandroid.blogapp.core.Result
import com.cursoandroid.blogapp.data.model.Post

interface HomeScreenRepo {
    suspend fun getLastestPosts(): Result<List<Post>>
}