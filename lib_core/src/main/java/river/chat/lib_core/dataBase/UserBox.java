package com.xingxiu.frame.database;

import com.xingxiu.frame.database.model.UserInfo;
import com.xingxiu.frame.database.model.UserInfo_;

public class UserBox extends BaseBox<UserInfo> {

    private static volatile UserBox mInstance;

    public static UserBox getInstance() {
        if (mInstance == null) {
            synchronized (UserBox.class) {
                if (mInstance == null) {
                    mInstance = new UserBox();
                }
            }
        }
        return mInstance;
    }

    @Override
    protected Class<UserInfo> getEntityClass() {
        return UserInfo.class;
    }

    @Override
    protected void exit() {
        mInstance = null;
    }

    @Override
    public UserInfo get(long id) {
        if (box == null)
            return null;

        return box.query().equal(UserInfo_.memberId, id).build().findFirst();
    }

    @Override
    public long add(UserInfo data) {
        return super.add(data);
    }
}