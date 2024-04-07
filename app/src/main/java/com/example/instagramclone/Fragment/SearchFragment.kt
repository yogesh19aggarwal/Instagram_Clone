package com.example.instagramclone.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.Adapters.SearchAdapter
import com.example.instagramclone.Models.User
import com.example.instagramclone.R
import com.example.instagramclone.databinding.FragmentSearchBinding
import com.example.instagramclone.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    lateinit var adapter: SearchAdapter
    var userList = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        adapter = SearchAdapter(requireContext(), userList)

        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter


        Firebase.firestore.collection(USER_NODE).get()
            .addOnSuccessListener {
                var tempList = ArrayList<User>()
                userList.clear()

                for(i in it.documents){
                    if(i.id.toString() != Firebase.auth.currentUser!!.uid.toString()){
                        var user:User = i.toObject<User>()!!
                        tempList.add(user)
                    }
                }

                userList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }

        var search = binding.imageButton
        search.setOnClickListener {

            var text = binding.searchView.text.toString()
            Firebase.firestore.collection(USER_NODE).whereEqualTo("name", text).get()
                .addOnSuccessListener {
                    var tempList = ArrayList<User>()
                    userList.clear()

                    if(!it.isEmpty){
                        for(i in it.documents){
                            if(i.id.toString() != Firebase.auth.currentUser!!.uid.toString()){
                                var user:User = i.toObject<User>()!!
                                tempList.add(user)
                            }
                        }

                        userList.addAll(tempList)
                        adapter.notifyDataSetChanged()
                    }
                }
        }

        return binding.root
    }

    companion object
}