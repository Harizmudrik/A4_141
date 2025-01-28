package com.example.uaspam_141.ui.view.pemilik

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
import com.example.uaspam_141.model.Pemilik
import com.example.uaspam_141.ui.customewidget.TopAppBar
import com.example.uaspam_141.navigation.DestinasiNavigasi
import com.example.uaspam_141.ui.viewmodel.pemilik.DetailPemilikUiState
import com.example.uaspam_141.ui.viewmodel.pemilik.DetailPemilikViewModel
import com.example.uaspam_141.ui.viewmodel.pemilik.PenyediaPemilikViewModel


object DestinasiDetailPemilik : DestinasiNavigasi {
    override val route = "detailPemilik"
    override val titleRes = "Detail Pemilik"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPemilikView(
    id_pemilik: String,
    navigateBack: () -> Unit,
    onClick: () -> Unit,
    viewModel: DetailPemilikViewModel = viewModel(factory = PenyediaPemilikViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    viewModel.getPemilikById(id_pemilik)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiDetailPemilik.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPemilikById(id_pemilik)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Pemilik")
            }
        },
    ) { innerPadding ->
        when (uiState) {
            is DetailPemilikUiState.Loading -> {
                Text("Loading...", Modifier.fillMaxSize())
            }
            is DetailPemilikUiState.Success -> {
                val pemilik = (uiState as DetailPemilikUiState.Success).pemilik
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    ItemDetailPemilik(pemilik = pemilik)
                }
            }
            is DetailPemilikUiState.Error -> {
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
fun ItemDetailPemilik(
    modifier: Modifier = Modifier,
    pemilik: Pemilik
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
            ComponentDetailPemilik(judul = "ID Pemilik", isinya = pemilik.id_pemilik)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPemilik(judul = "Nama", isinya = pemilik.nama_pemilik)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPemilik(judul = "Kontak", isinya = pemilik.kontak_pemilik)
        }
    }
}

@Composable
fun ComponentDetailPemilik(
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