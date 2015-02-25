package com.company.cbess;

import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;

/**
 * Created by caseybrumbaugh on 2/25/15.
 */
public class CompanyBoxFile extends CompanyBoxItem {

    private String mLocalFilePath;

    private BoxFolder.Info mBoxFolderInfo;
    private BoxFile.Info mBoxFileInfo;

    public CompanyBoxFile(BoxFolder boxFolder, String localFilePath) {
        mBoxFolderInfo = boxFolder.getInfo();
        mLocalFilePath = localFilePath;
    }

    public CompanyBoxFile(BoxFile boxFile) {
        mBoxFileInfo = boxFile.getInfo();
    }

    public String getLocalFilePath() {
        return mLocalFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        mLocalFilePath = localFilePath;
    }

    public BoxFolder getBoxFolder() {
        return mBoxFolderInfo.getResource();
    }
}
