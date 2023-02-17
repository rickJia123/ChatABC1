package river.chat.lib_core.storage;


/**
 * @Author :rickBei
 * @Date :2019/6/27 10:20
 * @Descripe: 存储类型
 **/

public enum StorageType {
    TYPE_LOG(DirectoryName.LOG_DIRECTORY_NAME),
    TYPE_SKIN(DirectoryName.SKIN_DIRECTORY_NAME),
    TYPE_TEMP(DirectoryName.TEMP_DIRECTORY_NAME),
    TYPE_MSG_IMAGE_THUMB(DirectoryName.MSG_MSG_IMAGE_THUMB_DIRECTORY_NAME),
    TYPE_MSG_AUDIO(DirectoryName.MSG_MSG_AUDIO_DIRECTORY_NAME),
    TYPE_MSG_VIDEO(DirectoryName.MSG_MSG_VIDEO_DIRECTORY_NAME),
    TYPE_VERSION(DirectoryName.MSG_VERSION),
    TYPE_FACE(DirectoryName.MSG_FACE);


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
        SKIN_DIRECTORY_NAME(".skin/"),
        TEMP_DIRECTORY_NAME("temp/"),
        MSG_MSG_IMAGE_THUMB_DIRECTORY_NAME("temp/chat/image/thumb/"),
        MSG_MSG_AUDIO_DIRECTORY_NAME(".temp/chat/audio/"),
        MSG_MSG_VIDEO_DIRECTORY_NAME(".temp/chat/video/"),
        MSG_VERSION(".temp/version/"),
        MSG_FACE(".temp/face/");

        private String path;

        public String getPath() {
            return path;
        }

        DirectoryName(String path) {
            this.path = path;
        }
    }
}
