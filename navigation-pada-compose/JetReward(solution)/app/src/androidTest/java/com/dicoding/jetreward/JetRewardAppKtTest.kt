package com.dicoding.jetreward

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.dicoding.jetreward.model.FakeRewardDataSource
import com.dicoding.jetreward.ui.navigation.Screen
import com.dicoding.jetreward.ui.theme.JetRewardTheme
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JetRewardAppKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetRewardTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                JetRewardApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRoute(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigateToDetailWithData() {
        composeTestRule.apply {
            onNodeWithTag("RewardList").performScrollToIndex(10)
            onNodeWithText(FakeRewardDataSource.dummyRewards[10].title).performClick()
            navController.assertCurrentRoute(Screen.DetailReward.route)
            onNodeWithText(FakeRewardDataSource.dummyRewards[10].title).assertIsDisplayed()
        }
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.apply {
            onNodeWithStringId(R.string.menu_home).performClick()
            navController.assertCurrentRoute(Screen.Home.route)
            onNodeWithStringId(R.string.menu_cart).performClick()
            navController.assertCurrentRoute(Screen.Cart.route)
            onNodeWithStringId(R.string.menu_profile).performClick()
            navController.assertCurrentRoute(Screen.Profile.route)
        }
    }


}