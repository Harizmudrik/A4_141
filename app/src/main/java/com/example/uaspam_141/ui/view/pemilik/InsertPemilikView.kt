package com.example.uaspam_141.ui.view.pemilik

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
import com.example.uaspam_141.ui.viewmodel.pemilik.InsertPemilikUiEvent
import com.example.uaspam_141.ui.viewmodel.pemilik.InsertPemilikUiState
import com.example.uaspam_141.ui.viewmodel.pemilik.InsertPemilikViewModel
import com.example.uaspam_141.ui.viewmodel.pemilik.PenyediaPemilikViewModel
import kotlinx.coroutines.launch

object DestinasiPemilikEntry : DestinasiNavigasi {
    override val route = "pemilik_entry"
    override val titleRes = "Tambah Pemilik"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPemilikScreen(
    navigateBack: () -> Unit,
    onRefresh: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: InsertPemilikViewModel = viewModel(factory = PenyediaPemilikViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiPemilikEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyPemilik(
            insertUiState = viewModel.uiState,
            onPemilikValueChange = viewModel::updateInsertPemilikState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPemilik()
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
fun EntryBodyPemilik(
    insertUiState: InsertPemilikUiState,
    onPemilikValueChange: (InsertPemilikUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    isSaveEnabled: Boolean,
    onRefresh: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPemilik(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onPemilikValueChange,
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
fun FormInputPemilik(
    insertUiEvent: InsertPemilikUiEvent,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
    onValueChange: (InsertPemilikUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.id_pemilik,
            onValueChange = { onValueChange(insertUiEvent.copy(id_pemilik = it)) },
            label = { Text("ID PEMILIK") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = insertUiEvent.id_pemilik.isBlank()
        )
        OutlinedTextField(
            value = insertUiEvent.nama_pemilik,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_pemilik = it)) },
            label = { Text("Nama Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = insertUiEvent.nama_pemilik.isBlank()
        )
        OutlinedTextField(
            value = insertUiEvent.kontak_pemilik,
            onValueChange = { onValueChange(insertUiEvent.copy(kontak_pemilik = it)) },
            label = { Text("Nomor Telepon") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = insertUiEvent.kontak_pemilik.isBlank()
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
