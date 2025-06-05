package com.angellevyne0045.miniproject3.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.angellevyne0045.miniproject3.R
import com.angellevyne0045.miniproject3.model.Buku
import com.angellevyne0045.miniproject3.ui.theme.MiniProject3Theme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ){  innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier){

    val viewModel: MainViewModel = viewModel()
    val data by viewModel.data



    LazyVerticalGrid(
        modifier = modifier.fillMaxSize().padding(4.dp),
        columns = GridCells.Fixed(2),
    ){
        items(data) { ListItem(buku = it) }
    }
}

@Composable
fun ListItem(buku: Buku) {
    Box(
        modifier = Modifier.padding(4.dp).border(1.dp, Color.Gray)
    ){
        AsyncImage(
            model = buku.coverUrl,
            contentDescription = stringResource(R.string.gambar, buku.title),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    MiniProject3Theme {
        MainScreen()
    }
}