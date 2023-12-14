package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        plugin = {"pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "json:target/cucumber.json",
                "timeline:test-output-thread/",
                "rerun:target/failedrerun.txt"
        },
        monochrome = true,
        glue = {"platform"},
        tags = "@healthCheck",

        features = "src/test/resources/features/"

)
public class HealthCheckRunner  extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
