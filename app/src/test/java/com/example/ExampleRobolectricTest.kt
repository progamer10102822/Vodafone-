package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Vodafone", appName)
  }

  @Test
  fun `verify user initial account state`() {
    val account = com.example.model.UserAccount()
    assertEquals("0542 783 91 24", account.phoneNumber)
    assertEquals(35.0f, account.totalInternetGb)
    assertEquals(false, account.isBillPaid)
  }
}
