package com.ihfazh.warnain.no_connection

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavOptionsBuilder
import coil.compose.AsyncImage
import com.ihfazh.warnain.R
import com.ihfazh.warnain.destinations.CategoryListFragmentDestination
import com.ihfazh.warnain.destinations.ServerConfigurationFragmentDestination
import com.ihfazh.warnain.domain.Category
import com.ihfazh.warnain.domain.CategoryDetail
import com.ihfazh.warnain.ui.theme.WarnainTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DestinationStyle
import org.koin.androidx.compose.get


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination()
@Composable
fun NoConnectionFragment(
    navigator: DestinationsNavigator,
    viewModel: NoConnectionViewModel = get()
){
    val scrollState = rememberScrollState()
    Scaffold(){
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

            Box(
                Modifier.width(250.dp),
                contentAlignment = Alignment.Center,

            ){

                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ){

                    AsyncImage(
                        model = R.drawable.komputer,
                        contentDescription = "No Connection",
                        modifier = Modifier.fillMaxWidth()
                    )

                    Column(){
                        Text(
                            text = "Tidak Ada Sambungan",
                            fontWeight = MaterialTheme.typography.h5.fontWeight,
                            fontSize = MaterialTheme.typography.h5.fontSize,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Pastikan aplikasi di komputer sudah berjalan.",
                            fontWeight = MaterialTheme.typography.body2.fontWeight,
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.body2.fontSize
                        )
                    }

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ){
                        OutlinedButton(onClick = {
                            viewModel.removeToken()
                            navigator.navigate(ServerConfigurationFragmentDestination.route)
                        }) {
                            Text("Keluar")
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Button(onClick = {
                            navigator.navigate(
                                CategoryListFragmentDestination,
                                true,
                            ) {
                                popUpTo(CategoryListFragmentDestination.route){
                                    inclusive = true
                                }
                            }
                        }) {
                            Text("Coba Lagi")
                        }
                    }

                }

            }
        }

    }

}

