package com.example.midmorningfirebaseapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var edtName:EditText
    lateinit var edtEmail:EditText
    lateinit var edtidNumber:EditText
    lateinit var btnsave:Button
    lateinit var btnview:Button
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtName = findViewById(R.id.mEdtName)
        edtEmail = findViewById(R.id.mEdtEmail)
        edtidNumber = findViewById(R.id.mEdtidNumber)
        btnsave = findViewById(R.id.mButtonSave)
        btnview = findViewById(R.id.mButtonView)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Saving")
        progressDialog.setMessage("Please wait")
        btnsave.setOnClickListener {
            var name = edtName.text.toString().trim()
            var email = edtEmail.text.toString().trim()
            var idNumber = edtidNumber.text.toString().trim()
            var id = System.currentTimeMillis().toString()
            // Check if the user is submitting empty fields
            if (name.isEmpty()){
                edtName.setError("Please fill this input")
                edtName.requestFocus()
            }else if (email.isEmpty()){
                edtEmail.setError("Please fill this input")
                edtEmail.requestFocus()
            }else if (idNumber.isEmpty()){
                edtidNumber.setError("Please fill this input")
                edtidNumber.requestFocus()
            }else{
                // Proceed to save
                var user = User (name, email, idNumber, id)
                // Create a reference to the firebase
                var ref = FirebaseDatabase.getInstance().getReference().child("Users/"+id)
                progressDialog.show()
                ref.setValue(user).addOnCompleteListener {
                    progressDialog.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this,"User saved successfully!",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"User saving failed! ",Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
        btnview.setOnClickListener {
            var intent = Intent(this,UsersActivity::class.java)
            startActivity(intent)
        }
    }
}