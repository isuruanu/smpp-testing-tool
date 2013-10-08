package aqua.smpp;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.smpp.Data;
import org.smpp.Session;
import org.smpp.pdu.SubmitSM;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: isuruanu
 * Date: 10/8/13
 * Time: 8:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class SubmitOne implements Command {
    @Override
    public Descriptor getDescriptor() {
        return new Descriptor() {
            @Override
            public String getNamespace() {
                return "syscmd";
            }

            @Override
            public String getName() {
                return "submitone";
            }

            @Override
            public String getDescription() {
                return "Submit Sm";
            }

            @Override
            public String getUsage() {
                return "submit <sender-addr> <reciever-addr> <message>";
            }

            @Override
            public Map<String, String> getArguments() {
                return null;
            }
        };
    }

    @Override
    public Object execute(Context ctx) {
        String[] args = (String[]) ctx.getValue(Context.KEY_COMMAND_LINE_ARGS);
        try {
            Session session = SmppSessionManager.getInstance().getAvailableSession();
            String source = args[0];
            String dest = args[1];
            String message = args[2];

            SubmitSM submitSM = new SubmitSM();
            submitSM.setSourceAddr((byte)1, (byte)1, source);
            submitSM.setDestAddr((byte) 1, (byte) 1, dest);
            submitSM.setShortMessage(message);
            session.submit(submitSM);

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }

    @Override
    public void plug(Context plug) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
