package com.ihfazh.warnain.category_detail

import android.annotation.SuppressLint
import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.util.lerp
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.ihfazh.warnain.R
import com.ihfazh.warnain.destinations.PrintRunningFragmentDestination
import com.ihfazh.warnain.domain.Category
import com.ihfazh.warnain.ui.components.FilterChip
import com.ihfazh.warnain.ui.components.ImageCard
import com.ihfazh.warnain.ui.components.Stepper
import com.ihfazh.warnain.ui.components.TextInput
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun CategoryDetailFragment(
    category: Category,
    page: Int = 0,
    categoryDetailViewModel: CategoryDetailViewModel = get(),
    navigator: DestinationsNavigator
) {

    categoryDetailViewModel.getDetail(category.id)
    val count = categoryDetailViewModel.count.collectAsState()
    val pagerState = rememberPagerState(page)

    Scaffold(

        contentColor = MaterialTheme.colors.onPrimary,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        category.title,
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = MaterialTheme.typography.h6.fontWeight,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colors.onPrimary
                    )
                },
                contentColor = MaterialTheme.colors.onPrimary,
                navigationIcon = {
                    IconButton(onClick = { navigator.navigateUp() }) {
                        Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24), contentDescription = "Go Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(painter = painterResource(id = R.drawable.ic_baseline_info_24), contentDescription = "INFO", tint = MaterialTheme.colors.onPrimary)
                    }
                }
            )
        },
        content = {
            val images = categoryDetailViewModel.images.collectAsState()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp, 0.dp)
            ) {
                HorizontalPager(
                    count = images.value.size,
                    modifier = Modifier.fillMaxSize(),
                    state = pagerState
                ) { page ->
                    AsyncImage(
                        model = images.value[page].image,
                        contentDescription = "Image position ${page}",
                        modifier = Modifier
                            .graphicsLayer {
                                // Calculate the absolute offset for the current page from the
                                // scroll position. We use the absolute value which allows us to mirror
                                // any effects for both directions
                                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                                // We animate the scaleX + scaleY, between 85% and 100%
                                lerp(
                                    start = 0.85f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                ).also { scale ->
                                    scaleX = scale
                                    scaleY = scale
                                }

                                // We animate the alpha, between 50% and 100%
                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            }
                    )
                }



                Row(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(0.dp, 32.dp, 0.dp, 16.dp),
                    horizontalArrangement = Arrangement.End

                ){
                    Stepper(onChange = {
                        categoryDetailViewModel.setCount(it)
                    }, value = count.value)

                    Spacer(Modifier.width(16.dp))

                    Button(modifier = Modifier
                        .height(TextFieldDefaults.MinHeight),
                        onClick = {
                            images.value[pagerState.currentPage].let { detail ->
                                categoryDetailViewModel.print(detail.id)
                                navigator.navigate(PrintRunningFragmentDestination(category, detail))
                            }
                        }) {
                        Text("Print")
                    }
                }

            }

        }

    )
}