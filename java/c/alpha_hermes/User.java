package c.alpha_hermes;

import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.List;

/**
 * Created by zeeshan on 17-03-2017.
 */

public class User  {



    private String mName;
    private String uId;
    private String image ;
    private String last;
    private String time;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public User(String mName, String image, String time, String last) {

        this.mName = mName;
        this.image = image;
        this.time = time;
        this.last = last;
    }
    public  User()
    {

    }
    public User(String mName)
    {
        this.mName = mName ;
    }

}
