package com.shinjaehun.mvvmtvshows.util

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

const val TAG = "BindingAdapters"

// 이렇게 하면 오류 발생... 내가 DataBinding에 대해서 잘 몰라서 그러는거지???
// BindingAdapter 사용할 때 오류.
// https://blog.naver.com/PostView.naver?blogId=kimjongwoo2003&logNo=222791786449&parentCategoryNo=&categoryNo=1&viewDate=&isShowPopularPosts=false&from=postView
//class BindingAdapters {
//    @BindingAdapter("android:imageURL")
//    fun setImageURL(imageView: ImageView, URL: String) {
//        try {
//            imageView.alpha = 0f
//            Picasso.get().load(URL).noFade().into(imageView, object: Callback {
//                override fun onSuccess() {
//                    imageView.animate().setDuration(300).alpha(1f).start()
//                }
//
//                override fun onError(e: Exception?) {
//
//                }
//
//            })
//        } catch (e : Exception) {
//            Log.i(TAG, e.message.toString())
//        }
//    }
//}

@BindingAdapter("android:imageURL")
fun setImageURL(imageView: ImageView, URL: String) {
    try {
        imageView.alpha = 0f
        Picasso.get().load(URL).noFade().into(imageView, object: Callback {
            // Picasso Callback() 구현
            // https://stackoverflow.com/questions/55829753/picasso-callback-with-kotlin
            override fun onSuccess() {
                imageView.animate().setDuration(300).alpha(1f).start()
            }

            override fun onError(e: Exception?) {

            }

        })
    } catch (e : Exception) {
        Log.i(TAG, e.message.toString())
    }
}