package com.shinjaehun.onlineshopappdemo.pages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.shinjaehun.onlineshopappdemo.components.ProductItemView
import com.shinjaehun.onlineshopappdemo.model.CategoryModel
import com.shinjaehun.onlineshopappdemo.model.ProductModel

@Composable
fun CategoryProductsPage(modifier: Modifier = Modifier, categoryId:String) {
//    Text(text = "Category Products ::: " + categoryId)

    val productsList = remember {
        mutableStateOf<List<ProductModel>>(emptyList())
    }

    LaunchedEffect(key1=Unit) {
        Firebase.firestore.collection("data").document("stock")
            .collection("products")
            .whereEqualTo("category", categoryId)
            .get().addOnCompleteListener {
                if(it.isSuccessful) {
                    val resultList = it.result.documents.mapNotNull { doc ->
                        doc.toObject(ProductModel::class.java)
                    }
                    productsList.value = resultList
//                    productsList.value = resultList.plus(resultList).plus(resultList) // for testing
                }
            }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize()
            .padding(16.dp)
    ) {
//        items(productsList.value){item ->
//            Text(text = item.title+" --> "+item.actualPrice)
//            Spacer(modifier=Modifier.height(50.dp))
//            ProductItemView(product = item)
//        }
        items(productsList.value.chunked(2)){rowItems ->
            Row{
                rowItems.forEach {
                    ProductItemView(product = it, modifier = Modifier.weight(1f))
                }
                if(rowItems.size==1){
                    Spacer(modifier=Modifier.weight(1f))
                }
            }
        }
    }

}