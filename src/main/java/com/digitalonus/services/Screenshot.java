package com.digitalonus.services;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * The interface Screenshot.
 */
public interface Screenshot {

    /**
     * Download web page file.
     *
     * @param url the url
     * @return the file
     * @throws IOException the io exception
     */
    File downloadWebPage(String url) throws IOException;

    /**
     * Dump to disk.
     *
     * @param sourceFile the source file
     * @param outputPath the output path
     * @throws IOException the io exception
     */
    default void dumpToDisk(File sourceFile, String outputPath) throws IOException {
        File destinationFile = new File(outputPath);
        FileUtils.copyFile(sourceFile,destinationFile);
        sourceFile.delete();
    }

}
