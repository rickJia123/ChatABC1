package com.xingxiu.frame.database;
import com.xingxiu.frame.database.model.GroupInfo;
import com.xingxiu.frame.database.model.GroupInfo_;


public class GroupBox extends BaseBox<GroupInfo> {

    private static volatile GroupBox mInstance;

    public static GroupBox getInstance() {
        if (mInstance == null) {
            synchronized (GroupBox.class) {
                if (mInstance == null) {
                    mInstance = new GroupBox();
                }
            }
        }
        return mInstance;
    }

    @Override
    protected void exit() {
        mInstance = null;
    }

    @Override
    protected Class<GroupInfo> getEntityClass() {
        return GroupInfo.class;
    }

    public GroupInfo getByGroupId(long groupId) {
        if (box == null)
            return null;
        return box.query().equal(GroupInfo_.account, getCurrentAccount()).equal(GroupInfo_.groupId, groupId).build().findFirst();
    }

    /**
     * @param groupId
     * @param state   1=正常 2=封群 3=解散
     */
    public void setState(long groupId, int state) {
        GroupInfo groupInfo = getByGroupId(groupId);
        if (groupInfo != null) {
            groupInfo.setGroupStatus(state);
            add(groupInfo);
        }
    }


    public void updateSendRedPacketData(GroupInfo info) {
        if (info == null) return;
        GroupInfo groupInfo = getByGroupId(info.getGroupId());
        if (groupInfo != null) {
            groupInfo.setRedPacketMoney(info.getRedPacketMoney());
            groupInfo.setRedPacketTime(info.getRedPacketTime());
            add(groupInfo);
        } else {
            add(info);
        }
    }

    public void updateGroupName(GroupInfo info) {
        if (info == null) return;
        GroupInfo groupInfo = getByGroupId(info.getGroupId());
        if (groupInfo != null) {
            groupInfo.setGroupName(info.getGroupName());
            add(groupInfo);
        } else {
            add(info);
        }
    }

    @Override
    public void remove(long groupId) {
        if (box == null) {
            return;
        }
        box.query().equal(GroupInfo_.account, getCurrentAccount()).equal(GroupInfo_.groupId, groupId).build().remove();
    }

    @Override
    public long add(GroupInfo data) {
        data.setAccount(getCurrentAccount());
        return super.add(data);
    }
}