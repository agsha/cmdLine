package cmdLine.utils;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sharath.g on 25/06/15.
 */
public class Bash {
    private static final Logger log = LogManager.getLogger();
    String curDirectory = System.getProperty("user.dir");

    public Bash(String curDirectory) {
        this.curDirectory = curDirectory;
    }

    public void setCurDirectory(String curDirectory) {
        this.curDirectory = curDirectory;
    }

    public void runBashCommand(String cmd) {
        log.debug("Cur dir: {}, cmd: {}", curDirectory, cmd);
        ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", cmd);
        pb.environment().put("SUDO_ASKPASS", "/Users/sharath.g/bin/askpass.sh");

        pb.directory(Paths.get(curDirectory).toFile());
        pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectErrorStream(true);
        final Process process;
        try {
            process = pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(new Runnable() {
            public void run() {
                try {
                    IOUtils.copy(process.getInputStream(), System.out);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        int ret = 0;
        try {
            ret = process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();
        if(ret!=0) {
            throw new RuntimeException("Command failed: "+pb.command()+" return code: "+ret);
        }
    }


    public List<String> runBashCommandGetOutput(String cmd) {
        log.debug("Executing command: {}", cmd);
        ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", cmd);
        pb.environment().put("SUDO_ASKPASS", "/Users/sharath.g/bin/askpass.sh");

        pb.redirectErrorStream(true);
        pb.redirectInput(ProcessBuilder.Redirect.INHERIT);

        final Process process;
        try {
            process = pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        final Scanner sc = new Scanner(process.getInputStream());
        final List<String> list = new ArrayList<String>();

        executorService.execute(new Runnable() {
            public void run() {
                while (sc.hasNextLine()) {
                    list.add(sc.nextLine());
                }
            }
        });

        int ret = 0;
        try {
            ret = process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();
        if(ret!=0) {
            throw new RuntimeException("Command failed: "+pb.command()+" return code: "+ret);
        }
        return list;
    }


}
