package com.example.instagramclone.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramclone.Post.PostActivity
import com.example.instagramclone.Post.ReelActivity
import com.example.instagramclone.databinding.FragmentAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(layoutInflater, container, false)

        binding.postAdd.setOnClickListener {
            val intent = Intent(requireContext(), PostActivity::class.java)
            activity?.startActivity(intent)
        }

        binding.reelAdd.setOnClickListener {
            val intent = Intent(requireContext(), ReelActivity::class.java)
            activity?.startActivity(intent)
        }

        return binding.root
    }

    companion object {

    }
}