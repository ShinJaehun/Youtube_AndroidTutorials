package com.shinjaehun.onlineshopappdemo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.shinjaehun.onlineshopappdemo.GlobalNavigation
import com.shinjaehun.onlineshopappdemo.model.CategoryModel

@Composable
fun CategoriesView(modifier: Modifier = Modifier) {

    var categoryList = remember {
        mutableStateOf<List<CategoryModel>>(emptyList())
    }

    LaunchedEffect(key1=Unit) {
        Firebase.firestore.collection("data").document("stock")
            .collection("categories")
            .get().addOnCompleteListener {
                if(it.isSuccessful) {
                    val resultList = it.result.documents.mapNotNull { doc ->
                        doc.toObject(CategoryModel::class.java)
                    }
                    categoryList.value = resultList
                }
            }
    }

//    Text(text = categoryList.value.toString())

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(categoryList.value){item ->
            CategoryItem(category = item)

        }
    }

}

@Composable
fun CategoryItem(category: CategoryModel) {
//    Text(text = category.name)
    Card(
        modifier = Modifier
            .size(90.dp)
            .clickable {
                GlobalNavigation.navController.navigate("category-products/" + category.id)
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = category.imageUrl,
                contentDescription = category.name,
                modifier = Modifier.size(70.dp)
            )
            Text(
                text = category.name,
                style= TextStyle(
                    fontSize = 12.sp,
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

//@Preview
//@Composable
//private fun CategoryItemPreview() {
//    CategoryItem(
//        CategoryModel(
//            id = "sports",
//            name = "Sports",
//            imageUrl = "https://firebasestorage.googleapis.com/v0/b/test-project-c364e.appspot.com/o/categories%2Fcategories_sports.PNG?alt=media&token=0b3827c5-9987-4025-88b5-c0d936290bfd"
//        )
//    )
//}