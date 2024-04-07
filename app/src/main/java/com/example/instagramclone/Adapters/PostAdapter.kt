package com.example.instagramclone.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.Models.Post
import com.example.instagramclone.Models.User
import com.example.instagramclone.R
import com.example.instagramclone.databinding.PostRvBinding
import com.example.instagramclone.utils.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class PostAdapter(var context: Context, var postList: ArrayList<Post>): RecyclerView.Adapter<PostAdapter.MyHolder>() {

    inner class MyHolder(var binding: PostRvBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = PostRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {

            try{
                Firebase.firestore.collection(USER_NODE).document(postList[position].uid).get().addOnSuccessListener {

                    var user = it.toObject<User>()
                    Glide.with(context).load(user!!.image).placeholder(R.drawable.profile).into(holder.binding.imageProfile)
                    holder.binding.name.text = user.name
                }
            }catch (e:Exception){

            }


            Glide.with(context).load(postList[position].postUrl).placeholder(R.drawable.loading).into(holder.binding.postImage)

            try {
                val text: String = TimeAgo.using(postList[position].time.toLong())
                holder.binding.time.text = text
            }catch (e:Exception){
                holder.binding.time.text = null
            }

            holder.binding.caption.text = postList[position].caption

            var flag: Boolean = true
            holder.binding.like.setOnClickListener {
                if(flag){
                    holder.binding.like.setImageResource(R.drawable.red_like)
                    flag = false
                }else{
                    holder.binding.like.setImageResource(R.drawable.like)
                    flag = true
                }
            }

            holder.binding.share.setOnClickListener {
                var intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, postList[position].postUrl)
                context.startActivity(intent)
            }

            holder.binding.save.setOnClickListener {

            }
        }
}