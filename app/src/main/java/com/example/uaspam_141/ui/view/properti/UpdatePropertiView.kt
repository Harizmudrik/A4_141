package com.example.uaspam_141.ui.view.properti

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
import com.example.uaspam_141.ui.viewmodel.properti.PenyediaPropertiViewModel
import com.example.uaspam_141.ui.viewmodel.properti.UpdatePropertiEvent
import com.example.uaspam_141.ui.viewmodel.properti.UpdatePropertiUiState
import com.example.uaspam_141.ui.viewmodel.properti.UpdatePropertiViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateProperti : DestinasiNavigasi {
    override val route = "update_properti"
    override val titleRes = "Update Properti"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePropertiView(
    id_properti: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePropertiViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = PenyediaPropertiViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(id_properti) {
        viewModel.loadProperti(id_properti)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiUpdateProperti.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdatePropertiBody(
            updateUiState = viewModel.uiState,
            onPropertiValueChange = viewModel::updatePropertiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateProperti(id_properti)
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
fun UpdatePropertiBody(
    updateUiState: UpdatePropertiUiState,
    onPropertiValueChange: (UpdatePropertiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormPropertiInput(
            updateUiEvent = updateUiState.updatePropertiEvent,
            onValueChange = onPropertiValueChange,
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
fun FormPropertiInput(
    updateUiEvent: UpdatePropertiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdatePropertiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateUiEvent.id_properti ?: "",
            onValueChange = { onValueChange(updateUiEvent.copy(id_properti = it)) },
            label = { Text("ID Properti") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.nama_properti ?: "",
            onValueChange = { onValueChange(updateUiEvent.copy(nama_properti = it)) },
            label = { Text("Nama Properti") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.deskripsi_properti ?: "",
            onValueChange = { onValueChange(updateUiEvent.copy(deskripsi_properti = it)) },
            label = { Text("Deskripsi Properti") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )
        OutlinedTextField(
            value = updateUiEvent.lokasi ?: "",
            onValueChange = { onValueChange(updateUiEvent.copy(lokasi = it)) },
            label = { Text("Lokasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.harga ?: "",
            onValueChange = { onValueChange(updateUiEvent.copy(harga = it)) },
            label = { Text("Harga") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.status_properti ?: "",
            onValueChange = { onValueChange(updateUiEvent.copy(status_properti = it)) },
            label = { Text("Status Properti") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.id_jenis ?: "",
            onValueChange = { onValueChange(updateUiEvent.copy(id_jenis = it)) },
            label = { Text("ID Jenis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.id_pemilik ?: "",
            onValueChange = { onValueChange(updateUiEvent.copy(id_pemilik = it)) },
            label = { Text("ID Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.id_manajer ?: "",
            onValueChange = { onValueChange(updateUiEvent.copy(id_manajer = it)) },
            label = { Text("ID Manajer") },
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
