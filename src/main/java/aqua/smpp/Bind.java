package aqua.smpp;

import com.google.common.collect.ImmutableMap;
import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.smpp.Connection;
import org.smpp.TCPIPConnection;
import org.smpp.TimeoutException;
import org.smpp.WrongSessionStateException;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindTransciever;
import org.smpp.pdu.PDUException;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: isuruanu
 * Date: 10/7/13
 * Time: 11:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Bind implements Command {
    @Override
    public Descriptor getDescriptor() {
        return new Descriptor() {
            @Override
            public String getNamespace() {
                return "syscmd";
            }

            @Override
            public String getName() {
                return "bind";
            }

            @Override
            public String getDescription() {
                return "Bind to SMSC";
            }

            @Override
            public String getUsage() {
                return "bind <ip> <port> <system-id> <password>";
            }

            @Override
            public Map<String, String> getArguments() {
                return null;
            }
        };
    }

    @Override
    public Object execute(Context context) {
        String[] args = (String[]) context.getValue(Context.KEY_COMMAND_LINE_ARGS);

        try {
            String ip = args[0];
            Integer port = Integer.valueOf(args[1]);
            String systemId = args[2];
            String password = args[3];
            Connection connection = new TCPIPConnection(ip, port);

            BindRequest bindRequest = new BindTransciever();
            bindRequest.setSystemId(systemId);
            bindRequest.setPassword(password);
            bindRequest.setInterfaceVersion((byte) 0x34);

            SmppSessionManager.getInstance().addSession(connection, bindRequest);

        } catch (NullPointerException | NumberFormatException e) {
            context.getIoConsole().writeOutput("Error in the input parameters.");
        } catch (PDUException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TimeoutException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (WrongSessionStateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }

    @Override
    public void plug(Context context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
