package river.chat.lib_core.net.cache
import okhttp3.internal.cache.DiskLruCache
import okhttp3.internal.concurrent.TaskRunner
import okhttp3.internal.io.FileSystem
import okhttp3.internal.threadFactory
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by beiyongChao on 2023/2/22
 * Description:
 */
internal object OkHttpHelper {

    /**
     * 获取diskLruCache
     */
    internal fun getDiskLruCache(
        fileSystem: FileSystem?,
        directory: File?,
        appVersion: Int,
        valueCount: Int,
        maxSize: Long
    ): DiskLruCache {
        val cls = DiskLruCache::class.java
        return try {
            val runnerClass = Class.forName("okhttp3.internal.concurrent.TaskRunner")
            val constructor = cls.getConstructor(
                FileSystem::class.java,
                File::class.java,
                Int::class.java,
                Int::class.java,
                Long::class.java,
                runnerClass
            )
            constructor.newInstance(
                fileSystem,
                directory,
                appVersion,
                valueCount,
                maxSize,
                TaskRunner.INSTANCE
            )
        } catch (e: Exception) {
            try {
                val constructor = cls.getConstructor(
                    FileSystem::class.java,
                    File::class.java,
                    Int::class.java,
                    Int::class.java,
                    Long::class.java,
                    Executor::class.java
                )
                val executor = ThreadPoolExecutor(
                    0, 1, 60L, TimeUnit.SECONDS,
                    LinkedBlockingQueue(), threadFactory("OkHttp DiskLruCache", true)
                )
                constructor.newInstance(
                    fileSystem,
                    directory,
                    appVersion,
                    valueCount,
                    maxSize,
                    executor
                )
            } catch (e: Exception) {
                throw IllegalArgumentException("Please use okhttp 4.0.0 or later")
            }
        }
    }
}