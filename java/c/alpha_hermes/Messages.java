package c.alpha_hermes;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

/**
 * Created by zeeshan on 17-03-2017.
 */

public class Messages  {



    private String mText;
    private String mName;
    private String mGroupname ;
    private String mSender ;

    public String getmSender() {
        return mSender;
    }

    public void setmSender(String mSender) {
        this.mSender = mSender;
    }

    public String getmGroupname() {
        return mGroupname;
    }

    public void setmGroupname(String mGroupname) {
        this.mGroupname = mGroupname;
    }

    public Messages(String mText, String mName) {

        this.mText = mText;
        this.mName = mName;

    }

    public Messages(String mText)
    {
        this.mText = mText;
    }


public  Messages()
{
    // TODO Well Sort of Nothing
}


    public String getmText()
    {
        return mText;
    }

    public void setmText(String mText)
    {
        this.mText = mText;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }






}
