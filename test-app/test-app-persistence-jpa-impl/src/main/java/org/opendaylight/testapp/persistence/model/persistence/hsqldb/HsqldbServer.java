package org.opendaylight.testapp.persistence.model.persistence.hsqldb;

import java.io.File;
import java.nio.file.Path;

import org.opendaylight.persistence.util.common.Restartable;
import org.opendaylight.persistence.util.common.io.FileUtil;

import org.hsqldb.Server;


public class HsqldbServer  implements Restartable {
    private static final boolean SILENT = true;

    private final Path databaseFolder;
    private final String databaseName;
    private Server databaseServer;
    private String logPrefix;

    /**
     * Creates an HSQLDB server.
     * <p>
     * User: sa
     * <p>
     * Password: &lt;blank&gt;
     * 
     * @param databaseName database name
     * @param databaseFolder folder to store data so the database can be accessed using dbVisualizer
     *            while the server is running
     */
    public HsqldbServer(String databaseName, Path databaseFolder) {
        if (databaseName == null) {
            throw new NullPointerException("databaseName cannot be null");
        }

        if (databaseName.isEmpty()) {
            throw new IllegalArgumentException("databaseName cannot be empty");
        }

        if (databaseFolder == null) {
            throw new NullPointerException("databaseFolder cannot be null");
        }

        this.databaseFolder = databaseFolder;
        this.databaseName = databaseName;
        this.logPrefix = getClass().getName();
    }

    @Override
    public synchronized void start() {
        if (this.databaseServer != null) {
            return;
        }

        System.out.println(this.logPrefix + " Starting HSQLDB server (slient mode is " + SILENT + ")...");
        System.out.println(this.logPrefix + " dbVisualizer connection:");
        System.out.println(this.logPrefix + " Driver:HSQLDB server, Server:localhost, Port:9001");
        System.out.println(this.logPrefix + " Datebase: " + this.databaseName + ", Userid: sa, Password: <blank>");

        System.out
                .println(this.logPrefix
                        + " Using directory "
                        + this.databaseFolder
                        + " to hold HSQLDB so the database can be accessed using dbVisualizer while the server is running (breakpoint if used during unit test)");
        File dir = new File(this.databaseFolder.toString());
        dir.mkdirs();

        Path databaseFile = FileUtil.getPath(this.databaseFolder, this.databaseName);

        this.databaseServer = new Server();
        this.databaseServer.setLogWriter(null);
        this.databaseServer.setSilent(SILENT);
        this.databaseServer.setDatabaseName(0, this.databaseName);
        this.databaseServer.setDatabasePath(0, "file:" + databaseFile.toString());

        this.databaseServer.start();

        // System.out.println("databaseServer.getPort(): " + databaseServer.getPort());

        try {
            Class.forName("org.hsqldb.jdbcDriver");
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void stop() {
        if (this.databaseServer == null) {
            return;
        }
        System.out.println(this.logPrefix + " Stopping HSQLDB server...");
        this.databaseServer.stop();
        this.databaseServer = null;
    }
}