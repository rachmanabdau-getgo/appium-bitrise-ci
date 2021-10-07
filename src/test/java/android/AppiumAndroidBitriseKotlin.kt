package android

import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.MobileCapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.testng.Assert
import org.testng.annotations.AfterSuite
import org.testng.annotations.BeforeSuite
import org.testng.annotations.Test
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.TimeUnit


@Test
class AppiumAndroidBitriseKotlin {

    companion object {
        var url: URL? = null
        var capabilities: DesiredCapabilities? = null
        var driver: AndroidDriver<MobileElement>? = null
    }

    @BeforeSuite
    @Throws(MalformedURLException::class)
    fun setupAppium() {
        val URL_STRING = "http://localhost:4723/wd/hub"
        url = URL(URL_STRING)
        capabilities = DesiredCapabilities()
        capabilities?.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android")
        capabilities?.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.0")
        capabilities?.setCapability(MobileCapabilityType.DEVICE_NAME, "pixel")
        capabilities?.setCapability(MobileCapabilityType.APP, System.getenv("BITRISE_APK_PATH"))
        capabilities?.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2")
        driver = AndroidDriver(url, capabilities)
        driver?.manage()?.timeouts()?.implicitlyWait(90000, TimeUnit.MILLISECONDS)
    }

    @AfterSuite
    @Throws(InterruptedException::class)
    fun uninstallApp() {
        driver?.removeApp("com.example.testapplication")
        driver?.quit()
    }

    @Test
    fun sampleTest() {
        val textView = driver?.findElementByAccessibilityId("labelTest")

        Assert.assertTrue(textView?.text == "Hello World!")
    }
}