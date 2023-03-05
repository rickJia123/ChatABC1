package com.xingxiu.frame.database;


import com.xingxiu.frame.cache.Cache;
import com.xingxiu.frame.database.model.UserInfo_;
import com.xingxiu.frame.rx.RxUtil;
import com.xingxiu.frame.rx.task.RxIOTask;
import com.xingxiu.frame.util.log.LogShow;
import com.xingxiu.frame.util.log.LogUtil;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Author :rickBei
 * @Date :2019/6/26 10:49
 * @Descripe: 数据库操作基类
 **/
public abstract class BaseBox<T> {
    //必须要在子类中初始化box
    protected Box<T> box;

    protected abstract Class<T> getEntityClass();

    public BaseBox() {
        try {
            BoxManager boxManager = BoxManager.getInstance();
            BoxStore boxStore = boxManager.getBoxStore();
            if (boxStore == null) {
                boxManager.init();
            }
            box = boxManager.getBoxStore().boxFor(getEntityClass());
            registerExitPublisher();
        } catch (Exception e) {
            LogShow.e("BaseBox  BaseBox", e.getMessage());
            LogUtil.save("baseBox创建异常==" + e.toString());
        }
    }

    /**
     * 注册接收器，接收退出推送
     */
    private void registerExitPublisher() {
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                exit();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        BoxManager.subject.subscribe(observer);
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

    /**
     * 获取当前账户memberid，作为数据库筛选首要条件
     *
     * @return
     */
    protected long getCurrentAccount() {
        return Cache.getUserId();
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
     *
     * @return
     */
    public List<T> getAll() {
        if (box == null)
            return null;
        return box.getAll();
    }

    public T get(long id) {
        if (box == null)
            return null;
        return box.get(id);
    }

    public void remove(long id) {
        if (box == null)
            return;
        box.remove(id);
    }

    public void removeAll() {
        if (box == null)
            return;
        box.removeAll();
    }

    protected abstract void exit();
}
