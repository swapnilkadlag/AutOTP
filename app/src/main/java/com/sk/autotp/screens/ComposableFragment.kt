package com.sk.autotp.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.sk.autotp.theme.AutOTPTheme

abstract class ComposableFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AutOTPTheme {
                    Surface {
                        Box(
                            modifier = Modifier
                                .statusBarsPadding()
                                .navigationBarsPadding(),
                            contentAlignment = Alignment.Center,
                            content = { FragmentContent() },
                        )
                    }
                }
            }
        }
    }

    @Composable
    abstract fun BoxScope.FragmentContent()
}