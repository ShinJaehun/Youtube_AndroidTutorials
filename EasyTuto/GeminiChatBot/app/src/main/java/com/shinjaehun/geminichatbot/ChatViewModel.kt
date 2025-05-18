package com.shinjaehun.geminichatbot

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.vertexai.type.content
import com.google.firebase.vertexai.vertexAI
import kotlinx.coroutines.launch

private const val TAG = "ChatViewModel"

class ChatViewModel: ViewModel() {

    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }

    val generativeModel = Firebase.vertexAI.generativeModel("gemini-2.0-flash")

    fun sendMessage(question: String) {
//        Log.i(TAG, "question: $question")
        viewModelScope.launch {
//            Log.i(TAG, "Response from Gemini: ${response.text.toString()}")

            // 공식 메뉴얼
//            messageList.add(MessageModel(question, "user"))
//            val response = generativeModel.generateContent(question)
//            messageList.add(MessageModel(response.text.toString(), "model"))

            // history 활용하기: 이게 있어야 대화가 연결됨?
//            val chat = generativeModel.startChat(
//                history = messageList.map {
//                    content(it.role) {
//                        text(it.message)
//                    }
//                }.toList()
//            )
//            messageList.add(MessageModel(question, "user"))
//            messageList.add(MessageModel("Typing...", "model"))
//            val response = chat.sendMessage(question)
//            messageList.removeAt(messageList.lastIndex)
//            messageList.add(MessageModel(response.text.toString(), "model"))

            try {
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role) {
                            text(it.message)
                        }
                    }.toList()
                )
                messageList.add(MessageModel(question, "user"))
                messageList.add(MessageModel("Typing...", "model"))
                val response = chat.sendMessage(question)
                messageList.removeAt(messageList.lastIndex)
                messageList.add(MessageModel(response.text.toString(), "model"))
            } catch (e: Exception) {
                messageList.removeAt(messageList.lastIndex)
                messageList.add(MessageModel("Error: " + e.message.toString(), "model"))
            }

        }
    }
}