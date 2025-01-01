package com.shinjaehun.roomapp.fragments.list

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil3.Bitmap
import coil3.ImageLoader
import coil3.asDrawable
import coil3.imageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.target
import coil3.toBitmap
import com.shinjaehun.roomapp.R
import com.shinjaehun.roomapp.viewmodel.UserViewModel
import com.shinjaehun.roomapp.databinding.FragmentListBinding
import com.shinjaehun.roomapp.model.Address
import com.shinjaehun.roomapp.model.User
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var mUserViewModel : UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        val adapter = ListAdapter()
        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

//        lifecycleScope.launch {
//            val person = User(0, "Shin", "Jaehun", 49, Address("Aran 4gil", 41), getBitmap())
//            mUserViewModel.addUser(person)
//        }

        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer {users ->
            adapter.setData(users)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return binding.root
    }

    private suspend fun getBitmap(): Bitmap {
        lateinit var bitmap: Bitmap
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data("https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4")
            .target {
                bitmap = it.toBitmap()
            }
            .build()

        val result = (loading.execute(request) as SuccessResult)
//        return (result as BitmapDrawable).bitmap
        return bitmap
    }

}