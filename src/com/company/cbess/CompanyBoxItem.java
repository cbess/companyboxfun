package com.company.cbess;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by caseybrumbaugh on 2/25/15.
 */
public class CompanyBoxItem {

    /**
     * Provides upload operation
     */
    interface ICompanyBoxItemUploader {
        void upload(String fileName) throws IOException;
    }

    /**
     * Provides download operation
     */
    interface ICompanyBoxItemDownloader {
        void download(OutputStream outputStream);
    }

}