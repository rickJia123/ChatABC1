package com.xingxiu.frame.database;


import com.xingxiu.frame.BuildConfig;
import com.xingxiu.frame.app.BaseApplication;
import com.xingxiu.frame.database.model.MyObjectBox;
import com.xingxiu.frame.util.log.LogShow;

import io.objectbox.BoxStore;
import io.reactivex.subjects.PublishSubject;

/**
 * * @Author :rickBei
 * * @Date :2019/6/25 16:32
 * * @Descripe: 数据库操作统一管理类
 * 操作指南
 * between :两者之间
 * contains ：包含
 * equal : 相等
 * notEqual :不相等
 * startWith :以什么开头
 * endWith :以什么结尾
 * greater :大于
 * less :小于
 * in ：包含
 * notIn ：不包含
 * isNull :是否null
 * isNotNull ：是否不null
 * and :条件与
 * or :条件或
 * order ：按某属性升序排列返回
 * orderDesc ：按某属性降序返回
 */
public class BoxManager {
    private volatile static BoxManager instance;
    private BoxStore boxStore;
    /**
     * 用于退出状态推送给已打开数据库
     */
    public static PublishSubject<Boolean> subject = PublishSubject.create();

    private BoxManager() {
    }

    public static BoxManager getInstance() {
        if (instance == null) {
            synchronized (BoxManager.class) {
                if (instance == null) {
                    instance = new BoxManager();
                }
            }
        }
        return instance;
    }


    /**
     * 打开指定用户数据库
     */
    public void init() {
        LogShow.i("BoxManager  init");
        try {
            boxStore = MyObjectBox.builder().name("" + BuildConfig.BOX_VERSION).androidContext(BaseApplication.getInstance()).build();
        } catch (Exception e) {
            LogShow.i("BoxManager  init 2", boxStore.isClosed());
        }
        LogShow.i("BoxManager  init over");
    }


    public BoxStore getBoxStore() {
        return boxStore;
    }


}