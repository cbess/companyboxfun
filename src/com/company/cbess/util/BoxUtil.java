package com.company.cbess.util;

import com.box.sdk.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Box utility functions.
 */
public class BoxUtil {
    /**
     * Gets a list of all subfolders starting from the specified depth.
     * @param folder The root folder.
     * @param depth Should be zero.
     * @return List of folder info instances for each folder
     */
    private static List<BoxItem.Info> getChildFolderListingInfo(BoxFolder folder, int depth) {
        final ArrayList<BoxItem.Info> finalItems = new ArrayList<BoxItem.Info>();

        // iterate each child folder
        for (BoxItem.Info itemInfo : folder) {
            if (itemInfo instanceof BoxFolder.Info) {
                BoxFolder childFolder = (BoxFolder) itemInfo.getResource();
                finalItems.add(itemInfo);

                final int maxDepth = 1;
                if (depth < maxDepth) {
                    finalItems.addAll(getChildFolderListingInfo(childFolder, depth + 1));
                }
            }
        }

        return finalItems;
    }
}
