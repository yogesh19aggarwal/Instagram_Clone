package com.example.instagramclone.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.instagramclone.Adapters.viewPagerAdapter
import com.example.instagramclone.Models.User
import com.example.instagramclone.SignUpActivity
import com.example.instagramclone.databinding.FragmentProfileBinding
import com.example.instagramclone.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private lateinit var viewPagerAdapter1: viewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        binding.editProfile.setOnClickListener {
            val intent = Intent(activity, SignUpActivity::class.java)
            intent.putExtra("Mode", 1)
            activity?.startActivity(intent)
            activity?.finish()
        }

        viewPagerAdapter1 =  viewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter1.addFragments(MyPostFragment(), "My Posts")
        viewPagerAdapter1.addFragments(MyReelsFragment(), "My Reels")

        binding.viewPager.adapter =  viewPagerAdapter1
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
//
                val user: User = it.toObject<User>()!!
                binding.name.text = user.name
                    binding.bio.text = user.email


                if(!user.image.isNullOrBlank()){
                    Picasso.get().load(user.image).into(binding.imageProfile)
                }
        }
    }
}