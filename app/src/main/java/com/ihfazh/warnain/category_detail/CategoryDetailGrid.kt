package com.ihfazh.warnain.category_detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ihfazh.warnain.destinations.CategoryDetailFragmentDestination
import com.ihfazh.warnain.domain.Category
import com.ihfazh.warnain.ui.theme.WarnainTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
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

    WarnainTheme {
        // todo: expandable toolbar here

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
                                navigator.navigate(CategoryDetailFragmentDestination(category, index))
                            }
                    )

                }

            }
        }
    }

}