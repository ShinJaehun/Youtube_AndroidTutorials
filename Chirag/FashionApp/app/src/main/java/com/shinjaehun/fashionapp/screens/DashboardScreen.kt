package com.shinjaehun.fashionapp.screens

import android.graphics.Paint.Align
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shinjaehun.fashionapp.R
import com.shinjaehun.fashionapp.Screen

@Composable
fun DashboardScreen(
    navController: NavController
) {
    val searchState = remember { mutableStateOf("") }
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(2.0f)
            ) {
                Text(
                    text = "Experience\nThe Difference",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp, bottom = 24.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp)
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(48.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(48.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BasicTextField(
                        value = searchState.value,
                        onValueChange = {
                            searchState.value = it
                        },
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .wrapContentHeight()
                            .padding(start = 16.dp),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (searchState.value.isEmpty()) {
                                Text(
                                    text = "Search",
                                    color = Color.Gray,
                                    modifier = Modifier
                                        .weight(1f)
                                        .wrapContentHeight()
                                )
                            } else {
                                innerTextField()
                            }
                        }
                    )
                    Image(
                        Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .size(38.dp)
                            .background(
                                color = Color("#509790".toColorInt()),
                                shape = RoundedCornerShape(19.dp)
                            )
                            .padding(8.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .weight(1f)
                    .offset(y = (-24).dp)
                    .zIndex(1f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color("#CBEBF7".toColorInt()),
                                Color("#F9F7F6".toColorInt())
                            )
                        )
                    )
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                item {
                    ProductView(
                        image = R.drawable.list_item01,
                        name = "Summer Shirt",
                        price = 55.99f,
                        modifier = Modifier,
//                        modifier = Modifier.padding(end = 16.dp, top = 40.dp),
                        navController = navController
                    )
                }
                item {
                    ProductView(
                        image = R.drawable.list_item02,
                        name = "Sheath Dress",
                        price = 30.5f,
                        modifier = Modifier,
//                        modifier = Modifier.padding(end = 16.dp, top = 40.dp),
                        navController = navController
                    )
                }
                item {
                    ProductView(
                        image = R.drawable.list_item03,
                        name = "Sheath Dress",
                        price = 30.5f,
                        modifier = Modifier,
//                        modifier = Modifier.padding(end = 16.dp, top = 40.dp),
                        navController = navController
                    )
                }
                item {
                    ProductView(
                        image = R.drawable.list_item04,
                        name = "Jeans",
                        price = 47.58f,
                        modifier = Modifier,
//                        modifier = Modifier.padding(end = 16.dp, top = 40.dp),
                        navController = navController
                    )
                }
                item {
                    ProductView(
                        image = R.drawable.list_item05,
                        name = "Maxi",
                        price = 12.85f,
                        modifier = Modifier,
//                        modifier = Modifier.padding(end = 16.dp, top = 40.dp),
                        navController = navController
                    )
                }
                item {
                    ProductView(
                        image = R.drawable.list_item06,
                        name = "Skirt",
                        price = 89.5f,
                        modifier = Modifier,
//                        modifier = Modifier.padding(end = 16.dp, top = 40.dp),
                        navController = navController
                    )
                }
            }

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 16.dp, topEnd = 16.dp
                    )
                )
                .padding(top = 8.dp, bottom = 8.dp)
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon (
                   Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Icon (
                    Icons.Default.ShoppingCart,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Icon (
                    Icons.Default.AddCircle,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = Color("#509790".toColorInt())
                )
                Icon (
                    Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                )
                Icon (
                    Icons.Default.Settings,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                )
            }
        }
    }
}

@Composable
fun ProductView(
    image: Int,
    name: String,
    price: Float,
    modifier: Modifier,
    navController: NavController
) {
    ElevatedCard(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                navController.navigate(Screen.Product.route)
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(
            topStart = 112.dp,
            topEnd = 112.dp,
            bottomStart = 8.dp,
            bottomEnd = 8.dp
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(width = 170.dp, height = 220.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 112.dp,
                            topEnd = 112.dp
                        )
                    ),
                contentScale = ContentScale.Crop
            )
            Text(
                text = name,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "$$price",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 8.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    DashboardScreen(navController = rememberNavController())
}