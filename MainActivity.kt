package com.zerorh.transfer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF163C67)
private val Blue = Color(0xFF1769AA)
private val SoftBlue = Color(0xFFEAF3FC)
private val Green = Color(0xFF16834A)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ZeroRhApp() }
    }
}

@Composable
fun ZeroRhApp() {
    var sharing by remember { mutableStateOf(false) }
    var dark by remember { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = if (dark) darkColorScheme(primary = Color(0xFF90CAF9))
                      else lightColorScheme(primary = Blue, secondary = Navy)
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Wifi, null, tint = Blue, modifier = Modifier.size(28.dp))
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Text("ZeroRh Transfer", fontWeight = FontWeight.Bold)
                                Text("Wireless file sharing", fontSize = 12.sp)
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { dark = !dark }) {
                            Icon(if (dark) Icons.Default.LightMode else Icons.Default.DarkMode, "Toggle theme")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                    Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Connection status", fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                            Surface(color = if (sharing) Color(0xFFDDF4E5) else Color(0xFFE5E9EF), shape = RoundedCornerShape(50)) {
                                Text(if (sharing) "ACTIVE" else "READY", color = if (sharing) Green else Navy,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Text(if (sharing) "Sharing session started" else "Server stopped", color = if (sharing) Green else MaterialTheme.colorScheme.onSurface)
                        Text(if (sharing) "Phase 1 preview: connection server will be added in Phase 2." else "Start sharing when you are ready to connect your laptop.", fontSize = 13.sp)
                        Button(
                            onClick = { sharing = !sharing },
                            modifier = Modifier.fillMaxWidth().height(52.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = if (sharing) Color(0xFFB3261E) else Blue)
                        ) {
                            Icon(if (sharing) Icons.Default.Stop else Icons.Default.PlayArrow, null)
                            Spacer(Modifier.width(8.dp))
                            Text(if (sharing) "STOP SHARING" else "START WIRELESS SHARING", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Card(shape = RoundedCornerShape(18.dp)) {
                    Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Connection address", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(if (sharing) "Server address will appear in Phase 2" else "Not connected", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(onClick = { }, modifier = Modifier.weight(1f), enabled = false) {
                                Icon(Icons.Default.ContentCopy, null)
                                Spacer(Modifier.width(6.dp)); Text("Copy address")
                            }
                            OutlinedButton(onClick = { }, modifier = Modifier.weight(1f), enabled = false) {
                                Icon(Icons.Default.QrCode2, null)
                                Spacer(Modifier.width(6.dp)); Text("QR code")
                            }
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    FeatureCard("Upload to phone", "Laptop → Mobile", Icons.Default.Upload, Modifier.weight(1f))
                    FeatureCard("Download to laptop", "Mobile → Laptop", Icons.Default.Download, Modifier.weight(1f))
                }

                Card(shape = RoundedCornerShape(18.dp)) {
                    Column(Modifier.fillMaxWidth().padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Folder, tint = Blue)
                            Spacer(Modifier.width(8.dp))
                            Text("Received files", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                            Text("0 files", color = Green, fontWeight = FontWeight.SemiBold)
                        }
                        Spacer(Modifier.height(8.dp))
                        Text("Files transferred to your phone will be listed here.", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = SoftBlue)) {
                    Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Info, tint = Navy)
                        Spacer(Modifier.width(10.dp))
                        Text("Phase 1: App dashboard created. Wireless server and browser transfer will be built in the next phases.", fontSize = 13.sp, color = Navy)
                    }
                }
                Spacer(Modifier.weight(1f))
                Text("ZeroRh Transfer • Version 0.1.0", modifier = Modifier.align(Alignment.CenterHorizontally), fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
fun FeatureCard(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.fillMaxWidth().padding(14.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Icon(icon, null, tint = Blue, modifier = Modifier.size(28.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
