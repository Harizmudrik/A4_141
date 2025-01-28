package com.example.uaspam_141.ui.view.properti

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam_141.model.Jenis
import com.example.uaspam_141.model.Manajer
import com.example.uaspam_141.model.Pemilik
import com.example.uaspam_141.navigation.DestinasiNavigasi
import com.example.uaspam_141.ui.customewidget.TopAppBar
import com.example.uaspam_141.ui.viewmodel.jenis.HomeJenisUiState
import com.example.uaspam_141.ui.viewmodel.jenis.HomeJenisViewModel
import com.example.uaspam_141.ui.viewmodel.jenis.PenyediaJenisViewModel
import com.example.uaspam_141.ui.viewmodel.manajer.HomeManajerUiState
import com.example.uaspam_141.ui.viewmodel.manajer.HomeManajerViewModel
import com.example.uaspam_141.ui.viewmodel.manajer.PenyediaManajerViewModel
import com.example.uaspam_141.ui.viewmodel.pemilik.HomePemilikUiState
import com.example.uaspam_141.ui.viewmodel.pemilik.HomePemilikViewModel
import com.example.uaspam_141.ui.viewmodel.pemilik.PenyediaPemilikViewModel
import com.example.uaspam_141.ui.viewmodel.properti.InsertPropertiUiEvent
import com.example.uaspam_141.ui.viewmodel.properti.InsertPropertiUiState
import com.example.uaspam_141.ui.viewmodel.properti.InsertPropertiViewModel
import com.example.uaspam_141.ui.viewmodel.properti.PenyediaPropertiViewModel
import kotlinx.coroutines.launch

object DestinasiPropertiEntry : DestinasiNavigasi {
    override val route = "properti_entry"
    override val titleRes = "Tambah Properti"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPropertiScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPropertiViewModel = viewModel(factory = PenyediaPropertiViewModel.Factory),
    viewModelJenis: HomeJenisViewModel = viewModel(factory = PenyediaPropertiViewModel.Factory),
    viewModelPemilik: HomePemilikViewModel = viewModel(factory = PenyediaPropertiViewModel.Factory),
    viewModelManajer: HomeManajerViewModel = viewModel(factory = PenyediaPropertiViewModel.Factory),
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val jenisUiState = viewModelJenis.jenisUiState
    val pemilikUiState = viewModelPemilik.pemilikUiState
    val manajerUiState = viewModelManajer.manajerUiState

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiPropertiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyProperti(
            insertUiState = viewModel.uiState,
            jenis = jenisUiState,
            pemilik = pemilikUiState,
            manajer = manajerUiState,
            onPropertiValueChange = viewModel::updateInsertPropertiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertProperti()
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
fun EntryBodyProperti(
    insertUiState: InsertPropertiUiState,
    onPropertiValueChange: (InsertPropertiUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    isSaveEnabled: Boolean,
    jenis: HomeJenisUiState,
    pemilik:HomePemilikUiState,
    manajer: HomeManajerUiState,
    modifier: Modifier = Modifier
) {
    when(manajer){
        is HomeManajerUiState.Success -> {when(jenis){
            is HomeJenisUiState.Success ->{  when(pemilik){
                is HomePemilikUiState.Success -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(18.dp),
                        modifier = modifier.padding(12.dp)
                    ) {
                        FormInputProperti(
                            insertUiEvent = insertUiState.insertUiEvent,
                            onValueChange = onPropertiValueChange,
                            jenisList = jenis.jenis,
                            pemilikList = pemilik.pemilik,
                            manajerList = manajer.manajer,
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
                is HomePemilikUiState.Error -> {
                    Text("Error", modifier = Modifier.fillMaxSize())
                }
                is HomePemilikUiState.Loading -> OnLoading(modifier = Modifier.fillMaxSize())
            }}
            is HomeJenisUiState.Error -> {Text("Error", modifier = Modifier.fillMaxSize())}
            is HomeJenisUiState.Loading -> OnLoading(modifier = Modifier.fillMaxSize())

        }}
        is HomeManajerUiState.Error -> {Text("Error", modifier = Modifier.fillMaxSize())}
        is HomeManajerUiState.Loading -> OnLoading(modifier = Modifier.fillMaxSize())

    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputProperti(
    insertUiEvent: InsertPropertiUiEvent,
    jenisList: List<Jenis>,
    pemilikList: List<Pemilik>,
    manajerList: List<Manajer>,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPropertiUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    var selectedJenis by remember { mutableStateOf(jenisList.find { it.id_jenis == insertUiEvent.id_jenis } ?: jenisList.firstOrNull()) }
    var selectedPemilik by remember { mutableStateOf(pemilikList.find { it.id_pemilik == insertUiEvent.id_pemilik } ?: pemilikList.firstOrNull()) }
    var selectedManajer by remember { mutableStateOf(manajerList.find { it.id_manajer == insertUiEvent.id_manajer } ?: manajerList.firstOrNull()) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.id_properti,
            onValueChange = { onValueChange(insertUiEvent.copy(id_properti = it)) },
            label = { Text("ID Properti") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = insertUiEvent.id_properti.isBlank()
        )
        OutlinedTextField(
            value = insertUiEvent.nama_properti,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_properti = it)) },
            label = { Text("Nama Properti") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = insertUiEvent.nama_properti.isBlank()
        )
        OutlinedTextField(
            value = insertUiEvent.deskripsi_properti,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsi_properti = it)) },
            label = { Text("Deskripsi Properti") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false,
            isError = insertUiEvent.deskripsi_properti.isBlank()
        )
        OutlinedTextField(
            value = insertUiEvent.lokasi,
            onValueChange = { onValueChange(insertUiEvent.copy(lokasi = it)) },
            label = { Text("Lokasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = insertUiEvent.lokasi.isBlank()
        )
        OutlinedTextField(
            value = insertUiEvent.harga,
            onValueChange = { onValueChange(insertUiEvent.copy(harga = it)) },
            label = { Text("Harga") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = insertUiEvent.harga.isBlank()
        )
        OutlinedTextField(
            value = insertUiEvent.status_properti,
            onValueChange = { onValueChange(insertUiEvent.copy(status_properti = it)) },
            label = { Text("Status Properti") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = insertUiEvent.status_properti.isBlank()
        )


        DropdownMenuFieldWithObject(
            label = "ID Jenis",

            options = jenisList,
            selectedOption = selectedJenis,
            displayText = { it.nama_jenis },
            onOptionSelected = {
                selectedJenis = it
                onValueChange(insertUiEvent.copy(id_jenis = it.id_jenis))
            }
        )


        DropdownMenuFieldWithObject(
            label = "ID Pemilik",
            options = pemilikList,
            selectedOption = selectedPemilik,
            displayText = { it.nama_pemilik },
            onOptionSelected = {
                selectedPemilik = it
                onValueChange(insertUiEvent.copy(id_pemilik = it.id_pemilik))
            }
        )


        DropdownMenuFieldWithObject(
            label = "ID Manajer",
            options = manajerList,
            selectedOption = selectedManajer,
            displayText = { it.nama_manajer },
            onOptionSelected = {
                selectedManajer = it
                onValueChange(insertUiEvent.copy(id_manajer = it.id_manajer))

            }
        )
    }
}

@Composable
fun <T> DropdownMenuFieldWithObject(
    label: String,
    options: List<T>,
    selectedOption: T?,
    displayText: (T) -> String,
    onOptionSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = selectedOption?.let { displayText(it) } ?: "",
            onValueChange = {},
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onOptionSelected(option)
                    },
                    text = { Text(displayText(option)) }
                )
            }
        }
    }
}

