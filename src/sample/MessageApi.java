package sample;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

/**
 * Created by Jordan on 23/01/2018.
 */
public class MessageApi
{

    private static final String ACCOUNT_SID = "AC9f1b664b9b0aa04be9ff1036b49603cb";
    private static final String AUTH_TOKEN = "bc95847e9b1630d6e4a70066885dc94c";

    static{
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendMessage(String number, String text){
        MessageCreator messageCreator = Message.creator(new PhoneNumber(number),new PhoneNumber("441315104337"),text);
        Message message = messageCreator.create();
        System.out.println(message.getSid());
    }
}
