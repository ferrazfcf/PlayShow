package com.ferraz.playshow.presentation.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ferraz.playshow.presentation.theme.PlayShowTheme

@Composable
fun PlayShowBottomBar(route: Routes?, navigateTo: (Routes) -> Unit) {
    when(route) {
        Home, MyList -> BottomBar(route, navigateTo)
        else -> Unit
    }
}

@Composable
private fun BottomBar(route: Routes, navigateTo: (Routes) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomBarItem(
            label = "Home",
            icon = Icons.Default.Home,
            selected = route == Home
        ) { navigateTo(Home) }
        BottomBarItem(
            label = "My List",
            icon = Icons.Default.Star,
            selected = route == MyList
        ) { navigateTo(MyList) }
    }
}

@Composable
private fun BottomBarItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {

    val backgroundColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.secondary
        } else {
            MaterialTheme.colorScheme.background
        }, label = "Bottom Bar Item Background"
    )

    val color by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.onSecondaryContainer
        } else {
            MaterialTheme.colorScheme.onBackground
        }, label = "Bottom Bar Item Color"
    )

    Column (
        modifier = Modifier
            .size(64.dp)
            .background(backgroundColor, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            tint = color,
            imageVector = icon,
            contentDescription = null
        )

        Text(
            text = label,
            color = color,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomBarPreview() {
    PlayShowTheme {
        BottomBar(route = Home, navigateTo = {})
    }
}
