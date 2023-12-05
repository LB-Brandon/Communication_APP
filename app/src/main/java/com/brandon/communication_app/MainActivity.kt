package com.brandon.communication_app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.et_server_host)
        val confirmButton = findViewById<Button>(R.id.btn_confirm)
        val informationTextView = findViewById<TextView>(R.id.tv_info)
        val client = OkHttpClient()
        var serverHost = ""

        editText.addTextChangedListener {
            serverHost = it.toString()
            Log.d("Client", "$serverHost")
        }

        confirmButton.setOnClickListener {
            val url = "http://$serverHost:8080"
            Log.d("Client", url)
            // build 중 header 등 추가 정보 삽입 가능
            val request: Request = Request.Builder()
                .url(url)
                .build()

            val callback = object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Client", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val response = response.body?.string()

                        // Gson
                        val message = Gson().fromJson(response, Message::class.java)



                        runOnUiThread {
                            informationTextView.isVisible = true
                            informationTextView.text = message.message

                            editText.isVisible = false
                            confirmButton.isVisible = false
                        }
                    } else {
                        runOnUiThread {
                            makeText(this@MainActivity, "수신에 실패했습니다", LENGTH_SHORT).show()
                        }
                    }
                }
            }
            // request 를 가지고 서버로 요청 실행(execute)
            client.newCall(request).enqueue(callback)
        }


    }

    /*
            // Write -> Read
            Thread {
                try {
    //            val socket = Socket("192.168.0.6", 8080)
                    val socket = Socket("10.0.2.2", 8080) // 에뮬레이터 localhost ip
                    val printer = PrintWriter(socket.getOutputStream())
                    val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

                    printer.println("GET /HTTP/1.1")
                    printer.println("Host: 127.0.0.1:8080")
                    printer.println("User-Agent: android")
                    printer.println("\r\n")
                    printer.flush()


                    var input: String? = "-1"
                    while (input != null) {
                        input = reader.readLine()
                        Log.e("Client", "$input")
                    }

                    reader.close()
                    printer.close()
                    socket.close()
                } catch (e: Exception) {
                    Log.e("Client", e.toString())
                }
            }.start()
     */

}