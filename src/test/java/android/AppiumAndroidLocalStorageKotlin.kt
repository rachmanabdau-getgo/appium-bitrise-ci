package android

import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.MediaEntityBuilder
import com.aventstack.extentreports.Status
import com.aventstack.extentreports.reporter.ExtentSparkReporter
import com.aventstack.extentreports.reporter.configuration.ViewName
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.MobileCapabilityType
import org.apache.commons.io.FileUtils
import org.openqa.selenium.OutputType
import org.openqa.selenium.remote.DesiredCapabilities
import org.testng.annotations.AfterSuite
import org.testng.annotations.BeforeSuite
import org.testng.annotations.Test
import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


@Test
class AppiumAndroidLocalStorageKotlin {

    companion object {
        var url: URL? = null
        var capabilities: DesiredCapabilities? = null
        var driver: AndroidDriver<MobileElement>? = null

        var extentSpark: ExtentSparkReporter? = null
        var extentReports: ExtentReports? = null
    }

    @BeforeSuite
    @Throws(MalformedURLException::class)
    fun setupAppium() {
        val URL_STRING = "http://127.0.0.1:4723/wd/hub"
        url = URL(URL_STRING) //3
        capabilities = DesiredCapabilities()
        capabilities?.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel 4")
        // capabilities?.setCapability(MobileCapabilityType.UDID, "emulator-5554")
        capabilities?.setCapability(MobileCapabilityType.APP, "D:\\Downloads\\app-debug.apk")
        // capabilities?.setCapability(MobileCapabilityType.NO_RESET, true)
        capabilities?.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2") //4
        driver = AndroidDriver(url, capabilities)
        driver?.manage()?.timeouts()?.implicitlyWait(2, TimeUnit.SECONDS)

        extentSpark = ExtentSparkReporter("extentDemoReporter.html") .viewConfigurer()
            .viewOrder()
            .`as`(arrayOf (
                ViewName.DASHBOARD,
                ViewName.TEST,
                ViewName.AUTHOR,
                ViewName.DEVICE,
                ViewName.EXCEPTION,
                ViewName.LOG
            ))
            .apply()
        extentReports = ExtentReports()
        extentReports?.attachReporter(extentSpark)
    }

    @AfterSuite
    @Throws(InterruptedException::class)
    fun uninstallApp() {
        // driver!!.resetApp()
        driver?.removeApp("com.example.testapplication")
        extentReports?.flush()
    }

    @Test
    fun sampleTest() {
        val testRecorder = extentReports?.createTest("SampleTestOne")
            ?.assignAuthor("Rachman")

        val actualValue = driver?.findElementByAccessibilityId("labelTest")
        val expectedValue = "Hello World!"

        val labelValueStatus = if (actualValue?.text == expectedValue) Status.PASS else Status.FAIL
        val labelVisibilityStatus = if (actualValue?.isDisplayed == true) Status.PASS else Status.FAIL

        testRecorder?.log(labelValueStatus, "label should show 'Hello World!'")
            ?.log(labelVisibilityStatus, "label should be visible to user")
    }

    @Test
    fun sampleTestTwo() {
        val testRecorder = extentReports?.createTest("SampleTestTwo")
            ?.assignAuthor("Rachman")

        val actualValue = driver?.findElementByAccessibilityId("labelTest")
        val expectedValue = "Hello World"

        val labelValueStatus = if (actualValue?.text == expectedValue) Status.PASS else Status.FAIL
        val labelVisibilityStatus = if (actualValue?.isDisplayed == true) Status.PASS else Status.FAIL

        testRecorder?.log(labelValueStatus, "label should show 'Hello World'")
            ?.pass(MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot(driver)).build())
        testRecorder?.log(labelVisibilityStatus, "label should be visible to user")
            ?.fail(MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot(driver)).build())
    }

    @Test
    fun sampleTestThree() {
        val testRecorder = extentReports?.createTest("SampleTestThree")
            ?.assignAuthor("Rachman")

        Thread.sleep(3000)

        val actualValue = driver?.findElementByAccessibilityId("labelTest")
        val expectedValue = "Hello World"

        val labelValueStatus = if (actualValue?.text == expectedValue) Status.PASS else Status.FAIL
        val labelVisibilityStatus = if (actualValue?.isDisplayed == false) Status.PASS else Status.FAIL

        testRecorder?.log(labelValueStatus, "label should show 'Hello World!'")
            ?.log(labelVisibilityStatus, "label should be visible to user")
    }

    @Throws(IOException::class)
    fun getScreenshot(d: AndroidDriver<MobileElement>?) : String {
        val sdf = SimpleDateFormat("dd_MM_yyyy_hh_mm_ss")
        val date = Date()
        val fileName = "${sdf.format(date)}.png"
        val filePath = "./screenshot/$fileName"

        val des = d?.getScreenshotAs(OutputType.FILE)
        FileUtils.copyFile(des, File(filePath))
        println(filePath)

        return filePath
    }
}