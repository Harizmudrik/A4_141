package com.example.uaspam_141.ui.view.manajer

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam_141.navigation.DestinasiNavigasi
import com.example.uaspam_141.ui.customewidget.TopAppBar
import com.example.uaspam_141.ui.viewmodel.manajer.InsertManajerUiEvent
import com.example.uaspam_141.ui.viewmodel.manajer.InsertManajerUiState
import com.example.uaspam_141.ui.viewmodel.manajer.InsertManajerViewModel
import com.example.uaspam_141.ui.viewmodel.manajer.PenyediaManajerViewModel
import kotlinx.coroutines.launch

object DestinasiManajerEntry : DestinasiNavigasi {
    override val route = "manajer_entry"
    override val titleRes = "Tambah Manajer"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryManajerScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertManajerViewModel = viewModel(factory = PenyediaManajerViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiManajerEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyManajer(
            insertUiState = viewModel.uiState,
            onManajerValueChange = viewModel::updateInsertManajerState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertManajer()
                    navigateBack()
                }
            },
            isSaveEnabled = viewModel.isFormValid(),
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyManajer(
    insertUiState: InsertManajerUiState,
    onManajerValueChange: (InsertManajerUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    isSaveEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputManajer(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onManajerValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            enabled = isSaveEnabled
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputManajer(
    insertUiEvent: InsertManajerUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertManajerUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.id_manajer,
            onValueChange = { onValueChange(insertUiEvent.copy(id_manajer = it)) },
            label = { Text("ID Manajer") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = insertUiEvent.id_manajer.isBlank()
        )
        OutlinedTextField(
            value = insertUiEvent.nama_manajer,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_manajer = it)) },
            label = { Text("Nama Manajer") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = insertUiEvent.nama_manajer.isBlank()
        )
        OutlinedTextField(
            value = insertUiEvent.kontak_manajer,
            onValueChange = { onValueChange(insertUiEvent.copy(kontak_manajer = it)) },
            label = { Text("Nomor Telepon") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = insertUiEvent.kontak_manajer.isBlank()
        )

        if (enabled) {
            Text(
                text = "Isi Semua Data Untuk Menyimpan",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
