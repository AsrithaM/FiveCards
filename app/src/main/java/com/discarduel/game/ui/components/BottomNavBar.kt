package com.discarduel.game.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Weekend
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.discarduel.game.ui.theme.NavBackground
import com.discarduel.game.ui.theme.NavSelected
import com.discarduel.game.ui.theme.NavUnselected

enum class NavTab { TABLE, BET, DEALS, LOUNGE }

data class NavItem(val tab: NavTab, val label: String, val icon: ImageVector)

@Composable
fun BottomNavBar(
    selectedTab: NavTab,
    onTabSelected: (NavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavItem(NavTab.TABLE, "TABLE", Icons.Filled.Casino),
        NavItem(NavTab.BET, "BET", Icons.Filled.MonetizationOn),
        NavItem(NavTab.DEALS, "DEALS", Icons.Filled.Layers),
        NavItem(NavTab.LOUNGE, "LOUNGE", Icons.Filled.Weekend)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(NavBackground)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isSelected = item.tab == selectedTab
            val tint = if (isSelected) NavSelected else NavUnselected

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onTabSelected(item.tab) }
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = tint,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = item.label,
                    color = tint,
                    fontSize = 10.sp
                )
            }
        }
    }
}
