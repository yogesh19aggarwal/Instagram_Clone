package com.example.instagramclone.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagramclone.Adapters.ReelAdapter
import com.example.instagramclone.Models.Reel
import com.example.instagramclone.databinding.FragmentReelBinding
import com.example.instagramclone.utils.REEL
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class ReelFragment : Fragment() {

    private lateinit var binding: FragmentReelBinding
    lateinit var adapter: ReelAdapter
    var reelList = ArrayList<Reel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReelBinding.inflate(layoutInflater, container, false)
        adapter = ReelAdapter(requireContext(), reelList)

        var viewPager = binding.viewPager

        viewPager.adapter = adapter

        Firebase.firestore.collection(REEL).get()
            .addOnSuccessListener {
                var tempList = ArrayList<Reel>()

                for(i in it.documents){
                    var reel:Reel = i.toObject<Reel>()!!
                    tempList.add(reel)
                }
                reelList.addAll(tempList)
                reelList.reverse()
                adapter.notifyDataSetChanged()
            }

        return binding.root
    }

    companion object {

    }
}