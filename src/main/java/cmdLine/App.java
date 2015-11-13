package cmdLine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.Arrays;

import static cmdLine.utils.Bash.*;
/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger log = LogManager.getLogger();

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    public static void main( String[] args )
    {
        App app = new App();
        app.go(args);
    }

    private void go(String[] args) {
        log.debug(Arrays.toString(args));
    }
}
