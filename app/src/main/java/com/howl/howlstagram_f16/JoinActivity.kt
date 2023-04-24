package com.howl.howlstagram_f16

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.howl.howlstagram_f16.model.User
import com.howl.howlstagram_f16.model.UserCount
import kotlinx.android.synthetic.main.activity_join.*

class JoinActivity : AppCompatActivity() {


    var auth : FirebaseAuth? = null

    var isLogin = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth = FirebaseAuth.getInstance()

        back_btn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    fun signinAndSignup(){
        auth?.createUserWithEmailAndPassword(edit_step_one.text.toString(),edit_step_two.text.toString())
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    //Creating a user account
                    Toast.makeText(applicationContext, "회원가입 성공", Toast.LENGTH_SHORT).show()
                }else if(task.exception?.message.isNullOrEmpty()){
                    //Show the error message
                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
                }else{
                    //Login if you have account
                    Toast.makeText(applicationContext, "이메일 형식으로 아이디를 써주세요", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()

        val joinDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        val joinRef: DatabaseReference = joinDatabase.getReference("User/UserCount")

        join_btn.setOnClickListener {
            if(edit_step_two.length()<6) {
                    Toast.makeText(applicationContext, "비밀번호는 6자리 이상 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            else{
                if (edit_step_one.length() < 1 || edit_step_two.length() < 1 || edit_step_three.length() < 1) {
                    Toast.makeText(applicationContext, "빈 값이 있습니다. 다시 입력해주세요", Toast.LENGTH_SHORT).show()
                }
                else{
                    joinRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val userCount : UserCount? =  snapshot.getValue(UserCount::class.java)

                                setJoin(userCount!!.Count!!.toInt(), joinDatabase)
                                Log.d("로그",userCount.Count.toString())
                        }
                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
                        }

                    })
                }
                signinAndSignup()
            }
        }
    }



    private fun setJoin(userCount : Int, joinDatabase: FirebaseDatabase ){
        var id : String? = edit_step_one.text.toString()
        var pw : String? = edit_step_two.text.toString()
        var nickname : String? = edit_step_three.text.toString()

        val joinRef: DatabaseReference = joinDatabase.getReference("User")
        val user = User(id, pw, nickname)

        var count = userCount


        if(id != null && pw != null && nickname != null && id != "" && pw != "" && nickname != "") {

            if(isLogin) {
                isLogin = false

                count++

                joinRef.child("UserCount")
                    .child("Count")
                    .setValue(count.toString())

                joinRef.child("User_0${count}")
                    .child("id")
                    .setValue(user.id.toString())

                joinRef.child("User_0${count}")
                    .child("pw")
                    .setValue(user.pw.toString())

                joinRef.child("User_0${count}")
                    .child("nickName")
                    .setValue(user.nickName.toString())


                Toast.makeText(applicationContext, "회원가입 성공", Toast.LENGTH_SHORT).show()
            }
        }
        // 이렇게까지 해야 되나...
        id = null
        pw = null
        nickname = null
        edit_step_one.text = null
        edit_step_two.text = null
        edit_step_three.text = null

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}