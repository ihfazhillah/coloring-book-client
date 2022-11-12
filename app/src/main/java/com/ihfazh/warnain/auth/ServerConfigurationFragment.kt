package com.ihfazh.warnain.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ihfazh.warnain.R
import com.ihfazh.warnain.destinations.CategoryListFragmentDestination
import com.ihfazh.warnain.destinations.ServerConfigurationFragmentDestination
import com.ihfazh.warnain.ui.components.TextInput
import com.ihfazh.warnain.ui.theme.WarnainTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel


@RootNavGraph(start = true)
@Destination
@Composable
fun ServerConfigurationFragment(
    serverConfigurationViewModel: ServerConfigurationViewModel = getViewModel(),
    navigator: DestinationsNavigator
){

    val endpoint = serverConfigurationViewModel.endpoint.collectAsState()
    val success = serverConfigurationViewModel.success.collectAsState()

    if (success.value){
        navigator.clearBackStack(ServerConfigurationFragmentDestination)
        navigator.navigate(CategoryListFragmentDestination, true){

            popUpTo(CategoryListFragmentDestination.route){
                inclusive = true
            }
        }
    }

    WarnainTheme {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                "Konfigurasi Server",
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontStyle = MaterialTheme.typography.h6.fontStyle
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextInput(
                value = endpoint.value,
                onChange = {
                    serverConfigurationViewModel.updateEndpoint(it)
                },
                label = "Alamat Server",
                iconPainter = painterResource(id = R.drawable.ic_baseline_qr_code_24)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Masukkan alamat IP Server. Atau kamu dapat menggunakan QR Code scanner.",
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.caption.fontSize,
                fontStyle = MaterialTheme.typography.caption.fontStyle
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    serverConfigurationViewModel.login()
                }
            ) {
                Text("Masuk")
            }

        }

    }

}