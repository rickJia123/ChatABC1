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
            LogShow.e("BaseBox  BaseBox", e.getMessage());
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
