package cmdLine;

import cmdLine.utils.Bash;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by sharath.g on 08/07/15.
 */
public class FlipJavaVersion {
    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        FlipJavaVersion not = new FlipJavaVersion();
        not.go(args[0]);
    }

    private void go(String args) throws Exception {
        hello(args);
    }

    String java8Home = "/Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk";
    String bashProfile = "/Users/sharath.g/.bash_profile";
    private void hello(String args) throws Exception {
        Bash bash = new Bash("/Users/sharath.g/");
        if(!Files.exists(Paths.get("/Users/sharath.g/java8"))) {
            throw new RuntimeException("java8 folder expected in /Users/sharath.g/java8");
        }
        bash.runBashCommand("sudo -A rm -rf /Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk");

        if(args.equals("seven")) {
            String line = new String(Files.readAllBytes(Paths.get(bashProfile)));
            line = line.replace("export JAVA_HOME=`/usr/libexec/java_home -v 1.8`", "export JAVA_HOME=`/usr/libexec/java_home -v 1.7`");
            Files.write(Paths.get(bashProfile), line.getBytes());

        } else if (args.equals("eight")){

            bash.runBashCommand("sudo -A cp -r  ~/java8 /Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk");
            String line = new String(Files.readAllBytes(Paths.get(bashProfile)));
            line = line.replace("export JAVA_HOME=`/usr/libexec/java_home -v 1.7`", "export JAVA_HOME=`/usr/libexec/java_home -v 1.8`");
            Files.write(Paths.get(bashProfile), line.getBytes());

        } else {
            throw new RuntimeException("arg must be seven or eight");
        }
    }
}
