package com.ihfazh.warnain.print_running

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
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
import com.ihfazh.warnain.destinations.CategoryListFragmentDestination
import com.ihfazh.warnain.domain.Category
import com.ihfazh.warnain.domain.CategoryDetail
import com.ihfazh.warnain.ui.theme.WarnainTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DestinationStyle


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination()
@Composable
fun PrintRunningFragment(
    category: Category,
    categoryDetail: CategoryDetail,
    navigator: DestinationsNavigator
){
    Scaffold(){
        Column(
            Modifier.fillMaxSize(),
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
                        model = categoryDetail.image,
                        contentDescription = categoryDetail.source,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Column(){
                        Text(
                            text = "Yeay, Gambar Sedang Diprint",
                            fontWeight = MaterialTheme.typography.h5.fontWeight,
                            fontSize = MaterialTheme.typography.h5.fontSize,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Tunggu sebentar sampai proses print selesai. Kamu bisa lihat lihat yang lainnya.",
                            fontWeight = MaterialTheme.typography.body2.fontWeight,
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.body2.fontSize
                        )
                    }

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ){
                        OutlinedButton(onClick = { navigator.popBackStack() }) {
                            Text("Kembali")
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
                            Text("Lihat Lihat")
                        }
                    }

                }

            }
        }

    }

}

