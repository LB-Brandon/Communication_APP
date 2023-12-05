package com.brandon.communication_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 네트워크 동작은 main 스레드에서 수행될 경우 block이 걸려
        // Main thread exeception 발생
        Thread {
            val port = 8080
            // Waiting for accept
            val server = ServerSocket(port)

            val socket = server.accept()

            // Client to Server
            socket.getInputStream()
            // Server to Client
            socket.getOutputStream()

            // inputStream 을 Buffer 에 담는다
            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            val printer = PrintWriter(socket.getOutputStream())

            var input: String? = "-1"
            // BufferedReader 로 부터 데이터 한 줄씩 읽기
            while (input != null && input != "") {
                input = reader.readLine()
            }

            Log.e("SERVER", "READ DATA $input")

            printer.println("HTTP/1.1 200 OK")
            printer.println("Content-Type: text/html\r\n")
            printer.println("<h1>Hello World</h1>")
            printer.println("\r\b")
            printer.flush()

            printer.close()
            reader.close()
            socket.close()
        }.start()

    }
}