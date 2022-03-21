package com.hackaprende.dogedex.doglist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.hackaprende.dogedex.R
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.composables.BackNavigationIcon
import com.hackaprende.dogedex.composables.ErrorDialog
import com.hackaprende.dogedex.composables.LoadingWheel
import com.hackaprende.dogedex.model.Dog

private const val GRID_SPAN_COUNT = 3

@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun DogListScreen(
    onNavigationIconClick: () -> Unit,
    dogList: List<Dog>,
    onDogClicked: (Dog) -> Unit,
    status: ApiResponseStatus<Any>? = null,
    onErrorDialogDismiss: () -> Unit,
) {
    Scaffold(
        topBar = { DogListScreenTopBar(onNavigationIconClick) }
    ) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(GRID_SPAN_COUNT),
            content = {
                items(dogList) {
                    DogGridItem(dog = it, onDogClicked)
                }
            }
        )
    }

    if (status is ApiResponseStatus.Loading) {
        LoadingWheel()
    } else if (status is ApiResponseStatus.Error) {
        ErrorDialog(status.messageId, onErrorDialogDismiss)
    }
}

@Composable
fun DogListScreenTopBar(
    onClick: () -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(R.string.my_dog_collection)) },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        navigationIcon = { BackNavigationIcon(onClick) }
    )
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun DogGridItem(dog: Dog, onDogClicked: (Dog) -> Unit) {
    if (dog.inCollection) {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            onClick = { onDogClicked(dog) },
            shape = RoundedCornerShape(4.dp)
        ) {
            Image(
                painter = rememberImagePainter(dog.imageUrl),
                contentDescription = null,
                modifier = Modifier.background(Color.White)
            )
        }
    } else {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            color = Color.Red,
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = dog.index.toString(),
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontSize = 42.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}