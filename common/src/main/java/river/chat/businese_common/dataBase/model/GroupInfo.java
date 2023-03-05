package river.chat.businese_common.dataBase.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Transient;

/**
 * Create by Circle on 2019/7/24
 */
@Entity
public class GroupInfo implements Parcelable {
    @Id(assignable = true)
    long groupId;

    long account;
    String groupName;
    String groupHeadLogo;
    String groupIntroduce;//群介绍
    long groupOwnerId;//群主ID
    String groupOwnerName;//群主昵称
    int groupStatus;//群状态：1=正常 2=封群 3=解散
    int starLevel;//群等级
    int groupNum;//群人数
    int receivedLevel;//接受等级
    int sendLevel;//发送等级
    int girlNum;
    int manNum;
    long redPacketMoney;//发送红包钻石数
    String redPacketTime;//发送时间

    @Transient
    UserInfo groupMembers;

    public GroupInfo() {
    }


    public long getAccount() {
        return account;
    }

    public void setAccount(long account) {
        this.account = account;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupHeadLogo() {
        return groupHeadLogo;
    }

    public void setGroupHeadLogo(String groupHeadLogo) {
        this.groupHeadLogo = groupHeadLogo;
    }

    public String getGroupIntroduce() {
        return groupIntroduce;
    }

    public void setGroupIntroduce(String groupIntroduce) {
        this.groupIntroduce = groupIntroduce;
    }

    public long getGroupOwnerId() {
        return groupOwnerId;
    }

    public void setGroupOwnerId(long groupOwnerId) {
        this.groupOwnerId = groupOwnerId;
    }

    public String getGroupOwnerName() {
        return groupOwnerName;
    }

    public void setGroupOwnerName(String groupOwnerName) {
        this.groupOwnerName = groupOwnerName;
    }

    public int getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(int groupStatus) {
        this.groupStatus = groupStatus;
    }

    public int getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(int starLevel) {
        this.starLevel = starLevel;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
        if (groupNum < 0) {
            groupNum = 0;
        }
    }

    public int getGirlNum() {
        return girlNum;
    }

    public void setGirlNum(int girlNum) {
        this.girlNum = girlNum;
        if (girlNum < 0)
            girlNum = 0;
    }

    public int getManNum() {
        return manNum;
    }

    public void setManNum(int manNum) {
        this.manNum = manNum;
        if (manNum < 0)
            manNum = 0;
    }

    public int getReceivedLevel() {
        return receivedLevel;
    }

    public void setReceivedLevel(int receivedLevel) {
        this.receivedLevel = receivedLevel;
    }

    public int getSendLevel() {
        return sendLevel;
    }

    public void setSendLevel(int sendLevel) {
        this.sendLevel = sendLevel;
    }

    public UserInfo getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(UserInfo groupMembers) {
        this.groupMembers = groupMembers;
    }

    public long getRedPacketMoney() {
        return redPacketMoney;
    }

    public void setRedPacketMoney(long redPacketMoney) {
        this.redPacketMoney = redPacketMoney;
    }

    public String getRedPacketTime() {
        return redPacketTime;
    }

    public void setRedPacketTime(String redPacketTime) {
        this.redPacketTime = redPacketTime;
    }



    @Override
    public String toString() {
        return "GroupInfo{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", groupHeadLogo='" + groupHeadLogo + '\'' +
                ", groupIntroduce='" + groupIntroduce + '\'' +
                ", groupOwnerId=" + groupOwnerId +
                ", groupOwnerName='" + groupOwnerName + '\'' +
                ", groupStatus=" + groupStatus +
                ", starLevel=" + starLevel +
                ", groupNum=" + groupNum +
                ", girlNum=" + girlNum +
                ", manNum=" + manNum +
                ", redPacketMoney=" + redPacketMoney +
                ", redPacketTime='" + redPacketTime + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.groupId);
        dest.writeLong(this.account);
        dest.writeString(this.groupName);
        dest.writeString(this.groupHeadLogo);
        dest.writeString(this.groupIntroduce);
        dest.writeLong(this.groupOwnerId);
        dest.writeString(this.groupOwnerName);
        dest.writeInt(this.groupStatus);
        dest.writeInt(this.starLevel);
        dest.writeInt(this.groupNum);
        dest.writeInt(this.receivedLevel);
        dest.writeInt(this.sendLevel);
        dest.writeInt(this.girlNum);
        dest.writeInt(this.manNum);
        dest.writeLong(this.redPacketMoney);
        dest.writeString(this.redPacketTime);
        dest.writeParcelable(this.groupMembers, flags);
    }

    protected GroupInfo(Parcel in) {
        this.groupId = in.readLong();
        this.account = in.readLong();
        this.groupName = in.readString();
        this.groupHeadLogo = in.readString();
        this.groupIntroduce = in.readString();
        this.groupOwnerId = in.readLong();
        this.groupOwnerName = in.readString();
        this.groupStatus = in.readInt();
        this.starLevel = in.readInt();
        this.groupNum = in.readInt();
        this.receivedLevel = in.readInt();
        this.sendLevel = in.readInt();
        this.girlNum = in.readInt();
        this.manNum = in.readInt();
        this.redPacketMoney = in.readLong();
        this.redPacketTime = in.readString();
        this.groupMembers = in.readParcelable(UserInfo.class.getClassLoader());
    }

    public static final Creator<GroupInfo> CREATOR = new Creator<GroupInfo>() {
        @Override
        public GroupInfo createFromParcel(Parcel source) {
            return new GroupInfo(source);
        }

        @Override
        public GroupInfo[] newArray(int size) {
            return new GroupInfo[size];
        }
    };
}
