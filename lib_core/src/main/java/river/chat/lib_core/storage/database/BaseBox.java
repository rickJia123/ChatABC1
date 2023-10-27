package river.chat.lib_core.storage.database;

import java.util.List;

import io.objectbox.Box;
import river.chat.lib_core.utils.log.LogShow;
import river.chat.lib_core.utils.log.LogUtil;

/**
 * @Author :rickBei
 * @Date :2019/6/26 10:49
 * @Descripe: 数据库操作基类
 **/
public abstract class BaseBox<T> {

    protected Box<T> box;

    protected abstract Class<T> getEntityClass();

    public BaseBox() {
        try {
            box = BoxUtils.INSTANCE.getBox()
                    .boxFor(getEntityClass());
        } catch (Exception e) {
            LogUtil.e("BaseBox  BaseBox", e.getMessage());
            LogUtil.save("baseBox创建异常==" + e.toString());
        }
    }

    //存储单个数据
    public long add(T data) {
        synchronized (this) {
            try {
                return box.put(data);
            } catch (Exception e) {
                LogUtil.save("BaseBox add data:" + e);
            }
            return -1;
        }
    }

    //存储数据集合
    public void add(List<T> datas) {
        try {
            box.put(datas);
        } catch (Exception e) {
            LogUtil.save("BaseBox add list:" + e);
        }
    }

    /**
     * 获取到当前box全部数据
     */
    public List<T> getAll() {
        if (box == null) {
            return null;
        }
        return box.getAll();
    }

    public T get(long id) {
        if (box == null) {
            return null;
        }
        return box.get(id);
    }

    public void remove(long id) {
        if (box == null) {
            return;
        }
        box.remove(id);
    }

    public void removeAll() {
        if (box == null) {
            return;
        }
        box.removeAll();
    }

    protected abstract void exit();
}


//// query all notes, sorted a-z by their text
//// (http://greenrobot.org/objectbox/documentation/queries/)
//        notesQuery = notesBox.query().order(Note_.text).build();
//
//                // Reactive query (http://greenrobot.org/objectbox/documentation/data-observers-            reactive-extensions/)
//                notesQuery.subscribe()
//                .onError(new ErrorObserver() {
//@Override
//public void onError(Throwable th) {
//
//        }
//        })
//        // 官方推荐的做法是对 data observers 持有弱引用，防止忘记 cancel subscriptions，
//        // 但是最好还是记得及时 cancel subscriptions(例如在 onPause、onStop 或者
//        // onDestroy 方法中)
//        .weak()
//        .on(AndroidScheduler.mainThread())
//        .observer(new DataObserver<List<Note>>() {
//@Override
//public void onData(List<Note> notes) {
//        // 只要数据库里的数据发生了变化，这里的方法就会被回调执行，相当智能。。。
//        // 业务代码
//        }
//        });