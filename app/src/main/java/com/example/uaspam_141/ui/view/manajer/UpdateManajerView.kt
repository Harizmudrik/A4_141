package com.example.uaspam_141.ui.view.manajer

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
import com.example.uaspam_141.ui.viewmodel.manajer.PenyediaManajerViewModel
import com.example.uaspam_141.ui.viewmodel.manajer.UpdateManajerEvent
import com.example.uaspam_141.ui.viewmodel.manajer.UpdateManajerUiState
import com.example.uaspam_141.ui.viewmodel.manajer.UpdateManajerViewModel

import kotlinx.coroutines.launch

object DestinasiUpdateManajer : DestinasiNavigasi {
    override val route = "update_manajer"
    override val titleRes = "Update Manajer"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateManajerView(
    id_manajer: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateManajerViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = PenyediaManajerViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(id_manajer) {
        viewModel.loadManajer(id_manajer)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiUpdateManajer.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateManajerBody(
            updateUiState = viewModel.uiState,
            onManajerValueChange = viewModel::updateManajerState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateManajer(id_manajer)
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
fun UpdateManajerBody(
    updateUiState: UpdateManajerUiState,
    onManajerValueChange: (UpdateManajerEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormManajerInput(
            updateUiEvent = updateUiState.updateManajerEvent,
            onValueChange = onManajerValueChange,
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
fun FormManajerInput(
    updateUiEvent: UpdateManajerEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateManajerEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateUiEvent.nama_manajer ?: "", // Default value jika null
            onValueChange = { onValueChange(updateUiEvent.copy(nama_manajer = it)) },
            label = { Text("Nama Manajer") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.id_manajer ?: "", // Default value jika null
            onValueChange = { onValueChange(updateUiEvent.copy(id_manajer = it)) },
            label = { Text("ID Manajer") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.kontak_manajer ?: "", // Default value jika null
            onValueChange = { onValueChange(updateUiEvent.copy(kontak_manajer = it)) },
            label = { Text("Kontak Manajer") },
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
