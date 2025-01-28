package com.example.uaspam_141.ui.view.jenis

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
import com.example.uaspam_141.ui.viewmodel.jenis.PenyediaJenisViewModel
import com.example.uaspam_141.ui.viewmodel.jenis.UpdateJenisEvent
import com.example.uaspam_141.ui.viewmodel.jenis.UpdateJenisUiState
import com.example.uaspam_141.ui.viewmodel.jenis.UpdateJenisViewModel

import kotlinx.coroutines.launch

object DestinasiUpdateJenis : DestinasiNavigasi {
    override val route = "update_jenis"
    override val titleRes = "Update Jenis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateJenisView(
    id_jenis: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateJenisViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = PenyediaJenisViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(id_jenis) {
        viewModel.loadJenis(id_jenis)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiUpdateJenis.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateJenisBody(
            updateUiState = viewModel.uiState,
            onJenisValueChange = viewModel::updateJenisState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateJenis(id_jenis)
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
fun UpdateJenisBody(
    updateUiState: UpdateJenisUiState,
    onJenisValueChange: (UpdateJenisEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormJenisInput(
            updateUiEvent = updateUiState.updateJenisEvent,
            onValueChange = onJenisValueChange,
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
fun FormJenisInput(
    updateUiEvent: UpdateJenisEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateJenisEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateUiEvent.nama_jenis ?: "", // Default value jika null
            onValueChange = { onValueChange(updateUiEvent.copy(nama_jenis = it)) },
            label = { Text("Nama Jenis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.id_jenis ?: "", // Default value jika null
            onValueChange = { onValueChange(updateUiEvent.copy(id_jenis = it)) },
            label = { Text("ID Jenis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.deskripsi_jenis ?: "", // Default value jika null
            onValueChange = { onValueChange(updateUiEvent.copy(deskripsi_jenis = it)) },
            label = { Text("Deskripsi Jenis") },
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
