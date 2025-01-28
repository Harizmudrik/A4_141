package com.example.uaspam_141.ui.view.properti

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam_141.model.Properti
import com.example.uaspam_141.ui.customewidget.TopAppBar
import com.example.uaspam_141.navigation.DestinasiNavigasi
import com.example.uaspam_141.ui.viewmodel.properti.DetailPropertiUiState
import com.example.uaspam_141.ui.viewmodel.properti.DetailPropertiViewModel
import com.example.uaspam_141.ui.viewmodel.properti.PenyediaPropertiViewModel

object DestinasiDetailProperti : DestinasiNavigasi {
    override val route = "detailProperti"
    override val titleRes = "Detail Properti"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPropertiView(
    id_properti: String,
    navigateBack: () -> Unit,
    onClick: () -> Unit,
    viewModel: DetailPropertiViewModel = viewModel(factory = PenyediaPropertiViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    viewModel.getPropertiById(id_properti)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiDetailProperti.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPropertiById(id_properti)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Properti")
            }
        },
    ) { innerPadding ->
        when (uiState) {
            is DetailPropertiUiState.Loading -> {
                Text("Loading...", Modifier.fillMaxSize())
            }
            is DetailPropertiUiState.Success -> {
                val properti = (uiState as DetailPropertiUiState.Success).properti
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    ItemDetailProperti(properti = properti)
                }
            }
            is DetailPropertiUiState.Error -> {
                Text(
                    text = "Error: Gagal memuat data. Silakan coba lagi.",
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun ItemDetailProperti(
    modifier: Modifier = Modifier,
    properti: Properti
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailProperti(judul = "ID Properti", isinya = properti.id_properti)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailProperti(judul = "Nama Properti", isinya = properti.nama_properti)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailProperti(judul = "Deskripsi", isinya = properti.deskripsi_properti)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailProperti(judul = "Lokasi", isinya = properti.lokasi)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailProperti(judul = "Harga", isinya = properti.harga.toString())
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailProperti(judul = "Status", isinya = properti.status_properti)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailProperti(judul = "ID Jenis", isinya = properti.id_jenis)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailProperti(judul = "ID Pemilik", isinya = properti.id_pemilik)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailProperti(judul = "ID Manajer", isinya = properti.id_manajer)
        }
    }
}

@Composable
fun ComponentDetailProperti(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
        )
        Text(
            text = isinya,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp
            )
        )
    }
}
