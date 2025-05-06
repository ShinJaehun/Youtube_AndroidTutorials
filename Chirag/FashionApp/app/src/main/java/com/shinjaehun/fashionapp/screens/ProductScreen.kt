package com.shinjaehun.fashionapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shinjaehun.fashionapp.R

@Composable
fun ProductScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White,
                shape = RectangleShape
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null
                    ) {
                        navController.navigateUp()
                    }
            )
            Text(
                text = "Product",
                modifier = Modifier
                    .weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Image(
                Icons.Default.ShoppingCart,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.showcase_item01),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 40.dp, end = 40.dp)
                    .aspectRatio(1f)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) { i ->
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(if (i == 1) 10.dp else 8.dp)
                            .background(
                                color = if (i == 1) Color("#509790".toColorInt()) else Color.Gray,
                                shape = CircleShape
                            )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 20.dp, start = 48.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProductImage(image = R.drawable.showcase_item01, offset = 0)
                ProductImage(image = R.drawable.showcase_item02, offset = -12)
                ProductImage(image = R.drawable.showcase_item03, offset = -24)
                ProductImage(image = R.drawable.showcase_item04, offset = -36)
                ProductImage(image = R.drawable.list_item01, offset = -48)
            }
            Row(
                modifier = Modifier
                    .padding(top = 20.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hawaiian Shirt",
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                repeat(5) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color("#FFFF80".toColorInt()),
                    )
                }
            }
            Text(
                text = "올 아메리칸 카키스(All American Khakis)는 1983년 미국 조지아주에 설립된 가족 소유 기업 Chardan, Ltd.에서 시작했습니다.\n" +
                        "40년 동안 미 공군 및 해군 사관학교, AT&T, 그리고 디즈니에 이르기까지 미국의 크고 작은 기업과 단체를 위해 바지를 전문적으로 만들던 이들은 \n" +
                        "세월이 지남에 따라 미국에서 생산되는 제품과 일자리가 사라지는 것을 목격하였습니다.\n" +
                        "이를 계기로 2010년 고품질의 Made in USA 팬츠를 만들고자 이들은 올 아메리칸 카키스(All American Khakis)를 론칭했습니다.\n",
                modifier = Modifier
                    .padding(top = 20.dp, start = 24.dp, end = 24.dp)
                    .weight(1f),
                fontSize = 16.sp
            )
            Row(
                modifier = Modifier
                    .padding(start = 24.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProductSize(sizeText = "S")
                ProductSize(sizeText = "M")
                ProductSize(sizeText = "L")
                ProductSize(sizeText = "XL")
                ProductSize(sizeText = "XXL")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Price",
                    modifier = Modifier.wrapContentWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "$250.99",
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 4.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier
                    .background(
                        Color("#509790".toColorInt()),
                        RoundedCornerShape(40.dp)
                    )
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.shopping_bag_24px),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(color = Color.White)
                )
                Text(
                    text = "Add to cart",
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }

    }
}

@Composable
fun ProductSize(
    sizeText: String
) {
    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .wrapContentWidth()
            .size(48.dp)
            .border(width = 1.dp, color = Color.Gray, shape = CircleShape),
        contentAlignment =  Alignment.Center,

    ) {
        Text(
            text = sizeText,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
    
}

@Composable
fun ProductImage(
    image: Int,
    offset: Int,
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = null,
        modifier = Modifier
            .offset(x = offset.dp)
            .size(48.dp)
            .clip(shape = CircleShape)
            .border(
                width = 2.dp,
                color = Color.White,
                shape = CircleShape
            ),
        contentScale = ContentScale.Crop

    )
}

@Preview
@Composable
private fun ProductScreenPreview() {
    ProductScreen(navController = rememberNavController())
}