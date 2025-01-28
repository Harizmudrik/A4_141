package com.example.uaspam_141.ui.view.jenis

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
import com.example.uaspam_141.model.Jenis
import com.example.uaspam_141.ui.customewidget.TopAppBar
import com.example.uaspam_141.navigation.DestinasiNavigasi
import com.example.uaspam_141.ui.viewmodel.jenis.DetailJenisUiState
import com.example.uaspam_141.ui.viewmodel.jenis.DetailJenisViewModel
import com.example.uaspam_141.ui.viewmodel.jenis.PenyediaJenisViewModel


object DestinasiDetailJenis : DestinasiNavigasi {
    override val route = "detailJenis"
    override val titleRes = "Detail Jenis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailJenisView(
    id_jenis: String,
    navigateBack: () -> Unit,
    onClick: () -> Unit,
    viewModel: DetailJenisViewModel = viewModel(factory = PenyediaJenisViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    viewModel.getJenisById(id_jenis)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiDetailJenis.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getJenisById(id_jenis)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Jenis")
            }
        },
    ) { innerPadding ->
        when (uiState) {
            is DetailJenisUiState.Loading -> {
                Text("Loading...", Modifier.fillMaxSize())
            }
            is DetailJenisUiState.Success -> {
                val jenis = (uiState as DetailJenisUiState.Success).jenis
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    ItemDetailJenis(jenis = jenis)
                }
            }
            is DetailJenisUiState.Error -> {
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
fun ItemDetailJenis(
    modifier: Modifier = Modifier,
    jenis: Jenis
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
            ComponentDetailJenis(judul = "ID Jenis", isinya = jenis.id_jenis)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailJenis(judul = "Nama", isinya = jenis.nama_jenis)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailJenis(judul = "Deskripsi", isinya = jenis.deskripsi_jenis)
        }
    }
}

@Composable
fun ComponentDetailJenis(
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