package com.digitalonus;

import com.digitalonus.services.Screenshot;
import com.digitalonus.services.ScreenshotAshotImpl;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * The type Application.
 *
 * Some URL examples to try:
 *  https://www.nytimes.com/2020/02/14/world/asia/china-coronavirus-doctors.html?action=click&module=Top%20Stories&pgtype=Homepage
 *  https://www.cdc.gov/coronavirus/2019-ncov/php/risk-assessment.html
 *  https://edition.cnn.com
 *  https://weather.com
 */
public class Application {

    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String FIREFOX_ON_MAC_DRIVER = USER_DIR + File.separatorChar + "geckodriver";
    private static final String FIREFOX_ON_WIN_DRIVER = USER_DIR + File.separatorChar + "geckodriver.exe";

    public static void main(String[] args) {

        String inputURL = showInputDialog("Introduce the URL you want the screenshot from");

        selectDriverEnvironment();

        try {
            String path;

            do {
                path = showInputDialog("Provide the screenshot name.\nIt will be saved under: " + USER_DIR);
            } while (path.isEmpty());

            path = validateSavingFileName(path);

            WebDriver driver = getFirefoxWebDriver();

            Screenshot screenshot = new ScreenshotAshotImpl(driver, new AShot(), ShootingStrategies.viewportPasting(300));

            // open the web page
            File file = screenshot.downloadWebPage(inputURL);

            // close the driver, screenshot was already captured and no longer needed.
            driver.close();

            // save the screenshot to a file
            screenshot.dumpToDisk(file, path);

            showMessageDialog("File saved successfully to: " + path);

        } catch (IOException e) {
            showMessageDialog(e.getMessage());
        }
    }

    private static WebDriver getFirefoxWebDriver() {
        FirefoxDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    private static void selectDriverEnvironment() {
        // check OS name: mac, windows, etc
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac")) {
            System.setProperty("webdriver.gecko.driver", FIREFOX_ON_MAC_DRIVER);
        } else {
            // assume for now it is windows
            System.setProperty("webdriver.gecko.driver", FIREFOX_ON_WIN_DRIVER);
        }
    }

    private static String validateSavingFileName(String filename) {
        StringBuilder outputPath = new StringBuilder(USER_DIR);

        outputPath
            .append(File.separatorChar)
            .append(filename);

        // check for file extension as .png
        if (!FilenameUtils.getExtension(filename).equalsIgnoreCase("png")) {
            outputPath.append(".png");
        }
        return outputPath.toString();
    }

    private static void showMessageDialog(final String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    static String showInputDialog(final String message) {
        return JOptionPane.showInputDialog(null, message);
    }

}
