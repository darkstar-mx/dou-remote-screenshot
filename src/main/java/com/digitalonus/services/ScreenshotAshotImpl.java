package com.digitalonus.services;

import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * The type Screenshot ashot.
 */
public class ScreenshotAshotImpl implements Screenshot {

    private final WebDriver webDriver;
    private final AShot aShot;

    /**
     * Instantiates a new Screenshot ashot.
     *
     * @param webDriver          the web driver
     * @param aShot              the a shot
     * @param shootingStrategies the shooting strategies
     */
    public ScreenshotAshotImpl(WebDriver webDriver, AShot aShot, ShootingStrategy shootingStrategies) {
        this.webDriver = webDriver;
        this.aShot = aShot;
        this.aShot.shootingStrategy(shootingStrategies);
    }

    @Override
    public File downloadWebPage(String url) throws IOException {
        this.webDriver.get(url);
        ru.yandex.qatools.ashot.Screenshot screenshot = this.aShot
            .takeScreenshot(webDriver);
        File tmp = File.createTempFile(
            "screenshot-" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
            ".png");
        ImageIO.write(screenshot.getImage(), "png", tmp);
        return tmp;
    }

}
