package cmdLine;

import cmdLine.utils.Bash;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by sharath.g on 08/07/15.
 */
public class RestartHbase {
    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        RestartHbase not = new RestartHbase();
        not.go();
    }

    private void go() throws Exception {
        restartHbase();
    }

    private void restartHbase() throws Exception {
        Bash bash = new Bash("/Users/sharath.g/hbase-0.94.27/bin");
        bash.runBashCommand("./stop-hbase.sh");
        bash.runBashCommand("rm -rf ~/dist/*");
        bash.runBashCommand("./start-hbase.sh");

    }
}
