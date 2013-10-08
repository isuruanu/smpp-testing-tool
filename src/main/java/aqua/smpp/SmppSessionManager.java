package aqua.smpp;

import org.smpp.*;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.BindTransciever;
import org.smpp.pdu.PDUException;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: isuruanu
 * Date: 10/7/13
 * Time: 11:33 PM
 * To change this template use File | Settings | File Templates.
 */
public final class SmppSessionManager {

    private static SmppSessionManager smppSessionManager = null;
    private Session session = null;

    private SmppSessionManager() {
    }

    public static synchronized SmppSessionManager getInstance() {
        if (smppSessionManager == null) {
            smppSessionManager = new SmppSessionManager();
        }
        return smppSessionManager;
    }

    public synchronized void addSession(Connection connection, BindRequest bindRequest) throws PDUException, TimeoutException, WrongSessionStateException, IOException {
        if (session == null) {
            this.session = new Session(connection);
        } else if (session.isBound()) {
            session.unbind();
            session.close();
            session = new Session(connection);
        } else if (session.isOpened()) {
            session.close();
            session = new Session(connection);
        }

        bind(session, bindRequest);
    }

    private void bind(Session session, BindRequest bindRequest) throws IOException, WrongSessionStateException, PDUException, TimeoutException {
        session.open();
        BindResponse response = session.bind(bindRequest);
        boolean bindSuccess = response.getCommandStatus() == Data.ESME_ROK;
        if (!bindSuccess) {
            System.out.println("Bind failed");
            session.close();
        }
    }

    public Session getAvailableSession() throws Exception {
        if (session != null && session.isBound()) {
            return session;
        } else {
            throw new Exception("No Bounded session found.");
        }

    }
}