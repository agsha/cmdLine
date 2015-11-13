package cmdLine;

import cmdLine.utils.Bash;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by sharath.g on 25/06/15.
 */
public class TestMachines {
    private static final Logger log = LogManager.getLogger();
    public static void main(String[] args) {
        TestMachines cn = new TestMachines();
        cn.printVersions();
    }

    public void printVersions() {
        Bash bash = new Bash("/Users/sharath.g/flipkart/notifications/notification-cp/webservice");

        String fmt = "ssh cp-hedwig-svc%s.nm.flipkart.com \"dpkg -l | grep noti\"";
        for(int i=1; i<=20; i++) {
            if(i==5) continue;
            String cmd = String.format(fmt, i);
            bash.runBashCommand(cmd);
        }

    }
    public void go(String[] args) {
    }
}
