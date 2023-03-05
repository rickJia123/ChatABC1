package com.xingxiu.frame.database;

import android.text.TextUtils;

import com.xingxiu.frame.cache.Cache;
import com.xingxiu.frame.database.model.InputModel;
import com.xingxiu.frame.database.model.InputModel_;
import com.xingxiu.frame.rx.RxHelper;
import com.xingxiu.frame.rx.RxUtil;
import com.xingxiu.frame.rx.subsciber.BaseSubscriber;
import com.xingxiu.frame.rx.task.RxIOTask;

import io.objectbox.query.Query;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.rong.imlib.model.Conversation;

public class InputBox extends BaseBox<InputModel> {

    private static volatile InputBox mInstance;

    public static InputBox getInstance() {
        if (mInstance == null) {
            synchronized (InputBox.class) {
                if (mInstance == null) {
                    mInstance = new InputBox();
                }
            }
        }
        return mInstance;
    }

    @Override
    protected Class<InputModel> getEntityClass() {
        return InputModel.class;
    }

    @Override
    protected void exit() {
        mInstance = null;
    }

    private int getType(Conversation.ConversationType conversationType) {
        return conversationType == Conversation.ConversationType.PRIVATE ? 0 : 1;
    }

    public InputModel get(Conversation.ConversationType conversationType, String targetId) {
        if (box == null) {
            return null;
        }
        return box.query().equal(InputModel_.memberId, getCurrentAccount()).equal(InputModel_.type, getType(conversationType)).equal(InputModel_.targetId, targetId).build().findFirst();
    }

    public void add(Conversation.ConversationType conversationType, String targetId, String content) {
        if (conversationType == null || TextUtils.isEmpty(targetId) || TextUtils.isEmpty(content)) {
            return;
        }
        RxUtil.doInIOThread(new RxIOTask<String>("") {
            @Override
            public Void doInIOThread(String t) {
                long acount = Cache.getUserInfo().getMemberId();
                InputModel inputModel = get(conversationType, targetId);
                if (inputModel == null) {
                    inputModel = new InputModel(acount, conversationType, targetId, content);
                    add(inputModel);
                } else {
                    inputModel.setContent(content);
                }
                add(inputModel);
                return null;
            }
        });
    }
}