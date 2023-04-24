package com.howl.howlstagram_f16

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import com.google.firebase.database.*
import com.howl.howlstagram_f16.model.User


class LoginActivity : AppCompatActivity() {
    private var userList = ArrayList<User>()
    var user : User? =  User()

    var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        var intent: Intent

        email_login_button.setOnClickListener {
            if (email_edittext.length() < 1 || password_edittext.length() < 1) {
                Toast.makeText(applicationContext, "아이디와 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                email_login_button.text = "잠시만 기다려주세요..."
                email_login_button.isEnabled = false
                getLoginData()
            }
            signinEmail()
        }
        email_join_button.setOnClickListener {
            intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getLoginData() {

        val loginDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        val loginRef: DatabaseReference = loginDatabase.getReference("User")

        loginRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (i: DataSnapshot in snapshot.children) {
                    user = i.getValue(User::class.java)
                    userList.add(user!!)
                }
                loginDataCheck(userList)
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }
    private fun loginDataCheck(userList: ArrayList<User>) {

        val editId: String = email_edittext.text.toString()
        val editPw: String = password_edittext.text.toString()
        var loginCheck = false
        var userData : User? = null


        for (i in userList.indices) {
            if (userList[i].id == editId) {
                if (userList[i].pw == editPw) {
                    Toast.makeText(
                        applicationContext,
                        "${userList[i].nickName}님 환영합니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    userData = userList[i]
                    loginCheck = true
                    break
                }
            }
        }
        if (loginCheck) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userData", userData)
            intent.putExtra("loginSuccess", true)
            startActivity(intent)
        } else if (!loginCheck) {
            email_login_button.isEnabled = true
            Toast.makeText(applicationContext, "계정이 없습니다.", Toast.LENGTH_SHORT).show()
            email_login_button.text = "LOGIN"
        }
    }

    override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }

    fun printHashKey() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i("TAG", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("TAG", "printHashKey()", e)
        } catch (e: Exception) {
            Log.e("TAG", "printHashKey()", e)
        }

    }


    fun signinEmail(){
        auth?.signInWithEmailAndPassword(email_edittext.text.toString(),password_edittext.text.toString())
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    //Login
                    moveMainPage(task.result?.user)
                }else{
                    //Show the error message
                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
                }
            }
    }
    fun moveMainPage(user:FirebaseUser?){
        if(user != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
