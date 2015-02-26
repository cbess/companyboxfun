package com.company.cbess;

import com.box.sdk.ProgressListener;

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
        void upload(String fileName, ProgressListener progressListener) throws IOException;
    }

    /**
     * Provides download operation
     */
    interface ICompanyBoxItemDownloader {
        void download(OutputStream outputStream);
    }

}