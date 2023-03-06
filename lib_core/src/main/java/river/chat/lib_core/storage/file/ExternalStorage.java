package river.chat.lib_core.storage.file;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

import river.chat.lib_core.app.BaseApplication;

/**
 * @Author :rickBei
 * @Date :2019/6/27 10:21
 * @Descripe: 存储操作类
 **/
public class ExternalStorage {

    private static String storageRootName = "river";
    /**
     * 外部存储目录
     */
    private String storageRoot;

    private static final String PUBLIC_COMMON_ROOT = Environment.getExternalStorageDirectory().getPath() + File.separator
            + storageRootName + File.separator;
    private static final String PUBLIC_PRIVATE_ROOT = BaseApplication.getInstance().getExternalFilesDir(storageRootName).getPath()+ File.separator;
    private static final String PRIVATE_ROOT = BaseApplication.getInstance().getFilesDir().getPath() + File.separator;
    private static ExternalStorage instance;

    private static final String TAG = "ExternalStorage";

    private boolean hasPermission = true; // 是否拥有存储卡权限

    private Context context;


    private ExternalStorage() {

    }

    synchronized public static ExternalStorage getInstance() {
        if (instance == null) {
            instance = new ExternalStorage();
        }
        return instance;
    }

    void init(Context context) {
        this.context = context;
        storageRoot = PRIVATE_ROOT;
        createSubFolders();
    }

    private void loadStorageState(Context context) {
        String externalPath = Environment.getExternalStorageDirectory().getPath();
        this.storageRoot = externalPath + "/" + storageRootName + "/";
    }

    //创建子目录，根据 enum 中创建
    private void createSubFolders() {
        boolean result = true;
        File root = new File(storageRoot);
        if (root.exists() && !root.isDirectory()) {
            root.delete();
        }
        for (StorageType storageType : StorageType.values()) {
            result &= makeDirectory(storageRoot + storageType.getStoragePath());
        }
        if (result) {
            createNoMediaFile(storageRoot);
        }
    }

    /**
     * 创建目录
     *
     * @param path
     * @return
     */
    private boolean makeDirectory(String path) {
        File file = new File(path);
        boolean exist = file.exists();
        if (!exist) {
            exist = file.mkdirs();
        }
        return exist;
    }

    protected static String NO_MEDIA_FILE_NAME = ".nomedia";

    private void createNoMediaFile(String path) {
        File noMediaFile = new File(path + "/" + NO_MEDIA_FILE_NAME);
        try {
            if (!noMediaFile.exists()) {
                noMediaFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件全名转绝对路径（写）
     *
     * @param fileName 文件全名（文件名.扩展名）
     * @return 返回绝对路径信息
     */
    String getWritePath(String fileName, StorageType fileType) {
        return pathForName(fileName, fileType, false, false);
    }

    private String pathForName(String fileName, StorageType type, boolean dir,
                               boolean check) {
        String directory = getDirectoryByDirType(type);
        StringBuilder path = new StringBuilder(directory);

        if (!dir) {
            path.append(fileName);
        }

        String pathString = path.toString();
        File file = new File(pathString);

        if (check) {
            if (file.exists()) {
                if ((dir && file.isDirectory())
                        || (!dir && !file.isDirectory())) {
                    return pathString;
                }
            }

            return "";
        } else {
            return pathString;
        }
    }

    /**
     * 返回指定类型的文件夹路径
     *
     * @param fileType
     * @return
     */
    String getDirectoryByDirType(StorageType fileType) {
        return storageRoot + fileType.getStoragePath();
    }

    public String getStorageRoot() {
        return storageRoot;
    }

    /**
     * 根据输入的文件名和类型，找到该文件的全路径。
     *
     * @param fileName
     * @param fileType
     * @return 如果存在该文件，返回路径，否则返回空
     */
    String getReadPath(String fileName, StorageType fileType) {
        if (TextUtils.isEmpty(fileName)) {
            return "";
        }

        return pathForName(fileName, fileType, false, true);
    }

    boolean isSdkStorageReady() {
        String externalRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
        if (this.storageRoot.startsWith(externalRoot)) {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        } else {
            return true;
        }
    }

    /**
     * 获取外置存储卡剩余空间
     *
     * @return
     */
    long getAvailableExternalSize() {
        return getResidualSpace(storageRoot);
    }

    /**
     * 获取目录剩余空间
     *
     * @param directoryPath
     * @return
     */
    private long getResidualSpace(String directoryPath) {
        try {
            StatFs sf = new StatFs(directoryPath);
            long blockSize = sf.getBlockSize();
            long availCount = sf.getAvailableBlocks();
            long availCountByte = availCount * blockSize;
            return availCountByte;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * SD卡存储权限检查
     */
    private boolean checkPermission(Context context) {
        if (context == null) {
            return false;
        }

        // 写权限有了默认就赋予了读权限
        PackageManager pm = context.getPackageManager();
        if (PackageManager.PERMISSION_GRANTED !=
                pm.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, context.getApplicationInfo().packageName)) {
            return false;
        }

        return true;
    }

    /**
     * 有效性检查
     * 如果已经获取权限，就检查是否已经创建子文件夹
     */
    boolean checkStorageValid() {
        if (hasPermission) {
            return true; // M以下版本&授权过的M版本不需要检查
        }

        hasPermission = checkPermission(context); // 检查是否已经获取权限了
        if (hasPermission) {
            // 已经重新获得权限，那么重新检查一遍初始化过程
            createSubFolders();
        }
        return hasPermission;
    }
}


