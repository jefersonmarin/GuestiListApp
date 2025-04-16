package com.example.guest.ui.screens

import GuestDatabaseHelper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.guest.data.Guest
import com.example.guest.data.GuestRepositorySQLiteImpl
import com.example.guest.viewmodel.GuestViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestListScreen(navController: NavController) {
    val dbHelper = GuestDatabaseHelper(LocalContext.current)
    val repository = GuestRepositorySQLiteImpl(dbHelper)
    val viewmodel = GuestViewModel(repository)

    val guestList by viewmodel.guests.collectAsState()

    var guestToEdit by remember { mutableStateOf<Guest?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val onConfirmChange: (Guest, Boolean) -> Unit = { guest, isChecked ->
        val updatedGuest = guest.copy(confirmed = if (isChecked) 1 else 0)
        viewmodel.updateGuest(updatedGuest)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Convidados") },
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { navController.navigate("ProfileScreen")}
                    ) {
                        Icon(Icons.Default.Person, contentDescription = "Informações")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                guestToEdit = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            items(guestList.size) { index ->
                GuestCard(guestList[index],
                            onDelete = {
                                viewmodel.deleteGuest(guestList[index])
                            },
                            onEdit = {
                                showDialog = true
                                guestToEdit = guestList[index]
                            },
                            onConfirmChange = onConfirmChange
                )
            }
        }
    }
    GuestDialogForm(
        isOpen = showDialog,
        onDismiss = {
            showDialog = false
        },
        onSave = { guest ->
            if (guestToEdit == null) {
                viewmodel.insertGuest(guest)
            } else {
                viewmodel.updateGuest(guest)
            }
            showDialog = false
        },
        guest = guestToEdit
    )
}

@Composable
fun GuestCard(
    guest: Guest,
    onDelete: (Guest) -> Unit,
    onEdit: (Guest) -> Unit,
    onConfirmChange: (Guest, Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = guest.confirmed == 1,
                    onCheckedChange = {

                        onConfirmChange(guest, it)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(guest.name, style = MaterialTheme.typography.titleMedium)
                    Text(guest.email, style = MaterialTheme.typography.bodyMedium)
                    Text(guest.phone, style = MaterialTheme.typography.bodyMedium)
                }
            }

            Row {
                IconButton(onClick = {
                    onEdit(guest)
                }) {
                    Icon(Icons.Default.Edit, contentDescription = "Alterar")
                }
                IconButton(onClick = {
                    onDelete(guest)
                }) {
                    Icon(Icons.Default.Delete, contentDescription = "Excluir")
                }
            }
        }
    }
}