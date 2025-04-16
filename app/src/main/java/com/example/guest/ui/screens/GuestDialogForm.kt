package com.example.guest.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.guest.data.Guest

@Composable
fun GuestDialogForm(
    isOpen: Boolean,
    onDismiss: () -> Unit, //callback
    onSave: (Guest) -> Unit,
    guest: Guest? = null
) {
    if (isOpen) {
        var name by remember { mutableStateOf(guest?.name ?: "") }
        var email by remember { mutableStateOf(guest?.email ?: "") }
        var phone by remember { mutableStateOf(guest?.phone ?: "") }
        var confirmed by remember { mutableStateOf(guest?.confirmed == 1) } // Inicializa com o valor do convidado, se disponível

        AlertDialog(
            title = {
                Text("Adicionar convidado")
            },
            onDismissRequest = {},
            confirmButton = {
                TextButton(
                    onClick = {
                        val newGuest = Guest(
                            name = name,
                            email = email,
                            phone = phone,
                            confirmed = if (confirmed) 1 else 0, // Convert to 1 if confirmed, else 0
                            id = guest?.id ?: 0
                        )
                        name = ""
                        email = ""
                        phone = ""
                        onSave(newGuest)
                        onDismiss()
                    }
                ) {
                    Text("Salvar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text("Cancelar")
                }
            },
            text = {
                Column {
                    OutlinedTextField(
                        label = { Text("Informe o nome") },
                        value = name,
                        onValueChange = {
                            name = it
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        label = { Text("Informe o email") },
                        value = email,
                        onValueChange = {
                            email = it
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        label = { Text("Informe o telefone") },
                        value = phone,
                        onValueChange = {
                            phone = it
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = confirmed,
                            onCheckedChange = {
                                confirmed = it
                            }
                        )
                        Text("Confirmado?")
                    }
                }
            }
        )
    }
}
