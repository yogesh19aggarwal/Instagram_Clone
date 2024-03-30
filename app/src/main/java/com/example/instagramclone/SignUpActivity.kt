package com.example.instagramclone

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagramclone.Models.User
import com.example.instagramclone.databinding.ActivitySignUpBinding
import com.example.instagramclone.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    lateinit var user: User
    private var launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
        uri->

        uri?.let{

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        user = User()

        val signToLogin = binding.alreadySignUp

        val textInputName = binding.textInput1.editText
        val textInputEmail = binding.textInput2.editText
        val textInputPassword = binding.textInput3.editText

        signToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val signUpbtn = binding.signUpBtn

        signUpbtn.setOnClickListener {

            if(textInputName?.text.toString().equals("") or
                textInputEmail?.text.toString().equals("") or
                textInputPassword?.text.toString().equals("")
                ){
                Toast.makeText(this, "Please fill all information", Toast.LENGTH_SHORT).show()
            }else{
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    textInputEmail?.text.toString(),
                    textInputPassword?.text.toString()
                ).addOnCompleteListener {
                    result->

                    if(result.isSuccessful){

                        user.name = textInputName?.text.toString()
                        user.password = textInputPassword?.text.toString()
                        user.emails = textInputEmail?.text.toString()

                        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnSuccessListener {
                                Toast.makeText(this, "SignUp Successfull", Toast.LENGTH_SHORT).show()
                            }
                    }else{

                        Toast.makeText(this, result.exception?.localizedMessage, Toast.LENGTH_SHORT).show()

                    }
                }
            }

        }
    }
}