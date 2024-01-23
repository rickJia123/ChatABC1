package river.chat.lib_core.storage.database


import io.objectbox.BoxStore
import river.chat.lib_core.app.BaseApplication
import river.chat.lib_resource.model.database.MyObjectBox


/**
 * http://greenrobot.org/greendao/documentation/objectbox-compat/
 *
 * https://github.com/objectbox/objectbox-examples
 *
 * val noteSession = BoxUtils.box.boxFor<Note>()
 * query:  noteSession.query().build().find()
 * remove: noteSession.remove(noteSession.query().build().findFirst())
 * add:    noteSession.put(Note(text = Date().time.toString()))
 */
object BoxUtils {

    val box: BoxStore by lazy {
        MyObjectBox.builder().androidContext(BaseApplication.getInstance()).build()
    }
}