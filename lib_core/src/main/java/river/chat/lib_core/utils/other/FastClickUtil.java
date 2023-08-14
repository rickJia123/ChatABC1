package river.chat.lib_core.utils.other;

public class FastClickUtil {
    private static long currentMills = 0;

    public static boolean isFastClick() {
        if (System.currentTimeMillis() - currentMills > 300) {
            currentMills = System.currentTimeMillis();
            return false;
        }
        return true;
    }
}
