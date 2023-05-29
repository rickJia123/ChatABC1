package river.chat.lib_core.net.common;

public class ApiConfig {

    //读超时长，单位：秒
   public static final int READ_TIME_OUT = 30;
    //连接时长，单位：秒
    public  static final int CONNECT_TIME_OUT = 30;
    //写超时长，单位：秒
    public  static final int WRITE_TIME_OUT = 30;

    private static final int HOST_TYPE_DEV = 0; //测试环境
    private static final int HOST_TYPE_ONLINE = 1;//生产环境
    private static int mHostType = HOST_TYPE_DEV;

    private static final String HOST_DEV = "http://124.222.175.43:8000/";//测试环境
    private static final String HOST_ONLINE = "http://124.222.175.43:8000/";//生产环境
    private static String mHost = HOST_ONLINE;

    //图片
    private static final String QINIU_HOST_DEV = "https://devimg.imkela.com/";//七牛测试地址
    private static final String QINIU_HOST_ONLINE = "https://img.imkela.com/";//七牛生产地址
    private static String mQiniuHost = QINIU_HOST_ONLINE;

    //webview
    private static final String HOST_WEB_DEV = "https://devm.imkela.com/";
    private static final String HOST_WEB_ONLINE = "https://m.imkela.com/";
    public static String HOST_WEB = HOST_WEB_ONLINE;

    public static String API_KEY = "kelaprod$RFV792f28requestIdvfdsfd2019";

    public static final String USER_PATH = "user-api/"; //用户模块path

    public static boolean isDebug() {
        return mHostType != HOST_TYPE_ONLINE;
    }

    /**
     * 设置运行环境
     */
    public static void init(int hotType) {

        mHostType = hotType;
        switch (mHostType) {
            case HOST_TYPE_DEV:
                mHost = HOST_DEV;
                mQiniuHost = QINIU_HOST_DEV;
                HOST_WEB = HOST_WEB_DEV;
                API_KEY = "kela792f28requestIdvfdsfd2019";
                break;
            case HOST_TYPE_ONLINE:
                mHost = HOST_ONLINE;
                mQiniuHost = QINIU_HOST_ONLINE;
                HOST_WEB = HOST_WEB_ONLINE;
                API_KEY = "kelaprod$RFV792f28requestIdvfdsfd2019";
                break;
        }
    }

    /**
     * 获取域名
     */
    public static String getHost() {
        return mHost;
    }

    /**
     * 获取域名
     */
    public static String getQiniuHost() {
        return mQiniuHost;
    }

}
