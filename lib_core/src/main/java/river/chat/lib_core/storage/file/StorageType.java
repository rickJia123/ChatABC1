package river.chat.lib_core.storage.file;


/**
 * @Author :rickBei
 * @Date :2019/6/27 10:20
 * @Descripe: 存储类型
 **/

public enum StorageType {
    TYPE_LOG(DirectoryName.LOG_DIRECTORY_NAME),
    TYPE_TEMP(DirectoryName.TEMP_DIRECTORY_NAME),
    TYPE_NET(DirectoryName.TEMP_DIRECTORY_NET),
    TYPE_MSG_IMAGE_THUMB(DirectoryName.MSG_MSG_IMAGE_THUMB_DIRECTORY_NAME);


    private DirectoryName storageDirectoryName;
    private long storageMinSize;

    public String getStoragePath() {
        return storageDirectoryName.getPath();
    }

    public long getStorageMinSize() {
        return storageMinSize;
    }

    StorageType(DirectoryName dirName) {
        this(dirName, StorageUtil.THRESHOLD_MIN_SPCAE);
    }

    StorageType(DirectoryName dirName, long storageMinSize) {
        this.storageDirectoryName = dirName;
        this.storageMinSize = storageMinSize;
    }

    enum DirectoryName {
        LOG_DIRECTORY_NAME("log/"),
        TEMP_DIRECTORY_NAME("temp/"),
        TEMP_DIRECTORY_NET("net/"),
        MSG_MSG_IMAGE_THUMB_DIRECTORY_NAME("temp/chat/image/thumb/");

        private String path;

        public String getPath() {
            return path;
        }

        DirectoryName(String path) {
            this.path = path;
        }
    }
}
