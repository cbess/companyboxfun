package com.company.cbess;

import com.box.sdk.ProgressListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Represents the company box item.
 */
public class CompanyBoxItem {

    /**
     * Provides upload operation
     */
    interface ICompanyBoxItemUploader {

        /**
         * Uploads the content of the implementer
         * @param fileName Desired name to upload the file as, if null original file name is used
         * @param progressListener Can be used to monitor progress of the upload
         * @throws CompanyBoxException
         * @throws IOException
         */
        void upload(String fileName, ProgressListener progressListener) throws CompanyBoxException, IOException;
    }

    /**
     * Provides download operation
     */
    interface ICompanyBoxItemDownloader {
        void download(OutputStream outputStream, ProgressListener progressListener);
    }

}