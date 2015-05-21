package org.opendaylight.testapp.persistence.model.persistence.jpa.query;

import java.nio.file.Path;

import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.jpa.JpaContext;
import org.opendaylight.persistence.jpa.JpaDataStore;
import org.opendaylight.persistence.util.common.io.FileUtil;
import org.opendaylight.testapp.persistence.impl.PersistenceServiceImpl;
import org.opendaylight.testapp.persistence.model.persistence.hsqldb.HsqldbServer;


public class JpaPersistenceService extends PersistenceServiceImpl<JpaContext> {

    /**
     * Creates a JPA Persistence Service
     * 
     * @param loggerProvider logger provider
     */
    public JpaPersistenceService() {
        super(DataStoreProvider.createDataStore(), new JpaQueryFactory());
    }

    private static class DataStoreProvider {
        private static final String PERSISTENCE_UNIT = "plugable-persistence-demo";
        private static final String DATABASE_NAME = "plugable-persistence-demo-db";
        private static final Path DATABASE_TMP_DATA = FileUtil.getPath(FileUtil.getTempDirectory(),
                "plugable-persistence-demo-tmp-data");

        private static HsqldbServer databaseServer;

        public static DataStore<JpaContext> createDataStore() {
            databaseServer = new HsqldbServer(DATABASE_NAME, DATABASE_TMP_DATA);
            databaseServer.start();
            Runtime.getRuntime().addShutdownHook(new Thread() {

                @Override
                public void run() {
                    databaseServer.stop();
                }
            });

            return new JpaDataStore(PERSISTENCE_UNIT);// revisit: Removed loggerProvider from arguments
        }
    }

	@Override
	public TsdrMetricPersistenceService tsdrMetric() {
		// TODO Auto-generated method stub
		return null;
	}
}
