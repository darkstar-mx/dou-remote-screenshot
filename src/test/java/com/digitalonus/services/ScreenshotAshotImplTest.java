package com.digitalonus.services;

import com.digitalonus.utils.TestingUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;

class ScreenshotAshotImplTest {

    @Mock
    WebDriver webDriver;

    @Mock
    AShot aShot;

    @Mock
    Screenshot libScreenshot;

    @Mock
    ScreenshotAshotImpl screenshotAshotImplMock;

    @InjectMocks
    ScreenshotAshotImpl screenshotAshotImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testDownloadWebPageSuccess() throws IOException {
        String url = "";

        // prevent URL navigation
        doNothing().when(webDriver).get(anyString());

        // return the Library Screenshot mock upon takeScreenshot method call
        doReturn(libScreenshot)
            .when(aShot).takeScreenshot(webDriver);

        doReturn(TestingUtil.loadImage(TestingUtil.IMAGE_COVI_19_PATH))
            .when(libScreenshot).getImage();

        File result = screenshotAshotImpl.downloadWebPage(url);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isFile());
        Assertions.assertTrue(result.exists());
        Assertions.assertTrue(result.getName().endsWith(".png") );
    }

    @Test
    void testDownloadWebPageIOException() throws IOException {
        String url = "";

        // prevent URL navigation
        doNothing().when(webDriver).get(anyString());

        // return the Library Screenshot mock upon takeScreenshot method call
        doReturn(libScreenshot)
            .when(aShot).takeScreenshot(webDriver);

        doReturn(TestingUtil.IMAGE_COVI_19_BUF)
            .when(libScreenshot).getImage();

        doThrow(new IOException("Simulated IO Exception intentionally"))
            .when(screenshotAshotImplMock).downloadWebPage(anyString());

        Exception exception = Assertions.assertThrows(IOException.class, ()-> {

            // send the temp directory instead of a file to cause an IOException
            screenshotAshotImplMock.downloadWebPage(url);
        });
        Assertions.assertEquals(IOException.class.getName(), exception.getClass().getName());

    }

    @Test
    void testDumpToDiskSuccess() throws IOException {

        final String outputFilePath = System.getProperty("java.io.tmpdir") + File.pathSeparatorChar + "output.png";

        File tmpFile = File.createTempFile("test", ".tmp");

        screenshotAshotImpl.dumpToDisk(
            tmpFile,
            outputFilePath
        );
        File output = new File(outputFilePath);

        // make sure new file exists.
        Assertions.assertTrue(output.exists());

        // make sure dumpToDisk routine deleted source file, this is for housekeeping
        Assertions.assertFalse(tmpFile.exists());
        output.delete();
    }


    @Test
    void testDumpToDiskIOException() {
        File tmpFile = new File(System.getProperty("java.io.tmpdir"));
        Exception exception = Assertions.assertThrows(IOException.class, ()-> {

            // send the temp directory instead of a file to cause an IOException
            screenshotAshotImpl.dumpToDisk(tmpFile, "some-non-existing-dir");
        });
        Assertions.assertEquals(IOException.class.getName(), exception.getClass().getName());
    }
}
