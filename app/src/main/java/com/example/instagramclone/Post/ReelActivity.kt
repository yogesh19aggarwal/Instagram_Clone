package com.example.instagramclone.Post

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagramclone.MainActivity
import com.example.instagramclone.Models.Reel
import com.example.instagramclone.Models.User
import com.example.instagramclone.R
import com.example.instagramclone.databinding.ActivityReelBinding
import com.example.instagramclone.utils.REEL
import com.example.instagramclone.utils.REEL_FOLDER
import com.example.instagramclone.utils.USER_NODE
import com.example.instagramclone.utils.uploadVideo
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReelBinding
    private lateinit var videoUrl: String
    lateinit var progressDialog: ProgressDialog

    private var launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
        uri->
        uri?.let {
            uploadVideo(uri, REEL_FOLDER, progressDialog){
                url->
                if(url != null){
                    videoUrl = url
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityReelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = binding.materialToolbar

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this@ReelActivity, MainActivity::class.java))
            finish()
        }

        progressDialog= ProgressDialog(this)

        binding.selectReel.setOnClickListener {
            launcher.launch("video/*")
        }

        binding.postBtn.setOnClickListener {
           Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
               .addOnSuccessListener {
                   val user: User = it.toObject<User>()!!

                   val reel: Reel = Reel(videoUrl, binding.caption.text.toString(), user.image!!)

                   Firebase.firestore.collection(REEL).document().set(reel)
                       .addOnSuccessListener {
                           Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL)
                               .document()
                               .set(reel)
                               .addOnSuccessListener {
                                   startActivity(
                                       Intent(
                                           this@ReelActivity,
                                           MainActivity::class.java
                                       )
                                   )
                                   finish()
                               }
                       }
               }
        }
        binding.cancelBtn.setOnClickListener {
            startActivity(Intent(this@ReelActivity, MainActivity::class.java))
            finish()
        }
    }
}