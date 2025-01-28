package com.example.uaspam_141.ui.view.properti

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam_141.R
import com.example.uaspam_141.model.Properti
import com.example.uaspam_141.navigation.DestinasiNavigasi
import com.example.uaspam_141.ui.customewidget.TopAppBar
import com.example.uaspam_141.ui.viewmodel.properti.HomePropertiUiState
import com.example.uaspam_141.ui.viewmodel.properti.HomePropertiViewModel
import com.example.uaspam_141.ui.viewmodel.properti.PenyediaPropertiViewModel

object DestinasiHomeProperti : DestinasiNavigasi {
    override val route = "homeProperti"
    override val titleRes = "Daftar Properti"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePropertiView(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
    navigateBack: () -> Unit,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomePropertiViewModel = viewModel(factory = PenyediaPropertiViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val showDialog = remember { mutableStateOf(false) }
    val propertiToDelete = remember { mutableStateOf<Properti?>(null) }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiHomeProperti.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getProperti()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Properti")
            }
        },
    ) { innerPadding ->
        HomePropertiStatus(
            homeUiState = viewModel.propertiUiState,
            retryAction = { viewModel.getProperti() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                propertiToDelete.value = it
                showDialog.value = true
            }
        )

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text("Konfirmasi Hapus") },
                text = { Text("Apakah Anda yakin ingin menghapus properti ini?") },
                confirmButton = {
                    Button(onClick = {
                        propertiToDelete.value?.let { properti ->
                            viewModel.deleteProperti(properti.id_properti)
                        }
                        showDialog.value = false
                    }) {
                        Text("Hapus")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog.value = false }) {
                        Text("Batal")
                    }
                }
            )
        }
    }
}

@Composable
fun HomePropertiStatus(
    homeUiState: HomePropertiUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Properti) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeUiState) {
        is HomePropertiUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomePropertiUiState.Success ->
            if (homeUiState.properti.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data properti")
                }
            } else {
                PropertiLayout(
                    properti = homeUiState.properti,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_properti)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomePropertiUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription =  stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.error), contentDescription = "Add Properti"
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction){
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun PropertiLayout(
    properti: List<Properti>,
    modifier: Modifier = Modifier,
    onDetailClick: (Properti) -> Unit,
    onDeleteClick: (Properti) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(properti) { properti ->
            PropertiCard(
                properti = properti,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(properti) },
                onDeleteClick = {
                    onDeleteClick(properti)
                }
            )
        }
    }
}

@Composable
fun PropertiCard(
    properti: Properti,
    modifier: Modifier = Modifier,
    onDeleteClick: (Properti) -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Place, contentDescription = "Nama Properti")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = properti.nama_properti,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { onDeleteClick(properti) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
            Text(text = "Lokasi: ${properti.lokasi}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Harga: Rp ${properti.harga}", style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "Status: ${properti.status_properti}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
