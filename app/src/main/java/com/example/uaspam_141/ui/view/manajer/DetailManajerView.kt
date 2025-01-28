package com.example.uaspam_141.ui.view.manajer

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
import com.example.uaspam_141.model.Manajer
import com.example.uaspam_141.ui.customewidget.TopAppBar
import com.example.uaspam_141.navigation.DestinasiNavigasi
import com.example.uaspam_141.ui.viewmodel.manajer.DetailManajerUiState
import com.example.uaspam_141.ui.viewmodel.manajer.DetailManajerViewModel
import com.example.uaspam_141.ui.viewmodel.manajer.PenyediaManajerViewModel


object DestinasiDetailManajer : DestinasiNavigasi {
    override val route = "detailManajer"
    override val titleRes = "Detail Manajer"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailManajerView(
    id_manajer: String,
    navigateBack: () -> Unit,
    onClick: () -> Unit,
    viewModel: DetailManajerViewModel = viewModel(factory = PenyediaManajerViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    viewModel.getManajerById(id_manajer)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiDetailManajer.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getManajerById(id_manajer)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Manajer")
            }
        },
    ) { innerPadding ->
        when (uiState) {
            is DetailManajerUiState.Loading -> {
                Text("Loading...", Modifier.fillMaxSize())
            }
            is DetailManajerUiState.Success -> {
                val manajer = (uiState as DetailManajerUiState.Success).manajer
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    ItemDetailManajer(manajer = manajer)
                }
            }
            is DetailManajerUiState.Error -> {
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
fun ItemDetailManajer(
    modifier: Modifier = Modifier,
    manajer: Manajer
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
            ComponentDetailManajer(judul = "ID Manajer", isinya = manajer.id_manajer)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailManajer(judul = "Nama", isinya = manajer.nama_manajer)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailManajer(judul = "Kontak", isinya = manajer.kontak_manajer)
        }
    }
}

@Composable
fun ComponentDetailManajer(
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