package com.stylianosgakis.test

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import com.stylianosgakis.test.ui.theme.TestTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      TestTheme {
        Repro()
      }
    }
  }
}

@Composable
private fun Repro() {
  Layout(
    content = { ReproSharedTransitionLayout() },
  ) { measurables, constraints ->
    val measurable = measurables[0]
    measurable.maxIntrinsicWidth(constraints.maxHeight)
    val placeable = measurable.measure(constraints)
    layout(constraints.maxWidth, constraints.maxHeight) {
      placeable.place(0, 0)
    }
  }
}

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
private fun ReproSharedTransitionLayout() {
  SharedTransitionLayout {
    AnimatedContent(Unit) {
      Box(
        Modifier
          .sharedBounds(
            sharedContentState = rememberSharedContentState("id"),
            animatedVisibilityScope = this@AnimatedContent,
          ),
      ) {
        Box(Modifier.size(100.dp))
      }
    }
  }
}
