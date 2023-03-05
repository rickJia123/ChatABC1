package river.chat.businese_common.dataBase.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * 用户信息
 * Create by Circle on 2019/7/24
 */
@Entity
public class UserInfo implements Parcelable {
    @Id(assignable = true)
    long memberId;//主键

    long account;//当前账户
    String nickname;//昵称
    String headPhoto; //头像
    String extra;//扩充信息
    int sex;//性别  1-男，0-女


    public UserInfo() {
    }

    public UserInfo(long memberId, String nickname, String headPhoto) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.headPhoto = headPhoto;
    }

    public UserInfo(long memberId, String nickname, String headPhoto, String extra) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.headPhoto = headPhoto;
        this.extra = extra;
    }

    public UserInfo(long memberId, String nickname, String headPhoto, int sex, String extra) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.headPhoto = headPhoto;
        this.sex = sex;
        this.extra = extra;
    }

    public long getAccount() {
        return account;
    }

    public void setAccount(long account) {
        this.account = account;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public boolean isMan() {
        return sex == 1;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "memberId=" + memberId +
                ", nickname='" + nickname + '\'' +
                ", headPhoto='" + headPhoto + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.memberId);
        dest.writeLong(this.account);
        dest.writeString(this.nickname);
        dest.writeString(this.headPhoto);
        dest.writeString(this.extra);
        dest.writeInt(this.sex);
    }

    protected UserInfo(Parcel in) {
        this.memberId = in.readLong();
        this.account = in.readLong();
        this.nickname = in.readString();
        this.headPhoto = in.readString();
        this.extra = in.readString();
        this.sex = in.readInt();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
