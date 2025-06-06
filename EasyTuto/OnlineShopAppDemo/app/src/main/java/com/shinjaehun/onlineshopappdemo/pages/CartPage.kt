package com.shinjaehun.onlineshopappdemo.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.shinjaehun.onlineshopappdemo.AppUtil
import com.shinjaehun.onlineshopappdemo.GlobalNavigation
import com.shinjaehun.onlineshopappdemo.components.CartItemView
import com.shinjaehun.onlineshopappdemo.model.UserModel

@Composable
fun CartPage(modifier: Modifier = Modifier) {
//    Text(text="CartPage")
    val userModel = remember {
        mutableStateOf(UserModel())
    }

//    LaunchedEffect(key1 = Unit) {
//        Firebase.firestore.collection("users")
//            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
//            .get().addOnCompleteListener{
//                //결국 이렇게 하면 cartItem에 대한 동기화가 이루어지지는 않음!
//                if(it.isSuccessful){
//                    val result = it.result.toObject(UserModel::class.java)
//                    if(result!=null){
//                        userModel.value = result
//                    }
//                }
//            }
//    }

    DisposableEffect(key1 = Unit) {
        var listener = Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .addSnapshotListener { r, _ ->
                if(r!=null){
                    val result = r.toObject(UserModel::class.java)
                    if(result!=null){
                        userModel.value = result
                    }
                }
            }
        onDispose { listener.remove() }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Your cart",
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        )
//        Text(text = userModel.value.toString())
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
//            items(userModel.value.cartItems.toList()){(productId,qty)->
//            // 해결하기 어려운 버그인데 맨 위에 있는 품목이 removeAll() 상태가 될때
//            // 어의 없게 한 줄 씩 올라가버림(맨 밑에 있는 품목이 사라져버리는 건가?)
//                CartItemView(productId=productId, qty=qty)
//            }

            items(userModel.value.cartItems.toList(), key={it.first}){(productId,qty)->
//                Text(text = productId + " -> " + qty)
                CartItemView(productId=productId, qty=qty)
            }
        }

        Button(
            onClick = {
                GlobalNavigation.navController.navigate("checkout")
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(text = "Checkout", fontSize = 16.sp)
        }
    }
}