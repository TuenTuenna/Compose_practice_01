package com.example.randomuserlist

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.randomuserlist.ui.theme.MyGreen
import com.example.randomuserlist.ui.theme.RandomUserListTheme
import com.example.randomuserlist.utils.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomUserListTheme {
                ContentView()
            }
        }
    }
}

@Composable
fun MyAppBar(){
    TopAppBar(elevation = 10.dp,
        backgroundColor = MaterialTheme.colors.primary,
//        backgroundColor = MyGreen,
        modifier = Modifier.height(58.dp)
    ) {
        Text(
            text = stringResource(id = R.string.my_app_name),
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically),
            fontSize = 18.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
fun ContentView(){
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
//        Scaffold(backgroundColor = Color.White,
//            topBar = { MyAppBar()
//            }
//        ) {
////            RandomUserListView(randomUsers = DummyDataProvider.userList)
////            SimpleScreen()
//            BeautifulUIPractice()
//        }
        BeautifulUIPracticeContainer()
    }
}


@Composable
fun RandomUserListView(randomUsers: List<RandomUser>){
    // 메모리 관리가 들어간 LazyColumn
    LazyColumn(){
//        items(randomUsers){ aRandomUser ->
//            RandomUserView(aRandomUser)
//        }
        items(randomUsers){ RandomUserView(it) }
    }
}

@Composable
fun RandomUserView(randomUser: RandomUser){
    val typography = MaterialTheme.typography
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
//            Box(modifier =
//                Modifier
//                    .size(width = 60.dp, height = 60.dp)
//                    .clip(CircleShape)
//                    .background(Color.Red)
//            )
            ProfileImg(imgUrl = randomUser.profileImage)
            Column() {
                Text(text = randomUser.name,
                    style = typography.subtitle1)
                Text(text = randomUser.description,
                    style = typography.body1)
            }
        }
    }
}

@Composable
fun ProfileImg(imgUrl: String, modifier: Modifier = Modifier){
    // 이미지 비트맵
    val bitmap : MutableState<Bitmap?> = mutableStateOf(null)

    // 이미지 모디파이어
    val imageModifier = modifier
        .size(50.dp, 50.dp)
//        .clip(RoundedCornerShape(10.dp))
        .clip(CircleShape)

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(imgUrl)
        .into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmap.value = resource
            }
            override fun onLoadCleared(placeholder: Drawable?) { }
        })

    // 비트 맵이 있다면
    bitmap.value?.asImageBitmap()?.let { fetchedBitmap ->
        Image(bitmap = fetchedBitmap,
            contentScale = ContentScale.Fit,
            contentDescription = null,
            modifier = imageModifier
        )
    } ?: Image(painter = painterResource(id = R.drawable.ic_empty_user_img),
        contentScale = ContentScale.Fit,
        contentDescription = null,
        modifier = imageModifier
    )
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RandomUserListTheme {
        ContentView()
    }
}


class SimpleTFViewmodel : ViewModel() {

    val name : MutableState<String> = mutableStateOf("")

//    private val _name = MutableStateFlow<String>("")
//    val name = _name.asStateFlow()
//
//    fun onNameChange(newValue : String){
//        _name.value = newValue
//    }

}

//
@Composable
fun SimpleScreen(simpleTFViewmodel: SimpleTFViewmodel = SimpleTFViewmodel()){
    val name : String = simpleTFViewmodel.name.value
    Column(modifier = Modifier.padding()){
        simpleTextfield( name= name, onValueChange= {
            Log.d("로그", "SimpleScreen: onValueChange / $it")
            simpleTFViewmodel.name.value = it
        } )
        Text(text = name)
    }
}


@Composable
fun simpleTextfield(name : String, onValueChange : (String) -> Unit ){

    OutlinedTextField(
        value = name ,
        onValueChange = onValueChange,
        label = { Text(text = "Name") }
    )
}

@Composable
fun TopNav(modifier: Modifier = Modifier){
    // top nav
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ){
        Box(
            Modifier
                .weight(1f)
                .height(80.dp)
                .background(Color.Yellow)
        )
        Box(
            Modifier
                .width(90.dp)
                .height(80.dp)
                .background(Color.Green)
        )
        Box(
            Modifier
                .width(90.dp)
                .height(80.dp)
                .background(Color.Blue)
        )
    } // top row
}

@Composable
fun DummyBox(){
    Box(
        Modifier
            .width(90.dp)
            .height(80.dp)
            .background(Color.Magenta)
    )
}

@Composable
fun BottomNav(modifier: Modifier = Modifier){
    // bottom nav
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ){
        Box(
            Modifier
                .width(90.dp)
                .height(80.dp)
                .background(Color.Red)
        )
        Box(
            Modifier
                .width(90.dp)
                .height(80.dp)
                .background(Color.Yellow)
        )
        Box(
            Modifier
                .width(90.dp)
                .height(80.dp)
                .background(Color.Green)
        )
        Box(
            Modifier
                .width(90.dp)
                .height(80.dp)
                .background(Color.Blue)
        )
    } // bottom row
}

@Composable
fun SubCategoryView(subCategory: ItemSubCategory){
    Box(
        Modifier
            .size(80.dp)
//            .padding(vertical = 10.dp)
            .background(Color.LightGray)
    ){
        Text(text = subCategory.name,
        modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun RandomSubCategoryList(randomSubCategries: List<ItemSubCategory>){
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp),){
        items(randomSubCategries){ SubCategoryView(it) }
    }
}

@Composable
fun CircleDot(shape: Shape, modifier: Modifier){
    Column(modifier = modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.Center)
        .clip(shape)) {
        Box( modifier = Modifier
            .size(10.dp)
            .background(Color.Red))
    }
}

@Composable
fun CategoryView(category: ItemCategory){
    Column(
        Modifier
            .size(80.dp)
//            .padding(vertical = 10.dp)
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = category.name)
        CircleDot(shape = CircleShape,
            modifier = Modifier.padding(top = 10.dp))
    }
}

@Composable
fun RandomCategoryList(randomCategries: List<ItemCategory>){
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp),){
        items(randomCategries){ CategoryView(it) }
    }
}

@Composable
fun ProductView(product: ProductItem){
    Column(
        Modifier
//            .width(300.dp)
            .aspectRatio(1.2F / 2F)
            .fillMaxHeight()
            .background(Color.Green),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        DummyBox()
        Text(text = product.name)
    }
}

@Composable
fun RandomProductsList(randomProducts: List<ProductItem>, modifier: Modifier){
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),){
        items(randomProducts){ ProductView(it) }
    }
}

// ui 연습하는 컨테이너
@Composable
fun BeautifulUIPracticeContainer(){
    Column(
        modifier = Modifier
            .fillMaxWidth() // 가로 모두 채우기
            .fillMaxHeight() // 세로 모두 채우기
            .background(Color.Cyan),
        verticalArrangement = Arrangement.SpaceBetween
    )
        {
        TopNav() // 탑 네브
        RandomCategoryList(DummyDataProvider.itemCategoryList)
        RandomProductsList(DummyDataProvider.products,
            modifier = Modifier
                .weight(0.8f)
                .padding(vertical = 20.dp))
        RandomSubCategoryList(DummyDataProvider.itemSubCategoryList)
        BottomNav() // 바텀 네브
    }
}
