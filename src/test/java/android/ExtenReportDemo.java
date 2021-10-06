package android;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.aspectj.lang.annotation.After;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class ExtenReportDemo {

    ExtentSparkReporter extentSpark;
    ExtentReports extentReports;

    @BeforeSuite
    public void setup() {
        extentSpark = new ExtentSparkReporter("extentDemoReporter.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSpark);
    }

    @AfterSuite
    public void tearDown() {
        extentReports.flush();
    }
}
