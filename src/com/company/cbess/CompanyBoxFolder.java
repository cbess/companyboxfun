package com.company.cbess;

import com.box.sdk.BoxFolder;
import com.box.sdk.BoxItem;

import java.util.ArrayList;

/**
 * Represents a company box folder
 */
public class CompanyBoxFolder {
    // folder traverse depth
    private static final int MAX_DEPTH = 1;
    // only child folders
    private ArrayList<CompanyBoxFolder> mChildFolders = new ArrayList<CompanyBoxFolder>();
    // non-child folders, other folder items (ie. files, etc)
    private ArrayList<BoxItem.Info> mFileItems = new ArrayList<BoxItem.Info>();
    private BoxFolder.Info mBoxFolderInfo;

    public CompanyBoxFolder(BoxFolder.Info boxFolderInfo) {
        mBoxFolderInfo = boxFolderInfo;
    }

    /**
     * Get the underlying folder object.
     * @return BoxFolder ref
     */
    public BoxFolder getBoxFolder() {
        return mBoxFolderInfo.getResource();
    }

    /**
     * Get the name of the folder
     * @return Folder name.
     */
    public String getName() {
        return mBoxFolderInfo.getName();
    }

    /**
     * Populates the folder and child items. Each folder is traversed, populating the getChildFolders() collection.
     * @param populateFolderItems True if the non-folder items (getFileItems()) should be populated.
     */
    public void buildFolderTree(boolean populateFolderItems) {
        getChildFolders().clear();

        // iterate each immediate child folder
        populateChildFolderListingInfo(this, 0, populateFolderItems);
    }

    public void addFolder(CompanyBoxFolder companyBoxFolder) {
        getChildFolders().add(companyBoxFolder);
    }

    public ArrayList<CompanyBoxFolder> getChildFolders() {
        return mChildFolders;
    }

    public ArrayList<BoxItem.Info> getFileItems() {
        return mFileItems;
    }

    @Override
    public String toString() {
        return String.format("%s, FileItems: %d, Folders: %d", getBoxFolder().getInfo().getName(), getFileItems().size(), getChildFolders().size());
    }

    private void populateChildFolderListingInfo(CompanyBoxFolder folder, int depth, boolean populateFileItems) {
        // iterate each immediate child folder
        for (BoxItem.Info itemInfo : getBoxFolder()) {
            // traverse the nested child folders
            if (itemInfo instanceof BoxFolder.Info) {
                //BoxFolder childFolder = (BoxFolder) itemInfo.getResource();
                CompanyBoxFolder childCompanyFolder = new CompanyBoxFolder((BoxFolder.Info) itemInfo);
                folder.addFolder(childCompanyFolder);

                if (depth < MAX_DEPTH) {
                    populateChildFolderListingInfo(childCompanyFolder, depth + 1, populateFileItems);
                }
            } else if (populateFileItems) {
                folder.getFileItems().add(itemInfo);
            }
        }
    }
}
