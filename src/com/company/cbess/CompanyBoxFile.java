package com.company.cbess;

import com.box.sdk.BoxFile;
import com.box.sdk.BoxItem;
import com.box.sdk.ProgressListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * Represents the box file.
 */
public class CompanyBoxFile extends CompanyBoxItem implements CompanyBoxItem.ICompanyBoxItemUploader, CompanyBoxItem.ICompanyBoxItemDownloader {

    private String mLocalFilePath;
    private CompanyBoxFolder mCompanyBoxFolder;
    private BoxFile.Info mBoxFileInfo;

    public CompanyBoxFile(CompanyBoxFolder companyBoxFolder, String localFilePath) {
        mCompanyBoxFolder = companyBoxFolder;
        mLocalFilePath = localFilePath;
    }

    public CompanyBoxFile(BoxFile boxFile) {
        mBoxFileInfo = boxFile.getInfo();
    }

    public String getLocalFilePath() {
        return mLocalFilePath;
    }

    public CompanyBoxFolder getCompanyBoxFolder() {
        return mCompanyBoxFolder;
    }

    public BoxFile getBoxFile() {
        return mBoxFileInfo.getResource();
    }

    @Override
    public void upload(String fileName, ProgressListener progressListener) throws CompanyBoxException, IOException {

        // check for file name
        if (!isFileNameValid(fileName)) {
            // create path
//            Path path = Paths.get(getLocalFilePath());
//            // get file name of original file
//            fileName = path.getFileName().toString();
        }

        // make sure that there is a valid file name
        if (!isFileNameValid(fileName)) {
            throw new CompanyBoxException("Could not determine file name.");
        }

        // read file to upload
        FileInputStream stream = new FileInputStream(getLocalFilePath());
        long fileSize = stream.getChannel().size();
        
        // build list of files from directory
        getCompanyBoxFolder().buildFolderTree(true);

        // determine if file to be uploaded already exists
        // if it does exist get the BoxFile so it can be updated
        CompanyBoxFile companyBoxFile = getCompanyBoxFolder().findFileByName(fileName);

        // update existing file, or upload it for the first time
        if (companyBoxFile != null) {
            // upload a new version of the file
            companyBoxFile.getBoxFile().uploadVersion(stream, new Date(), fileSize, progressListener);
        } else {
            // perform initial file upload
            getCompanyBoxFolder().getBoxFolder().uploadFile(stream, fileName, fileSize, progressListener);
        }

        // close stream
        stream.close();
    }

    /**
     * Determines if file name is valid
     * @param fileName name of the file to analyze
     * @return boolean indicating the file name's validity
     */
    private boolean isFileNameValid(String fileName) {
        if (fileName == null || fileName.length() <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public void download(OutputStream outputStream, ProgressListener progressListener) {
        getBoxFile().download(outputStream, progressListener);
    }
}
