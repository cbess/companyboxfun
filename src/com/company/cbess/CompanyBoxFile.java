package com.company.cbess;

import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;
import com.box.sdk.ProgressListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents the box file.
 */
public class CompanyBoxFile extends CompanyBoxItem implements CompanyBoxItem.ICompanyBoxItemUploader, CompanyBoxItem.ICompanyBoxItemDownloader {

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

    public BoxFile getBoxFile() {
        return mBoxFileInfo.getResource();
    }

    @Override
    public void upload(String fileName, ProgressListener progressListener) throws IOException {

        // check for file name
        if (fileName == null || fileName.length() <= 0) {
            // create path
            Path path = Paths.get(mLocalFilePath);
            // get file name of original file
            fileName = path.getFileName().toString();
        }

        // read file
        FileInputStream stream = new FileInputStream(mLocalFilePath);
        long fileSize = stream.getChannel().size();

        // TODO: Check if file has already been uploaded. If so then use uploadVersion(), otherwise an exception is thrown
        // perform the file upload
        getBoxFolder().uploadFile(stream, fileName, fileSize, progressListener);

        // close stream
        stream.close();
    }

    @Override
    public void download(OutputStream outputStream, ProgressListener progressListener) {
        getBoxFile().download(outputStream, progressListener);
    }
}
