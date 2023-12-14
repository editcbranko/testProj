package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;


@CucumberOptions(
        features = {"@target/failedrerun.txt"},
        glue = {"platform"},
        plugin = {"pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "timeline:test-output-thread/",
                "rerun:target/failedrerun.txt"
        },
        monochrome = true


)
public class FailedRerun extends AbstractTestNGCucumberTests {

        @AfterSuite
        public synchronized void setReporterName() throws IOException {
                String fileName = FailedRerun.class.getName() + new Date().toString().replaceAll(" ", "_").replaceAll(":", "-");
                Path sourceHtml = Paths.get("test-output/spark/index.html");
                Path sourcePdf = Paths.get("test-output/pdf/index.pdf");
                Files.move(sourceHtml, sourceHtml.resolveSibling(fileName + ".html"));
                Files.move(sourcePdf, sourcePdf.resolveSibling(fileName + ".pdf"));
        }
}
