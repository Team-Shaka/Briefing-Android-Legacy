package com.dev.briefing.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dev.briefing.R
import com.dev.briefing.presentation.theme.SubBackGround

sealed class BottomNavigationItem(
    val route: String,
    val title: String,
    @DrawableRes val iconImg: Int,
    @DrawableRes val iconUnselectImg: Int
) {
    object Home : BottomNavigationItem(
        route = "HOME",
        title = "HOME",
        iconImg = R.drawable.breifing_selected,
        iconUnselectImg = R.drawable.breifing_normal
    )

    object Chat : BottomNavigationItem(
        route = "CHAT",
        title = "CHAT",
        iconImg = R.drawable.chat_selelcted,
        iconUnselectImg = R.drawable.chat_normal
    )

}

@Composable
fun BottomNavigationBar(
    selectedItemPosition: Int = 0,
    onItemSelected: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    items: List<BottomNavigationItem> = listOf(),
) {
    Row(
        modifier = modifier
            .background(color = SubBackGround)
            .padding(vertical = 18.dp)
        ,
        horizontalArrangement = Arrangement.SpaceAround) {
        items.forEachIndexed { index, bottomNavigationItem ->
            BottomNavigationItem(
                Modifier.weight(1f),
                index == selectedItemPosition,
                bottomNavigationItem
            ) {
                onItemSelected.invoke(index)
            }
        }
    }
}

@Composable
fun BottomNavigationItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    item: BottomNavigationItem,
    onClick: () -> Unit,
) {
    var isSelect by remember { mutableStateOf(isSelected) }

    Image(
        modifier = Modifier.clickable {
            isSelect = !isSelect
            onClick()
        },
        painter = painterResource(if (isSelected) item.iconImg else item.iconUnselectImg),
        contentDescription = item.title,
    )


}
