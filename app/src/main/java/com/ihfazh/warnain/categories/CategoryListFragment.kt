package com.ihfazh.warnain.categories

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState.Error
import androidx.paging.compose.collectAsLazyPagingItems
import com.ihfazh.warnain.destinations.CategoryDetailGridFragmentDestination
import com.ihfazh.warnain.destinations.NoConnectionFragmentDestination
import com.ihfazh.warnain.destinations.ServerConfigurationFragmentDestination
import com.ihfazh.warnain.domain.CategorySorter
import com.ihfazh.warnain.ui.components.ImageCard
import com.ihfazh.warnain.ui.components.TextInput
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import kotlinx.coroutines.flow.collect
import org.koin.androidx.compose.get
import java.net.ConnectException
import java.net.UnknownHostException

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RootNavGraph(start = true)
@Destination
@Composable
fun CategoryListFragment(
    navigator: DestinationsNavigator,
    categoriesViewModel: CategoriesViewModel = get(),
    loginResult: ResultRecipient<ServerConfigurationFragmentDestination, Boolean>
){

    val searchState = categoriesViewModel.searchState.collectAsState()
    val categories = categoriesViewModel.categories.collectAsLazyPagingItems()
    val sorterState = categoriesViewModel.sorterState.collectAsState()

    LaunchedEffect("hasToken", categories.loadState.source) {
        val noToken = !categoriesViewModel.hasToken()
        val unknownHostError = categories.loadState.source.refresh is Error && (categories.loadState.source.refresh as Error).error is UnknownHostException
        if (noToken || unknownHostError){
            navigator.navigate(ServerConfigurationFragmentDestination.route)
        }

        val connectionError = listOf(
            (categories.loadState.source.refresh is Error && (categories.loadState.source.refresh as Error).error is ConnectException),
            (categories.loadState.source.append is Error && (categories.loadState.source.append as Error).error is ConnectException),
            (categories.loadState.source.prepend is Error && (categories.loadState.source.prepend as Error).error is ConnectException),
        ).any { it -> it }

        if (connectionError){
            navigator.navigate(NoConnectionFragmentDestination)
        }
    }

    Scaffold(
        contentColor = MaterialTheme.colors.onPrimary,
        topBar = {
            TopAppBar(
                contentColor = MaterialTheme.colors.onPrimary
            ) {
                Text(
                    "Warnain",
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontWeight = MaterialTheme.typography.h6.fontWeight,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colors.onPrimary
                )
            }
        },
        content = {
            Column(
                modifier = Modifier.padding(16.dp, 0.dp)
            ) {

                Spacer(modifier = Modifier.height(42.dp))
                TextInput(
                    value = searchState.value,
                    placeHolder = "Aku mau mewarnai...",
                    onChange = {
                        categoriesViewModel.updateSearch(it)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(21.dp))

                Row {

                    com.ihfazh.warnain.ui.components.FilterChip(
                        text = "Alphabet", isActive = sorterState.value == CategorySorter.TITLE) {
                        categoriesViewModel.updateSorterState(CategorySorter.TITLE)

                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    com.ihfazh.warnain.ui.components.FilterChip(
                        text = "Terakhir",
                        isActive = sorterState.value == CategorySorter.ACCESS
                    ) {
                        categoriesViewModel.updateSorterState(CategorySorter.ACCESS)

                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    com.ihfazh.warnain.ui.components.FilterChip(
                        text = "Paling Sering",
                        isActive = sorterState.value == CategorySorter.FREQ
                    ) {
                        categoriesViewModel.updateSorterState(CategorySorter.FREQ)

                    }
                }


                Spacer(modifier = Modifier.height(21.dp))

                LazyVerticalGrid(
                   state = persistedLazyScrollState(viewModel = categoriesViewModel),
                    columns = GridCells.Adaptive(150.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxSize()

                ) {

                    items(categories.itemCount) { index ->
                        categories[index]?.let { category ->
                            ImageCard(
                                category = category,
                                onClick = {
                                    navigator.navigate(
                                        CategoryDetailGridFragmentDestination(category)
                                    )
//                                            navigator.navigate(
//                                                CategoryDetailGridDestination(category)
//                                            )
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
            
        }

    )
}


@Composable
fun persistedLazyScrollState(viewModel: CategoriesViewModel): LazyGridState {
    val scrollState = rememberLazyGridState(viewModel.firstVisibleItemIndex, viewModel.firstVisibleItemOffset)
    DisposableEffect(key1 = null) {
        onDispose {
            viewModel.firstVisibleItemIndex = scrollState.firstVisibleItemIndex
            viewModel.firstVisibleItemOffset = scrollState.firstVisibleItemScrollOffset
        }
    }
    return scrollState
}