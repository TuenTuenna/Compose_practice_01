package com.example.randomuserlist.utils

data class RandomUser(
    val name : String = "개발하는 정대리 🙊",
    val description: String = "오늘도 빡코딩 하고 계신가요?",
    val profileImage: String = "https://randomuser.me/api/portraits/women/72.jpg"
)

data class ItemSubCategory(
    val name : String = "Fan",
    val description: String = "오늘 너무 더워요~~",
    var selected: Boolean = false
)

data class ItemCategory(
    val name : String = "Electrical",
    val description: String = "카테고리 설명입니드아!",
    var selected: Boolean = false
)

data class ProductItem(
    val name : String = "Augusta Lamp",
    val description: String = "분위기 잡는데 좋은 램쁘~~~",
    val price: Float = 35.00f,
    var addedFavorite: Boolean = false
)

object DummyDataProvider {

    val userList = List<RandomUser>(200){ RandomUser() }
    val itemSubCategoryList = List<ItemSubCategory>(10){ ItemSubCategory() }
    val itemCategoryList = List<ItemCategory>(10){ ItemCategory() }
    val products = List<ProductItem>(10){ ProductItem() }
}
