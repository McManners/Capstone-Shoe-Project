package com.example.myapplication

import android.app.AlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddShoeDialog(
    state: ShoeState,
    onEvent: (ShoeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(ShoeEvent.HideDialog)
        },
        title = { Text(text = "Add shoe") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.shoeName,
                    onValueChange = {
                        onEvent(ShoeEvent.SetShoeName(it))
                    },
                    placeholder = {
                        Text(text = "Shoe name")
                    }
                )
                TextField(
                    value = state.shoeType,
                    onValueChange = {
                        onEvent(ShoeEvent.SetShoeType(it))
                    },
                    placeholder = {
                        Text(text = "Shoe Type")
                    }
                )
                TextField(
                    value = state.imageID,
                    onValueChange = {
                        onEvent(ShoeEvent.SetShoeImageID(it))
                    },
                    placeholder = {
                        Text(text = "Image ID")
                    }
                )
            }
        },
        buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    onEvent(ShoeEvent.SaveShoe)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}