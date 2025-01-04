package com.shinjaehun.turboandroidblog

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dev.hotwire.turbo.config.TurboPathConfiguration
import dev.hotwire.turbo.session.TurboSessionNavHostFragment
import kotlin.reflect.KClass

class MainSessionNavHostFragment : TurboSessionNavHostFragment() {
    override val sessionName = "main"

//    override val startLocation = "https://turbo-native-demo.glitch.me/"
    override val startLocation = "http://192.168.200.36:3000"

    override val registeredActivities: List<KClass<out AppCompatActivity>>
        get() = listOf(
            // Leave empty unless you have more
            // than one TurboActivity in your app
        )

    override val registeredFragments: List<KClass<out Fragment>>
        get() = listOf(
            WebFragment::class
            // And any other TurboFragments in your app
        )

    override val pathConfigurationLocation: TurboPathConfiguration.Location
        get() = TurboPathConfiguration.Location(
//            assetFilePath = "json/configuration.json",
//            remoteFileUrl = "https://turbo.hotwired.dev/demo/configurations/android-v1.json"

            assetFilePath = "json/configuration.json"
        )
}