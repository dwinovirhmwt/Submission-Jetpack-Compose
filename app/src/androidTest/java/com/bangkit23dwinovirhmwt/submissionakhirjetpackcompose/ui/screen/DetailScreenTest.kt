package com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.ui.screen

import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.R
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class DetailScreenTest{

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun detailScreenTest() {
        composeTestRule.setContent {
            DetailInformation(
                id = 1,
                title = "FROM UP ON POPPY HILL",
                poster = R.drawable.from_up_on_poppy_hill,
                year = "2011",
                genre = "Animation, Family, Family",
                producer = "Studio Ghibli",
                score = "7.4/10",
                synopsis = "From Up on Poppy Hill mengambil latar di Yokohama pada tahun 1963. Berkisah tentang Umi Matsuzaki dan Shun Kazama yang, tanpa mereka sadari, memiliki ikatan keluarga yang kuat. Mereka bekerja sama untuk menyelamatkan klub sastra sekolah mereka dari penghancuran demi mempersiapkan Olimpiade Tokyo. Sementara kisah cinta tumbuh di antara mereka, mereka mengungkap rahasia kelam masa lalu yang dapat mengubah segalanya.",
                isFavorite = false,
                navigateBack = {},
                onFavoriteButtonClicked = { _, _ -> }
            )
        }

        // Example assertions
        composeTestRule.onNodeWithTag("back_home").performClick()
        composeTestRule.onNodeWithTag("favorite_detail_button").assertIsEnabled()
    }
}