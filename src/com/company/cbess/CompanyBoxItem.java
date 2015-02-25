package com.company.cbess;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by caseybrumbaugh on 2/25/15.
 */
public class CompanyBoxItem {

    /**
     * Provides upload operation
     */
    interface ICompanyBoxItemUploader {
        void upload();
    }

    /**
     * Provides download operation
     */
    interface ICompanyBoxItemDownloader {
        void download(OutputStream outputStream);
    }

}