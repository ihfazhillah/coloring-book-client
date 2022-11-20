package com.ihfazh.warnain.category_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ihfazh.warnain.R
import com.ihfazh.warnain.destinations.CategoryDetailFragmentDestination
import com.ihfazh.warnain.domain.Category
import com.ihfazh.warnain.ui.theme.WarnainTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.compose.get

@Destination
@Composable
fun CategoryDetailGridFragment(
    category: Category,
    categoryDetailViewModel: CategoryDetailViewModel = get(),
    navigator: DestinationsNavigator
){
    categoryDetailViewModel.getDetail(category.id)
    val images = categoryDetailViewModel.images.collectAsState()
    val collapsingState = rememberCollapsingToolbarScaffoldState()
    val toolbarState = remember {
        derivedStateOf {
            collapsingState.toolbarState
        }
    }

    WarnainTheme {
        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(), state = collapsingState, scrollStrategy = ScrollStrategy.ExitUntilCollapsed, toolbar = {

                Column (
                    Modifier
                        .graphicsLayer {
                            alpha = toolbarState.value.progress
                        }
                        .background(MaterialTheme.colors.primary)
                        .padding(24.dp)
                ){

                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .width(65.dp),
                            model = category.thumbnail,
                            contentDescription = null
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            category.title,
                            fontSize = MaterialTheme.typography.h4.fontSize,
                            fontWeight = MaterialTheme.typography.h4.fontWeight,
                            color = MaterialTheme.colors.onPrimary,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            "${images.value.size.toString()} ${"image".pluralize(images.value.size)}",
                            fontSize = MaterialTheme.typography.body1.fontSize,
                            fontWeight = MaterialTheme.typography.body1.fontWeight,
                            color = MaterialTheme.colors.onPrimary,
                            textAlign = TextAlign.Center
                        )
                    }
                }


                    TopAppBar (
                        modifier=Modifier
                            .graphicsLayer {
                                           alpha = 1f - (1f * toolbarState.value.progress)
                            }
                        ,
                        contentColor = MaterialTheme.colors.onPrimary,
                        navigationIcon = {
                            IconButton(onClick = {
                                navigator.navigateUp()
                            }) {
                                Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24), contentDescription = "Go Back")
                            }

                        },
                        title = {
                            Column(
                                Modifier.padding(16.dp, 0.dp)
                            ) {
                                Text(
                                    category.title,
                                    fontSize = MaterialTheme.typography.h6.fontSize,
                                    fontWeight = MaterialTheme.typography.h6.fontWeight,
                                    color = MaterialTheme.colors.onPrimary
                                )
                                Text(
                                    "${images.value.size.toString()} ${"image".pluralize(images.value.size)}",
                                    fontSize = MaterialTheme.typography.body2.fontSize,
                                    fontWeight = MaterialTheme.typography.body2.fontWeight,
                                    color = MaterialTheme.colors.onPrimary
                                )
                            }
                        }
                    )


        }
        ) {
            Column(Modifier.padding(16.dp, 0.dp)) {

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(76.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ){
                    itemsIndexed(images.value){index, item ->
                        AsyncImage(
                            model = item.image,
                            contentDescription = "Category $category.title - $index",
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    navigator.navigate(
                                        CategoryDetailFragmentDestination(
                                            category,
                                            index
                                        )
                                    )
                                }
                        )

                    }

                }
            }
        }
        }


    }

fun String.pluralize(size: Int): String {
    if (size > 1) return this + "s"
    return this
}
