package river.chat.businese_common.dataBase.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.rong.imlib.model.Conversation;

/**
 * Create by Circle on 2019/9/10
 */
@Entity
public class InputModel implements Parcelable {
    @Id
    long id;
    int type = 0;//0:私聊 1
    String targetId;
    String content;
    long memberId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }


    public InputModel(long account, Conversation.ConversationType conversationType, String targetId, String content) {
        type = conversationType == Conversation.ConversationType.PRIVATE ? 0 : 1;
        this.targetId = targetId;
        memberId = account;
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeInt(this.type);
        dest.writeString(this.targetId);
        dest.writeString(this.content);
        dest.writeLong(this.memberId);
    }

    public InputModel() {
    }

    protected InputModel(Parcel in) {
        this.id = in.readLong();
        this.type = in.readInt();
        this.targetId = in.readString();
        this.content = in.readString();
        this.memberId = in.readLong();
    }

    public static final Creator<InputModel> CREATOR = new Creator<InputModel>() {
        @Override
        public InputModel createFromParcel(Parcel source) {
            return new InputModel(source);
        }

        @Override
        public InputModel[] newArray(int size) {
            return new InputModel[size];
        }
    };
}
