package com.example.guest.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.guest.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastro(
    navController: NavController
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var senhaConfirm by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var senhaMismatch by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 200.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icone_guestapp),
                            contentScale = ContentScale.Crop,
                            contentDescription = "icone_guestapp",
                            modifier = Modifier
                                .padding(top = 62.dp)
                                .size(80.dp)
                                .clip(CircleShape)
                                .align(Alignment.TopCenter)
                        )

                        Column(
                            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 16.dp)
                        ) {
                            Text(
                                text = "Cadastre-se",
                                color = androidx.compose.ui.graphics.Color.Black
                            )
                        }
                    }
                }
            )
        }
)   { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(start = 16.dp, end = 16.dp)
                .padding(top = 32.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Nome") },
                value = name,
                onValueChange = { name = it },
                isError = showError && name.isBlank()
            )
            if (showError && name.isBlank()) {
                Text("Nome obrigatório", color = androidx.compose.ui.graphics.Color.Red)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email") },
                value = email,
                onValueChange = { email = it },
                isError = showError && email.isBlank()
            )
            if (showError && email.isBlank()) {
                Text("Email obrigatório", color = androidx.compose.ui.graphics.Color.Red)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Senha") },
                value = senha,
                onValueChange = { senha = it },
                isError = (showError && senha.isBlank()) || senhaMismatch,
                visualTransformation = PasswordVisualTransformation()
            )
            if (showError && senha.isBlank()) {
                Text("Senha obrigatória", color = androidx.compose.ui.graphics.Color.Red)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Confirmar Senha") },
                value = senhaConfirm,
                onValueChange = { senhaConfirm = it },
                isError = (showError && senhaConfirm.isBlank()) || senhaMismatch,
                visualTransformation = PasswordVisualTransformation()
            )
            if (showError && senhaConfirm.isBlank()) {
                Text("Confirme a senha", color = androidx.compose.ui.graphics.Color.Red)
            } else if (senhaMismatch) {
                Text("As senhas não coincidem", color = androidx.compose.ui.graphics.Color.Red)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                Button(
                    onClick = {navController.navigate("Login")},
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Voltar")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        showError = name.isBlank() || email.isBlank() || senha.isBlank() || senhaConfirm.isBlank()
                        senhaMismatch = senha != senhaConfirm

                        if (!showError && !senhaMismatch) {
                            showDialog = true
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Salvar")
                }
            }
        }
    }

    if (showDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    name = ""
                    email = ""
                    senha = ""
                    senhaConfirm = ""
                }) {
                    Text("Fechar")
                }
            },
            title = { Text("Usuário cadastrado!") },
            text = { Text("Nome: $name\nEmail: $email") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TelaCadastroPreview() {
    TelaCadastro(navController = NavController(LocalContext.current))
}
