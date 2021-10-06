package android;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Test
public class AppiumAndroidRemoteApk {
    public static URL url;
    public static DesiredCapabilities capabilities;
    public static AndroidDriver driver;  //1
    public static String getgoPackageName = "sg.getgo.booking.dev";

    @BeforeSuite
    public void setupAppium() throws MalformedURLException {
        String getgoBitriseLink = "https://bitrise-prod-build-storage.s3.amazonaws.com/builds/cfd2a0b3-6224-4958-b08d-1fedec453999/artifacts/87362836/Getgo-uat-release.apk?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIV2YZWMVCNWNR2HA%2F20210825%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210825T022049Z&X-Amz-Expires=3600&X-Amz-SignedHeaders=host&X-Amz-Signature=eb6763b4fc8d334e5cdf55a9ae800e5686090e0a93e8a613f919552b14b244db";
        final String URL_STRING = "http://127.0.0.1:4723/wd/hub";
        url = new URL(URL_STRING);

        capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel 4");
        capabilities.setCapability(MobileCapabilityType.APP, getgoBitriseLink);
        capabilities.setCapability("appPackage", getgoPackageName);
        //capabilities.setCapability("appActivity", /*getgoPackageName +*/ ".entry.main.EntryActivity");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");

        driver = new AndroidDriver(url, capabilities);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.resetApp();
    }  //5

    @AfterSuite
    public void uninstallApp() throws InterruptedException {
        driver.removeApp(getgoPackageName);
    }  //6

    @Test (enabled=true) public void checkLoginTextButton() throws InterruptedException {
        Thread.sleep(5000);
        MobileElement el1 = (MobileElement) driver.findElementsByAccessibilityId("btnNext");

        Assert.assertEquals(el1.getText(), "Next");
    }
}
