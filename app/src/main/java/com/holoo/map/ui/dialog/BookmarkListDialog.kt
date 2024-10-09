package com.holoo.map.ui.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.holoo.map.R
import com.holoo.map.core.data.local.entity.LocationBookmarkEntity
import com.holoo.map.ui.components.Spacer8
import com.holoo.map.ui.theme.HolooTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkListDialog(
    bookmarks: List<LocationBookmarkEntity>,
    dismiss: () -> Unit,
    showOnMap: (bookmark: LocationBookmarkEntity) -> Unit,
    remove: (bookmark: LocationBookmarkEntity) -> Unit,
) {
    var expandedItemIndex by remember {
        mutableIntStateOf(-1)
    }

    Dialog(
        onDismissRequest = dismiss, properties = DialogProperties(
            dismissOnBackPress = true, dismissOnClickOutside = true
        )
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.bookmarks),
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                )

                Spacer8()

                LazyColumn {
                    items(count = bookmarks.size) { index ->
                        BookMarkItem(
                            bookmarkEntity = bookmarks[index],
                            expanded = index == expandedItemIndex,
                            remove = {
                                remove(bookmarks[index])
                            },
                            onClick = {
                                showOnMap(bookmarks[index])
                            },
                            toggleExpand = {
                                expandedItemIndex = if (expandedItemIndex == index) -1 else index
                            }
                        )

                        Spacer8()
                        HorizontalDivider()
                    }
                }

                if (bookmarks.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.no_bookmarks),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 32.dp)
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun BookMarkItem(
    bookmarkEntity: LocationBookmarkEntity,
    expanded: Boolean,
    remove: () -> Unit,
    onClick: () -> Unit,
    toggleExpand: () -> Unit,
) {
    Column(
        modifier = Modifier
            .heightIn(min = 64.dp)
            .clickable {
                onClick()
            }
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = toggleExpand) {
                val icon = if (expanded) Icons.Default.KeyboardArrowUp
                else Icons.Default.KeyboardArrowDown

                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
            }

            Text(
                text = bookmarkEntity.name,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleSmall
            )

            Spacer8()

            IconButton(onClick = remove) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null
                )
            }
        }
        if (expanded) {
            Text(
                text = bookmarkEntity.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewBookmarkItem() {
    HolooTheme {
        BookMarkItem(
            bookmarkEntity = LocationBookmarkEntity(
                id = 0,
                name = "Bookmark",
                description = "Description",
                latitude = 0.0,
                longitude = 0.0
            ),
            expanded = true,
            remove = {},
            onClick = {},
            toggleExpand = {}
        )
    }
}

@Preview
@Composable
fun PreviewBookmarkListDialog() {
    HolooTheme {
        BookmarkListDialog(
            bookmarks = emptyList(),
            dismiss = {},
            showOnMap = {},
            remove = {}
        )
    }
}