package com.example.gsoft.wschat_android001

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.firebase.tubesock.WebSocket
import com.firebase.tubesock.WebSocketEventHandler
import com.firebase.tubesock.WebSocketException
import com.firebase.tubesock.WebSocketMessage
import java.net.URI

import com.example.gsoft.wschat_android001.R.id;
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var sendButton: Button? = null
    private var messageIn: EditText? = null
    private var messageOut: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var self = this;
        var uri = URI("wss://safe-ravine-60287.herokuapp.com/")
        var ws = WebSocket(uri, "echo-protocol")
        ws.setEventHandler(object: WebSocketEventHandler {
            override fun onOpen() {
                println("open!")
                //ws.send("android".toString())
            }

            override fun onLogMessage(msg: String?) {
                println(msg)
            }

            override fun onMessage(message: WebSocketMessage?) {
                println(message?.text)
                self.runOnUiThread {
                    self.editText2.setText(message?.text + "\n" + self.editText2.text)
                }
            }

            override fun onClose() {
                println("close!")
            }

            override fun onError(e: WebSocketException?) {
                println(e)
            }
        })
        ws.connect()
        this.messageIn = this.findViewById(id.editText)
        this.messageOut = this.findViewById(id.editText2)
        this.sendButton = this.findViewById(id.button)
        this.sendButton?.setOnClickListener {
            println("click!")
            var msg = messageIn?.text
            ws.send(msg.toString())
        }
    }

    override fun onResume() {
        super.onResume()
    }
}

