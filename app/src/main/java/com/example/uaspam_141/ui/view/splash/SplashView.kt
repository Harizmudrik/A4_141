package com.example.uaspam_141.ui.view.splash

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uaspam_141.navigation.DestinasiNavigasi
import com.example.uaspam_141.ui.customewidget.TopAppBar

object DestinasiSplash : DestinasiNavigasi {
    override val route = "Splash View"
    override val titleRes = "Daftar Menu"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashView(
    onPropertiClick: () -> Unit,
    onJenisClick: () -> Unit,
    onPemilikClick: () -> Unit,
    onManajerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = DestinasiSplash.titleRes,
            navigateUp = {},
            canNavigateBack = false,
            modifier = modifier
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Main Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                elevation = CardDefaults.cardElevation(8.dp),
                shape = RoundedCornerShape(16.dp),

            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "MENU UTAMA PROPERTI",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Silakan pilih salah satu opsi di bawah ini untuk melanjutkan",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center

                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // Buttons
                    MenuButton(text = "Properti", onClick = onPropertiClick)
                    MenuButton(text = "Jenis", onClick = onJenisClick)
                    MenuButton(text = "Pemilik", onClick = onPemilikClick)
                    MenuButton(text = "Manajer", onClick = onManajerClick)
                }
            }
        }
    }
}

@Composable
fun MenuButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

