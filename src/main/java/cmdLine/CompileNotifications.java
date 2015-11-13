package cmdLine;

import cmdLine.utils.Bash;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static cmdLine.utils.Bash.*;

/**
 * Created by sharath.g on 25/06/15.
 */
public class CompileNotifications {
    private static final Logger log = LogManager.getLogger();
    public static void main(String[] args) {
        CompileNotifications cn = new CompileNotifications();
        cn.go(args);
    }

    public void go(String[] args) {
        Bash bash = new Bash("/Users/sharath.g/flipkart/notifications/notification-cp/webservice");
        bash.runBashCommand("mvn package -DskipTests");
        bash.runBashCommand("sudo -A rm -rf /Users/sharath.g/tomcat6/webapps/hedwigService*");
        bash.runBashCommand("cp target/hedwigService.war /Users/sharath.g/tomcat6/webapps/");
        bash.runBashCommand("sudo -A /Users/sharath.g/tomcat6/bin/catalina.sh run");
    }
}
