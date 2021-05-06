package com.cursoandroid.blogapp.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.cursoandroid.blogapp.R
import com.cursoandroid.blogapp.core.Result
import com.cursoandroid.blogapp.data.remote.home.HomeScreenDataSource
import com.cursoandroid.blogapp.databinding.FragmentHomeScreenBinding
import com.cursoandroid.blogapp.domain.home.HomeScreenRepoImpl
import com.cursoandroid.blogapp.presentation.HomeScreenViewModel
import com.cursoandroid.blogapp.presentation.HomeScreenViewModelFactory
import com.cursoandroid.blogapp.ui.home.adapter.HomeScreenAdapter
import java.lang.RuntimeException

class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModels<HomeScreenViewModel> {
        HomeScreenViewModelFactory(
            HomeScreenRepoImpl(
                HomeScreenDataSource()
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        //val current = LocalDateTime.now()
        //val fomatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss")
        //val formatted = current.format(fomatter)

        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        viewModel.fetchLatestPosts().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerViewHome.adapter = HomeScreenAdapter(result.data)
                    throw RuntimeException("Crashlytics test")
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Ocurrió un error : ${result.exception}",
                        Toast.LENGTH_LONG
                    ).show()
                    print("Ocurrió un error: ${result.exception}")
                    Log.d("ErrorFB", "${result.exception}")
                }
            }

        })
    }
}