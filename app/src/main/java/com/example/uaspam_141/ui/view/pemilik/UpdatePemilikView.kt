package com.example.uaspam_141.ui.view.pemilik

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.uaspam_141.navigation.DestinasiNavigasi
import com.example.uaspam_141.ui.customewidget.TopAppBar
import com.example.uaspam_141.ui.viewmodel.pemilik.PenyediaPemilikViewModel
import com.example.uaspam_141.ui.viewmodel.pemilik.UpdatePemilikEvent
import com.example.uaspam_141.ui.viewmodel.pemilik.UpdatePemilikUiState
import com.example.uaspam_141.ui.viewmodel.pemilik.UpdatePemilikViewModel

import kotlinx.coroutines.launch

object DestinasiUpdatePemilik : DestinasiNavigasi {
    override val route = "update_pemilik"
    override val titleRes = "Update Pemilik"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePemilikView(
    id_pemilik: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePemilikViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = PenyediaPemilikViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(id_pemilik) {
        viewModel.loadPemilik(id_pemilik)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiUpdatePemilik.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdatePemilikBody(
            updateUiState = viewModel.uiState,
            onPemilikValueChange = viewModel::updatePemilikState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePemilik(id_pemilik)
                    navigateBack() // Navigate back after saving
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun UpdatePemilikBody(
    updateUiState: UpdatePemilikUiState,
    onPemilikValueChange: (UpdatePemilikEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormPemilikInput(
            updateUiEvent = updateUiState.updatePemilikEvent,
            onValueChange = onPemilikValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Update")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormPemilikInput(
    updateUiEvent: UpdatePemilikEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdatePemilikEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateUiEvent.nama_pemilik ?: "", // Default value jika null
            onValueChange = { onValueChange(updateUiEvent.copy(nama_pemilik = it)) },
            label = { Text("Nama Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.id_pemilik ?: "", // Default value jika null
            onValueChange = { onValueChange(updateUiEvent.copy(id_pemilik = it)) },
            label = { Text("ID Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.kontak_pemilik ?: "", // Default value jika null
            onValueChange = { onValueChange(updateUiEvent.copy(kontak_pemilik = it)) },
            label = { Text("Kontak Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = "Isi Semua Data",
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
