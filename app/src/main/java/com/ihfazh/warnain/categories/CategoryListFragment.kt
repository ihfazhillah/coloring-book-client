package com.ihfazh.warnain.categories

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import com.ihfazh.warnain.destinations.CategoryDetailFragmentDestination
import com.ihfazh.warnain.domain.CategoryFilter
import com.ihfazh.warnain.ui.components.ImageCard
import com.ihfazh.warnain.ui.components.TextInput
import com.ihfazh.warnain.ui.theme.WarnainTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination()
@Composable
fun CategoryListFragment(
    navigator: DestinationsNavigator,
    categoriesViewModel: CategoriesViewModel = get()
){

    val searchState = categoriesViewModel.searchState.collectAsState()
    val categories = categoriesViewModel.categories.collectAsLazyPagingItems()
    val filterState = categoriesViewModel.filterState.collectAsState()
    val latestCategories = categoriesViewModel.latestCategories.collectAsState()

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
                    com.ihfazh.warnain.ui.components.FilterChip(text = "Semua", isActive = filterState.value == CategoryFilter.ALL) {
                        categoriesViewModel.updateFilterState(CategoryFilter.ALL)

                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    com.ihfazh.warnain.ui.components.FilterChip(
                        text = "Terakhir",
                        isActive = filterState.value == CategoryFilter.LATEST
                    ) {
                        categoriesViewModel.updateFilterState(CategoryFilter.LATEST)

                    }
                }


                Spacer(modifier = Modifier.height(21.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    when(filterState.value){
                        CategoryFilter.ALL -> {
                            items(categories.itemCount) { index ->
                                categories[index]?.let { category ->
                                    ImageCard(
                                        category = category,
                                        onClick = {
                                            navigator.navigate(
                                                CategoryDetailFragmentDestination(category)
                                            )
                                        }
                                    )
                                }
                            }
                        }

                        CategoryFilter.LATEST -> {
                            items(latestCategories.value){ category ->
                                ImageCard(
                                    category = category,
                                    onClick = {
                                        navigator.navigate(
                                            CategoryDetailFragmentDestination(category)
                                        )
                                    }
                                )
                            }

                        }
                    }

                }

                Spacer(modifier = Modifier.height(40.dp))
            }
            
        }

    )
}

