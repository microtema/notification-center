package de.seven.fate.sse.resource;

import de.seven.fate.event.EntityAddEvent;
import de.seven.fate.message.event.MessageEventData;
import de.seven.fate.person.enums.MessageType;
import org.apache.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mario on 17.02.2016.
 */
@WebServlet(value = "/sse", asyncSupported = true)
public class SSEServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SSEServlet.class);

    private static final Map<String, PrintWriter> BROADCAST = new HashMap<>();


    protected void service(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

        AsyncContext actx = req.startAsync();
        actx.setTimeout(TimeUnit.HOURS.toMillis(1));

        ServletResponse actxResponse = actx.getResponse();

        actxResponse.setContentType("text/event-stream");
        actxResponse.setCharacterEncoding("UTF-8");

        PrintWriter writer = actxResponse.getWriter();

        sentComment(actxResponse.getWriter(), "connect", Boolean.TRUE);

        BROADCAST.put(getUserName(req), writer);
    }

    /**
     * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * Message Events
     * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     *
     * @param eventData
     */
    protected void onMessageChangeEvent(@Observes @EntityAddEvent MessageEventData eventData) {

        broadcastMessage(eventData.getLdapId());
    }

    private void broadcastMessage(String ldapId) {
        logger.debug("broadcast outbound event on data: " + ldapId);

        PrintWriter writer = BROADCAST.get(ldapId);

        if (writer == null) {
            return;
        }

        sentComment(writer, "message", MessageType.PUBLISHED);

    }


    private void sentComment(PrintWriter writer, String command, Object data) {

        writer.write("event: " + command + "\n");
        writer.write("data: " + data + "\n\n");

        writer.flush();
    }

    public void destroy() {
        BROADCAST.clear();
    }

    private String getUserName(ServletRequest req) {
        return "mtema";
    }
}
