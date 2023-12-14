package org.eclipse.jetty.server.session;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionManager;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.session.JDBCSessionManager;
import org.eclipse.jetty.util.log.Logger;

public class JDBCSessionIdManager extends AbstractSessionIdManager {
    static final Logger LOG = SessionHandler.LOG;
    protected String _blobType;
    protected String _connectionUrl;
    protected String _createSessionIdTable;
    protected String _createSessionTable;
    protected DataSource _datasource;
    protected DatabaseAdaptor _dbAdaptor;
    protected String _deleteId;
    protected String _deleteOldExpiredSessions;
    protected String _deleteSession;
    protected Driver _driver;
    protected String _driverClassName;
    protected String _insertId;
    protected String _insertSession;
    protected String _jndiName;
    protected long _lastScavengeTime;
    protected String _longType;
    protected String _queryId;
    protected long _scavengeIntervalMs = 600000;
    protected String _selectBoundedExpiredSessions;
    private String _selectExpiredSessions;
    protected Server _server;
    protected String _sessionIdTable = "JettySessionIds";
    protected final HashSet<String> _sessionIds = new HashSet<>();
    protected String _sessionTable = "JettySessions";
    protected String _sessionTableRowId = "rowId";
    protected TimerTask _task;
    protected Timer _timer;
    protected String _updateSession;
    protected String _updateSessionAccessTime;
    protected String _updateSessionNode;

    public class DatabaseAdaptor {
        String _dbName;
        boolean _isLower;
        boolean _isUpper;

        public DatabaseAdaptor(DatabaseMetaData databaseMetaData) throws SQLException {
            this._dbName = databaseMetaData.getDatabaseProductName().toLowerCase(Locale.ENGLISH);
            JDBCSessionIdManager.LOG.debug("Using database {}", this._dbName);
            this._isLower = databaseMetaData.storesLowerCaseIdentifiers();
            this._isUpper = databaseMetaData.storesUpperCaseIdentifiers();
        }

        public String convertIdentifier(String str) {
            if (this._isLower) {
                return str.toLowerCase(Locale.ENGLISH);
            }
            return this._isUpper ? str.toUpperCase(Locale.ENGLISH) : str;
        }

        public String getDBName() {
            return this._dbName;
        }

        public String getBlobType() {
            if (JDBCSessionIdManager.this._blobType != null) {
                return JDBCSessionIdManager.this._blobType;
            }
            return this._dbName.startsWith("postgres") ? "bytea" : "blob";
        }

        public String getLongType() {
            if (JDBCSessionIdManager.this._longType != null) {
                return JDBCSessionIdManager.this._longType;
            }
            return this._dbName.startsWith("oracle") ? "number(20)" : "bigint";
        }

        public InputStream getBlobInputStream(ResultSet resultSet, String str) throws SQLException {
            if (this._dbName.startsWith("postgres")) {
                return new ByteArrayInputStream(resultSet.getBytes(str));
            }
            return resultSet.getBlob(str).getBinaryStream();
        }

        public String getRowIdColumnName() {
            String str = this._dbName;
            return (str == null || !str.startsWith("oracle")) ? "rowId" : "srowId";
        }

        public boolean isEmptyStringNull() {
            return this._dbName.startsWith("oracle");
        }

        public PreparedStatement getLoadStatement(Connection connection, String str, String str2, String str3) throws SQLException {
            if ((str2 == null || "".equals(str2)) && isEmptyStringNull()) {
                PreparedStatement prepareStatement = connection.prepareStatement("select * from " + JDBCSessionIdManager.this._sessionTable + " where sessionId = ? and contextPath is null and virtualHost = ?");
                prepareStatement.setString(1, str);
                prepareStatement.setString(2, str3);
                return prepareStatement;
            }
            PreparedStatement prepareStatement2 = connection.prepareStatement("select * from " + JDBCSessionIdManager.this._sessionTable + " where sessionId = ? and contextPath = ? and virtualHost = ?");
            prepareStatement2.setString(1, str);
            prepareStatement2.setString(2, str2);
            prepareStatement2.setString(3, str3);
            return prepareStatement2;
        }
    }

    public JDBCSessionIdManager(Server server) {
        this._server = server;
    }

    public JDBCSessionIdManager(Server server, Random random) {
        super(random);
        this._server = server;
    }

    public void setDriverInfo(String str, String str2) {
        this._driverClassName = str;
        this._connectionUrl = str2;
    }

    public void setDriverInfo(Driver driver, String str) {
        this._driver = driver;
        this._connectionUrl = str;
    }

    public void setDatasource(DataSource dataSource) {
        this._datasource = dataSource;
    }

    public DataSource getDataSource() {
        return this._datasource;
    }

    public String getDriverClassName() {
        return this._driverClassName;
    }

    public String getConnectionUrl() {
        return this._connectionUrl;
    }

    public void setDatasourceName(String str) {
        this._jndiName = str;
    }

    public String getDatasourceName() {
        return this._jndiName;
    }

    public void setBlobType(String str) {
        this._blobType = str;
    }

    public String getBlobType() {
        return this._blobType;
    }

    public String getLongType() {
        return this._longType;
    }

    public void setLongType(String str) {
        this._longType = str;
    }

    public void setScavengeInterval(long j) {
        if (j <= 0) {
            j = 60;
        }
        long j2 = this._scavengeIntervalMs;
        long j3 = j * 1000;
        this._scavengeIntervalMs = j3;
        long j4 = this._scavengeIntervalMs / 10;
        if (System.currentTimeMillis() % 2 == 0) {
            this._scavengeIntervalMs += j4;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Scavenging every " + this._scavengeIntervalMs + " ms", new Object[0]);
        }
        if (this._timer == null) {
            return;
        }
        if (j3 != j2 || this._task == null) {
            synchronized (this) {
                if (this._task != null) {
                    this._task.cancel();
                }
                this._task = new TimerTask() {
                    public void run() {
                        JDBCSessionIdManager.this.scavenge();
                    }
                };
                this._timer.schedule(this._task, this._scavengeIntervalMs, this._scavengeIntervalMs);
            }
        }
    }

    public long getScavengeInterval() {
        return this._scavengeIntervalMs / 1000;
    }

    public void addSession(HttpSession httpSession) {
        if (httpSession != null) {
            synchronized (this._sessionIds) {
                String clusterId = ((JDBCSessionManager.Session) httpSession).getClusterId();
                try {
                    insert(clusterId);
                    this._sessionIds.add(clusterId);
                } catch (Exception e) {
                    Logger logger = LOG;
                    logger.warn("Problem storing session id=" + clusterId, (Throwable) e);
                }
            }
        }
    }

    public void removeSession(HttpSession httpSession) {
        if (httpSession != null) {
            removeSession(((JDBCSessionManager.Session) httpSession).getClusterId());
        }
    }

    public void removeSession(String str) {
        if (str != null) {
            synchronized (this._sessionIds) {
                if (LOG.isDebugEnabled()) {
                    Logger logger = LOG;
                    logger.debug("Removing session id=" + str, new Object[0]);
                }
                try {
                    this._sessionIds.remove(str);
                    delete(str);
                } catch (Exception e) {
                    Logger logger2 = LOG;
                    logger2.warn("Problem removing session id=" + str, (Throwable) e);
                }
            }
        }
    }

    public String getClusterId(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf > 0 ? str.substring(0, lastIndexOf) : str;
    }

    public String getNodeId(String str, HttpServletRequest httpServletRequest) {
        if (this._workerName == null) {
            return str;
        }
        return str + '.' + this._workerName;
    }

    public boolean idInUse(String str) {
        boolean contains;
        if (str == null) {
            return false;
        }
        String clusterId = getClusterId(str);
        synchronized (this._sessionIds) {
            contains = this._sessionIds.contains(clusterId);
        }
        if (contains) {
            return true;
        }
        try {
            return exists(clusterId);
        } catch (Exception e) {
            Logger logger = LOG;
            logger.warn("Problem checking inUse for id=" + clusterId, (Throwable) e);
            return false;
        }
    }

    public void invalidateAll(String str) {
        SessionManager sessionManager;
        removeSession(str);
        synchronized (this._sessionIds) {
            Handler[] childHandlersByClass = this._server.getChildHandlersByClass(ContextHandler.class);
            int i = 0;
            while (childHandlersByClass != null && i < childHandlersByClass.length) {
                SessionHandler sessionHandler = (SessionHandler) ((ContextHandler) childHandlersByClass[i]).getChildHandlerByClass(SessionHandler.class);
                if (!(sessionHandler == null || (sessionManager = sessionHandler.getSessionManager()) == null || !(sessionManager instanceof JDBCSessionManager))) {
                    ((JDBCSessionManager) sessionManager).invalidateSession(str);
                }
                i++;
            }
        }
    }

    public void doStart() throws Exception {
        initializeDatabase();
        prepareTables();
        cleanExpiredSessions();
        super.doStart();
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("Scavenging interval = " + getScavengeInterval() + " sec", new Object[0]);
        }
        this._timer = new Timer("JDBCSessionScavenger", true);
        setScavengeInterval(getScavengeInterval());
    }

    public void doStop() throws Exception {
        synchronized (this) {
            if (this._task != null) {
                this._task.cancel();
            }
            if (this._timer != null) {
                this._timer.cancel();
            }
            this._timer = null;
        }
        this._sessionIds.clear();
        super.doStop();
    }

    /* access modifiers changed from: protected */
    public Connection getConnection() throws SQLException {
        DataSource dataSource = this._datasource;
        if (dataSource != null) {
            return dataSource.getConnection();
        }
        return DriverManager.getConnection(this._connectionUrl);
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x02f9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void prepareTables() throws java.sql.SQLException {
        /*
            r17 = this;
            r1 = r17
            java.lang.String r0 = ", "
            java.lang.String r2 = "idx_"
            java.lang.String r3 = "update "
            java.lang.String r4 = " = ?"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "create table "
            r5.append(r6)
            java.lang.String r7 = r1._sessionIdTable
            r5.append(r7)
            java.lang.String r7 = " (id varchar(120), primary key(id))"
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            r1._createSessionIdTable = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "select * from "
            r5.append(r7)
            java.lang.String r8 = r1._sessionTable
            r5.append(r8)
            java.lang.String r8 = " where expiryTime >= ? and expiryTime <= ?"
            r5.append(r8)
            java.lang.String r5 = r5.toString()
            r1._selectBoundedExpiredSessions = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r7)
            java.lang.String r8 = r1._sessionTable
            r5.append(r8)
            java.lang.String r8 = " where expiryTime >0 and expiryTime <= ?"
            r5.append(r8)
            java.lang.String r5 = r5.toString()
            r1._selectExpiredSessions = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r9 = "delete from "
            r5.append(r9)
            java.lang.String r10 = r1._sessionTable
            r5.append(r10)
            r5.append(r8)
            java.lang.String r5 = r5.toString()
            r1._deleteOldExpiredSessions = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r8 = "insert into "
            r5.append(r8)
            java.lang.String r10 = r1._sessionIdTable
            r5.append(r10)
            java.lang.String r10 = " (id)  values (?)"
            r5.append(r10)
            java.lang.String r5 = r5.toString()
            r1._insertId = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r9)
            java.lang.String r10 = r1._sessionIdTable
            r5.append(r10)
            java.lang.String r10 = " where id = ?"
            r5.append(r10)
            java.lang.String r5 = r5.toString()
            r1._deleteId = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r7)
            java.lang.String r7 = r1._sessionIdTable
            r5.append(r7)
            r5.append(r10)
            java.lang.String r5 = r5.toString()
            r1._queryId = r5
            r5 = 0
            java.sql.Connection r7 = r17.getConnection()     // Catch:{ all -> 0x02f5 }
            r10 = 1
            r7.setAutoCommit(r10)     // Catch:{ all -> 0x02f3 }
            java.sql.DatabaseMetaData r11 = r7.getMetaData()     // Catch:{ all -> 0x02f3 }
            org.eclipse.jetty.server.session.JDBCSessionIdManager$DatabaseAdaptor r12 = new org.eclipse.jetty.server.session.JDBCSessionIdManager$DatabaseAdaptor     // Catch:{ all -> 0x02f3 }
            r12.<init>(r11)     // Catch:{ all -> 0x02f3 }
            r1._dbAdaptor = r12     // Catch:{ all -> 0x02f3 }
            org.eclipse.jetty.server.session.JDBCSessionIdManager$DatabaseAdaptor r12 = r1._dbAdaptor     // Catch:{ all -> 0x02f3 }
            java.lang.String r12 = r12.getRowIdColumnName()     // Catch:{ all -> 0x02f3 }
            r1._sessionTableRowId = r12     // Catch:{ all -> 0x02f3 }
            org.eclipse.jetty.server.session.JDBCSessionIdManager$DatabaseAdaptor r12 = r1._dbAdaptor     // Catch:{ all -> 0x02f3 }
            java.lang.String r13 = r1._sessionIdTable     // Catch:{ all -> 0x02f3 }
            java.lang.String r12 = r12.convertIdentifier(r13)     // Catch:{ all -> 0x02f3 }
            java.sql.ResultSet r12 = r11.getTables(r5, r5, r12, r5)     // Catch:{ all -> 0x02f3 }
            boolean r12 = r12.next()     // Catch:{ all -> 0x02f3 }
            if (r12 != 0) goto L_0x00ed
            java.sql.Statement r12 = r7.createStatement()     // Catch:{ all -> 0x02f3 }
            java.lang.String r13 = r1._createSessionIdTable     // Catch:{ all -> 0x02f3 }
            r12.executeUpdate(r13)     // Catch:{ all -> 0x02f3 }
        L_0x00ed:
            org.eclipse.jetty.server.session.JDBCSessionIdManager$DatabaseAdaptor r12 = r1._dbAdaptor     // Catch:{ all -> 0x02f3 }
            java.lang.String r13 = r1._sessionTable     // Catch:{ all -> 0x02f3 }
            java.lang.String r14 = r12.convertIdentifier(r13)     // Catch:{ all -> 0x02f3 }
            java.sql.ResultSet r5 = r11.getTables(r5, r5, r14, r5)     // Catch:{ all -> 0x02f3 }
            boolean r5 = r5.next()     // Catch:{ all -> 0x02f3 }
            java.lang.String r15 = " ("
            if (r5 != 0) goto L_0x0183
            org.eclipse.jetty.server.session.JDBCSessionIdManager$DatabaseAdaptor r5 = r1._dbAdaptor     // Catch:{ all -> 0x02f3 }
            java.lang.String r5 = r5.getBlobType()     // Catch:{ all -> 0x02f3 }
            org.eclipse.jetty.server.session.JDBCSessionIdManager$DatabaseAdaptor r12 = r1._dbAdaptor     // Catch:{ all -> 0x02f3 }
            java.lang.String r12 = r12.getLongType()     // Catch:{ all -> 0x02f3 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x02f3 }
            r13.<init>()     // Catch:{ all -> 0x02f3 }
            r13.append(r6)     // Catch:{ all -> 0x02f3 }
            java.lang.String r6 = r1._sessionTable     // Catch:{ all -> 0x02f3 }
            r13.append(r6)     // Catch:{ all -> 0x02f3 }
            r13.append(r15)     // Catch:{ all -> 0x02f3 }
            java.lang.String r6 = r1._sessionTableRowId     // Catch:{ all -> 0x02f3 }
            r13.append(r6)     // Catch:{ all -> 0x02f3 }
            java.lang.String r6 = " varchar(120), sessionId varchar(120), "
            r13.append(r6)     // Catch:{ all -> 0x02f3 }
            java.lang.String r6 = " contextPath varchar(60), virtualHost varchar(60), lastNode varchar(60), accessTime "
            r13.append(r6)     // Catch:{ all -> 0x02f3 }
            r13.append(r12)     // Catch:{ all -> 0x02f3 }
            r13.append(r0)     // Catch:{ all -> 0x02f3 }
            java.lang.String r6 = " lastAccessTime "
            r13.append(r6)     // Catch:{ all -> 0x02f3 }
            r13.append(r12)     // Catch:{ all -> 0x02f3 }
            java.lang.String r6 = ", createTime "
            r13.append(r6)     // Catch:{ all -> 0x02f3 }
            r13.append(r12)     // Catch:{ all -> 0x02f3 }
            java.lang.String r6 = ", cookieTime "
            r13.append(r6)     // Catch:{ all -> 0x02f3 }
            r13.append(r12)     // Catch:{ all -> 0x02f3 }
            r13.append(r0)     // Catch:{ all -> 0x02f3 }
            java.lang.String r0 = " lastSavedTime "
            r13.append(r0)     // Catch:{ all -> 0x02f3 }
            r13.append(r12)     // Catch:{ all -> 0x02f3 }
            java.lang.String r0 = ", expiryTime "
            r13.append(r0)     // Catch:{ all -> 0x02f3 }
            r13.append(r12)     // Catch:{ all -> 0x02f3 }
            java.lang.String r0 = ", map "
            r13.append(r0)     // Catch:{ all -> 0x02f3 }
            r13.append(r5)     // Catch:{ all -> 0x02f3 }
            java.lang.String r0 = ", primary key("
            r13.append(r0)     // Catch:{ all -> 0x02f3 }
            java.lang.String r0 = r1._sessionTableRowId     // Catch:{ all -> 0x02f3 }
            r13.append(r0)     // Catch:{ all -> 0x02f3 }
            java.lang.String r0 = "))"
            r13.append(r0)     // Catch:{ all -> 0x02f3 }
            java.lang.String r0 = r13.toString()     // Catch:{ all -> 0x02f3 }
            r1._createSessionTable = r0     // Catch:{ all -> 0x02f3 }
            java.sql.Statement r0 = r7.createStatement()     // Catch:{ all -> 0x02f3 }
            java.lang.String r5 = r1._createSessionTable     // Catch:{ all -> 0x02f3 }
            r0.executeUpdate(r5)     // Catch:{ all -> 0x02f3 }
        L_0x0183:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x02f3 }
            r0.<init>()     // Catch:{ all -> 0x02f3 }
            r0.append(r2)     // Catch:{ all -> 0x02f3 }
            java.lang.String r5 = r1._sessionTable     // Catch:{ all -> 0x02f3 }
            r0.append(r5)     // Catch:{ all -> 0x02f3 }
            java.lang.String r5 = "_expiry"
            r0.append(r5)     // Catch:{ all -> 0x02f3 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x02f3 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x02f3 }
            r5.<init>()     // Catch:{ all -> 0x02f3 }
            r5.append(r2)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = r1._sessionTable     // Catch:{ all -> 0x02f3 }
            r5.append(r2)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = "_session"
            r5.append(r2)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = r5.toString()     // Catch:{ all -> 0x02f3 }
            r12 = 0
            r13 = 0
            r5 = 0
            r16 = 0
            r6 = r15
            r15 = r5
            java.sql.ResultSet r5 = r11.getIndexInfo(r12, r13, r14, r15, r16)     // Catch:{ all -> 0x02f3 }
            r11 = 0
            r12 = 0
        L_0x01bc:
            boolean r13 = r5.next()     // Catch:{ all -> 0x02f3 }
            if (r13 == 0) goto L_0x01d8
            java.lang.String r13 = "INDEX_NAME"
            java.lang.String r13 = r5.getString(r13)     // Catch:{ all -> 0x02f3 }
            boolean r14 = r0.equalsIgnoreCase(r13)     // Catch:{ all -> 0x02f3 }
            if (r14 == 0) goto L_0x01d0
            r11 = 1
            goto L_0x01bc
        L_0x01d0:
            boolean r13 = r2.equalsIgnoreCase(r13)     // Catch:{ all -> 0x02f3 }
            if (r13 == 0) goto L_0x01bc
            r12 = 1
            goto L_0x01bc
        L_0x01d8:
            if (r11 == 0) goto L_0x01dc
            if (r12 != 0) goto L_0x0248
        L_0x01dc:
            java.sql.Statement r5 = r7.createStatement()     // Catch:{ all -> 0x02f3 }
            java.lang.String r10 = "create index "
            if (r11 != 0) goto L_0x0209
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x0206 }
            r11.<init>()     // Catch:{ all -> 0x0206 }
            r11.append(r10)     // Catch:{ all -> 0x0206 }
            r11.append(r0)     // Catch:{ all -> 0x0206 }
            java.lang.String r0 = " on "
            r11.append(r0)     // Catch:{ all -> 0x0206 }
            java.lang.String r0 = r1._sessionTable     // Catch:{ all -> 0x0206 }
            r11.append(r0)     // Catch:{ all -> 0x0206 }
            java.lang.String r0 = " (expiryTime)"
            r11.append(r0)     // Catch:{ all -> 0x0206 }
            java.lang.String r0 = r11.toString()     // Catch:{ all -> 0x0206 }
            r5.executeUpdate(r0)     // Catch:{ all -> 0x0206 }
            goto L_0x0209
        L_0x0206:
            r0 = move-exception
            r2 = r0
            goto L_0x022d
        L_0x0209:
            if (r12 != 0) goto L_0x023b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0206 }
            r0.<init>()     // Catch:{ all -> 0x0206 }
            r0.append(r10)     // Catch:{ all -> 0x0206 }
            r0.append(r2)     // Catch:{ all -> 0x0206 }
            java.lang.String r2 = " on "
            r0.append(r2)     // Catch:{ all -> 0x0206 }
            java.lang.String r2 = r1._sessionTable     // Catch:{ all -> 0x0206 }
            r0.append(r2)     // Catch:{ all -> 0x0206 }
            java.lang.String r2 = " (sessionId, contextPath)"
            r0.append(r2)     // Catch:{ all -> 0x0206 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0206 }
            r5.executeUpdate(r0)     // Catch:{ all -> 0x0206 }
            goto L_0x023b
        L_0x022d:
            if (r5 == 0) goto L_0x023a
            r5.close()     // Catch:{ Exception -> 0x0233 }
            goto L_0x023a
        L_0x0233:
            r0 = move-exception
            r3 = r0
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x02f3 }
            r0.warn(r3)     // Catch:{ all -> 0x02f3 }
        L_0x023a:
            throw r2     // Catch:{ all -> 0x02f3 }
        L_0x023b:
            if (r5 == 0) goto L_0x0248
            r5.close()     // Catch:{ Exception -> 0x0241 }
            goto L_0x0248
        L_0x0241:
            r0 = move-exception
            r2 = r0
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x02f3 }
            r0.warn(r2)     // Catch:{ all -> 0x02f3 }
        L_0x0248:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x02f3 }
            r0.<init>()     // Catch:{ all -> 0x02f3 }
            r0.append(r8)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = r1._sessionTable     // Catch:{ all -> 0x02f3 }
            r0.append(r2)     // Catch:{ all -> 0x02f3 }
            r0.append(r6)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = r1._sessionTableRowId     // Catch:{ all -> 0x02f3 }
            r0.append(r2)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = ", sessionId, contextPath, virtualHost, lastNode, accessTime, lastAccessTime, createTime, cookieTime, lastSavedTime, expiryTime, map) "
            r0.append(r2)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            r0.append(r2)     // Catch:{ all -> 0x02f3 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x02f3 }
            r1._insertSession = r0     // Catch:{ all -> 0x02f3 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x02f3 }
            r0.<init>()     // Catch:{ all -> 0x02f3 }
            r0.append(r9)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = r1._sessionTable     // Catch:{ all -> 0x02f3 }
            r0.append(r2)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = " where "
            r0.append(r2)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = r1._sessionTableRowId     // Catch:{ all -> 0x02f3 }
            r0.append(r2)     // Catch:{ all -> 0x02f3 }
            r0.append(r4)     // Catch:{ all -> 0x02f3 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x02f3 }
            r1._deleteSession = r0     // Catch:{ all -> 0x02f3 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x02f3 }
            r0.<init>()     // Catch:{ all -> 0x02f3 }
            r0.append(r3)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = r1._sessionTable     // Catch:{ all -> 0x02f3 }
            r0.append(r2)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = " set lastNode = ?, accessTime = ?, lastAccessTime = ?, lastSavedTime = ?, expiryTime = ?, map = ? where "
            r0.append(r2)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = r1._sessionTableRowId     // Catch:{ all -> 0x02f3 }
            r0.append(r2)     // Catch:{ all -> 0x02f3 }
            r0.append(r4)     // Catch:{ all -> 0x02f3 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x02f3 }
            r1._updateSession = r0     // Catch:{ all -> 0x02f3 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x02f3 }
            r0.<init>()     // Catch:{ all -> 0x02f3 }
            r0.append(r3)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = r1._sessionTable     // Catch:{ all -> 0x02f3 }
            r0.append(r2)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = " set lastNode = ? where "
            r0.append(r2)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = r1._sessionTableRowId     // Catch:{ all -> 0x02f3 }
            r0.append(r2)     // Catch:{ all -> 0x02f3 }
            r0.append(r4)     // Catch:{ all -> 0x02f3 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x02f3 }
            r1._updateSessionNode = r0     // Catch:{ all -> 0x02f3 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x02f3 }
            r0.<init>()     // Catch:{ all -> 0x02f3 }
            r0.append(r3)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = r1._sessionTable     // Catch:{ all -> 0x02f3 }
            r0.append(r2)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = " set lastNode = ?, accessTime = ?, lastAccessTime = ?, lastSavedTime = ?, expiryTime = ? where "
            r0.append(r2)     // Catch:{ all -> 0x02f3 }
            java.lang.String r2 = r1._sessionTableRowId     // Catch:{ all -> 0x02f3 }
            r0.append(r2)     // Catch:{ all -> 0x02f3 }
            r0.append(r4)     // Catch:{ all -> 0x02f3 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x02f3 }
            r1._updateSessionAccessTime = r0     // Catch:{ all -> 0x02f3 }
            if (r7 == 0) goto L_0x02f2
            r7.close()
        L_0x02f2:
            return
        L_0x02f3:
            r0 = move-exception
            goto L_0x02f7
        L_0x02f5:
            r0 = move-exception
            r7 = r5
        L_0x02f7:
            if (r7 == 0) goto L_0x02fc
            r7.close()
        L_0x02fc:
            goto L_0x02fe
        L_0x02fd:
            throw r0
        L_0x02fe:
            goto L_0x02fd
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.session.JDBCSessionIdManager.prepareTables():void");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: java.sql.Connection} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.sql.PreparedStatement} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.sql.Connection} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: java.sql.Connection} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: java.sql.Connection} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0050 A[SYNTHETIC, Splitter:B:28:0x0050] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x005c A[SYNTHETIC, Splitter:B:33:0x005c] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0068  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void insert(java.lang.String r6) throws java.sql.SQLException {
        /*
            r5 = this;
            r0 = 0
            java.sql.Connection r1 = r5.getConnection()     // Catch:{ all -> 0x004b }
            r2 = 1
            r1.setAutoCommit(r2)     // Catch:{ all -> 0x0048 }
            java.lang.String r3 = r5._queryId     // Catch:{ all -> 0x0048 }
            java.sql.PreparedStatement r3 = r1.prepareStatement(r3)     // Catch:{ all -> 0x0048 }
            r3.setString(r2, r6)     // Catch:{ all -> 0x0046 }
            java.sql.ResultSet r4 = r3.executeQuery()     // Catch:{ all -> 0x0046 }
            boolean r4 = r4.next()     // Catch:{ all -> 0x0046 }
            if (r4 != 0) goto L_0x0028
            java.lang.String r4 = r5._insertId     // Catch:{ all -> 0x0046 }
            java.sql.PreparedStatement r0 = r1.prepareStatement(r4)     // Catch:{ all -> 0x0046 }
            r0.setString(r2, r6)     // Catch:{ all -> 0x0046 }
            r0.executeUpdate()     // Catch:{ all -> 0x0046 }
        L_0x0028:
            if (r3 == 0) goto L_0x0034
            r3.close()     // Catch:{ Exception -> 0x002e }
            goto L_0x0034
        L_0x002e:
            r6 = move-exception
            org.eclipse.jetty.util.log.Logger r2 = LOG
            r2.warn(r6)
        L_0x0034:
            if (r0 == 0) goto L_0x0040
            r0.close()     // Catch:{ Exception -> 0x003a }
            goto L_0x0040
        L_0x003a:
            r6 = move-exception
            org.eclipse.jetty.util.log.Logger r0 = LOG
            r0.warn(r6)
        L_0x0040:
            if (r1 == 0) goto L_0x0045
            r1.close()
        L_0x0045:
            return
        L_0x0046:
            r6 = move-exception
            goto L_0x004e
        L_0x0048:
            r6 = move-exception
            r3 = r0
            goto L_0x004e
        L_0x004b:
            r6 = move-exception
            r1 = r0
            r3 = r1
        L_0x004e:
            if (r3 == 0) goto L_0x005a
            r3.close()     // Catch:{ Exception -> 0x0054 }
            goto L_0x005a
        L_0x0054:
            r2 = move-exception
            org.eclipse.jetty.util.log.Logger r3 = LOG
            r3.warn(r2)
        L_0x005a:
            if (r0 == 0) goto L_0x0066
            r0.close()     // Catch:{ Exception -> 0x0060 }
            goto L_0x0066
        L_0x0060:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r2 = LOG
            r2.warn(r0)
        L_0x0066:
            if (r1 == 0) goto L_0x006b
            r1.close()
        L_0x006b:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.session.JDBCSessionIdManager.insert(java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x002d A[SYNTHETIC, Splitter:B:17:0x002d] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0039  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void delete(java.lang.String r5) throws java.sql.SQLException {
        /*
            r4 = this;
            r0 = 0
            java.sql.Connection r1 = r4.getConnection()     // Catch:{ all -> 0x0029 }
            r2 = 1
            r1.setAutoCommit(r2)     // Catch:{ all -> 0x0027 }
            java.lang.String r3 = r4._deleteId     // Catch:{ all -> 0x0027 }
            java.sql.PreparedStatement r0 = r1.prepareStatement(r3)     // Catch:{ all -> 0x0027 }
            r0.setString(r2, r5)     // Catch:{ all -> 0x0027 }
            r0.executeUpdate()     // Catch:{ all -> 0x0027 }
            if (r0 == 0) goto L_0x0021
            r0.close()     // Catch:{ Exception -> 0x001b }
            goto L_0x0021
        L_0x001b:
            r5 = move-exception
            org.eclipse.jetty.util.log.Logger r0 = LOG
            r0.warn(r5)
        L_0x0021:
            if (r1 == 0) goto L_0x0026
            r1.close()
        L_0x0026:
            return
        L_0x0027:
            r5 = move-exception
            goto L_0x002b
        L_0x0029:
            r5 = move-exception
            r1 = r0
        L_0x002b:
            if (r0 == 0) goto L_0x0037
            r0.close()     // Catch:{ Exception -> 0x0031 }
            goto L_0x0037
        L_0x0031:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r2 = LOG
            r2.warn(r0)
        L_0x0037:
            if (r1 == 0) goto L_0x003c
            r1.close()
        L_0x003c:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.session.JDBCSessionIdManager.delete(java.lang.String):void");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: java.sql.PreparedStatement} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: java.sql.Connection} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.sql.Connection} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.sql.Connection} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0032 A[SYNTHETIC, Splitter:B:18:0x0032] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x003e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean exists(java.lang.String r5) throws java.sql.SQLException {
        /*
            r4 = this;
            r0 = 0
            java.sql.Connection r1 = r4.getConnection()     // Catch:{ all -> 0x002e }
            r2 = 1
            r1.setAutoCommit(r2)     // Catch:{ all -> 0x002c }
            java.lang.String r3 = r4._queryId     // Catch:{ all -> 0x002c }
            java.sql.PreparedStatement r0 = r1.prepareStatement(r3)     // Catch:{ all -> 0x002c }
            r0.setString(r2, r5)     // Catch:{ all -> 0x002c }
            java.sql.ResultSet r5 = r0.executeQuery()     // Catch:{ all -> 0x002c }
            boolean r5 = r5.next()     // Catch:{ all -> 0x002c }
            if (r0 == 0) goto L_0x0026
            r0.close()     // Catch:{ Exception -> 0x0020 }
            goto L_0x0026
        L_0x0020:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r2 = LOG
            r2.warn(r0)
        L_0x0026:
            if (r1 == 0) goto L_0x002b
            r1.close()
        L_0x002b:
            return r5
        L_0x002c:
            r5 = move-exception
            goto L_0x0030
        L_0x002e:
            r5 = move-exception
            r1 = r0
        L_0x0030:
            if (r0 == 0) goto L_0x003c
            r0.close()     // Catch:{ Exception -> 0x0036 }
            goto L_0x003c
        L_0x0036:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r2 = LOG
            r2.warn(r0)
        L_0x003c:
            if (r1 == 0) goto L_0x0041
            r1.close()
        L_0x0041:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.session.JDBCSessionIdManager.exists(java.lang.String):boolean");
    }

    /* access modifiers changed from: private */
    public void scavenge() {
        PreparedStatement prepareStatement;
        SessionManager sessionManager;
        ArrayList arrayList = new ArrayList();
        Connection connection = null;
        try {
            if (LOG.isDebugEnabled()) {
                Logger logger = LOG;
                logger.debug("Scavenge sweep started at " + System.currentTimeMillis(), new Object[0]);
            }
            if (this._lastScavengeTime > 0) {
                connection = getConnection();
                connection.setAutoCommit(true);
                prepareStatement = connection.prepareStatement(this._selectBoundedExpiredSessions);
                long j = this._lastScavengeTime - this._scavengeIntervalMs;
                long j2 = this._lastScavengeTime;
                if (LOG.isDebugEnabled()) {
                    Logger logger2 = LOG;
                    logger2.debug(" Searching for sessions expired between " + j + " and " + j2, new Object[0]);
                }
                prepareStatement.setLong(1, j);
                prepareStatement.setLong(2, j2);
                ResultSet executeQuery = prepareStatement.executeQuery();
                while (executeQuery.next()) {
                    String string = executeQuery.getString("sessionId");
                    arrayList.add(string);
                    if (LOG.isDebugEnabled()) {
                        Logger logger3 = LOG;
                        logger3.debug(" Found expired sessionId=" + string, new Object[0]);
                    }
                }
                Handler[] childHandlersByClass = this._server.getChildHandlersByClass(ContextHandler.class);
                int i = 0;
                while (childHandlersByClass != null && i < childHandlersByClass.length) {
                    SessionHandler sessionHandler = (SessionHandler) ((ContextHandler) childHandlersByClass[i]).getChildHandlerByClass(SessionHandler.class);
                    if (!(sessionHandler == null || (sessionManager = sessionHandler.getSessionManager()) == null || !(sessionManager instanceof JDBCSessionManager))) {
                        ((JDBCSessionManager) sessionManager).expire(arrayList);
                    }
                    i++;
                }
                long j3 = this._lastScavengeTime;
                long j4 = this._scavengeIntervalMs;
                Long.signum(j4);
                long j5 = j3 - (j4 * 2);
                if (j5 > 0) {
                    if (LOG.isDebugEnabled()) {
                        Logger logger4 = LOG;
                        logger4.debug("Deleting old expired sessions expired before " + j5, new Object[0]);
                    }
                    PreparedStatement prepareStatement2 = connection.prepareStatement(this._deleteOldExpiredSessions);
                    prepareStatement2.setLong(1, j5);
                    int executeUpdate = prepareStatement2.executeUpdate();
                    if (LOG.isDebugEnabled()) {
                        Logger logger5 = LOG;
                        logger5.debug("Deleted " + executeUpdate + " rows of old sessions expired before " + j5, new Object[0]);
                    }
                    if (prepareStatement2 != null) {
                        try {
                            prepareStatement2.close();
                        } catch (Exception e) {
                            LOG.warn(e);
                        }
                    }
                }
            }
            this._lastScavengeTime = System.currentTimeMillis();
            if (LOG.isDebugEnabled()) {
                Logger logger6 = LOG;
                logger6.debug("Scavenge sweep ended at " + this._lastScavengeTime, new Object[0]);
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e2) {
                }
            }
        } catch (Exception e3) {
            try {
                if (isRunning()) {
                    LOG.warn("Problem selecting expired sessions", (Throwable) e3);
                } else {
                    LOG.ignore(e3);
                }
            } finally {
                this._lastScavengeTime = System.currentTimeMillis();
                if (LOG.isDebugEnabled()) {
                    Logger logger7 = LOG;
                    logger7.debug("Scavenge sweep ended at " + this._lastScavengeTime, new Object[0]);
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e22) {
                        LOG.warn(e22);
                    }
                }
            }
        } catch (Throwable th) {
            if (prepareStatement != null) {
                try {
                    prepareStatement.close();
                } catch (Exception e4) {
                    LOG.warn(e4);
                }
            }
            throw th;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: java.sql.Connection} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: java.sql.PreparedStatement} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v15, resolved type: java.sql.PreparedStatement} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v16, resolved type: java.sql.PreparedStatement} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v17, resolved type: java.sql.PreparedStatement} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v18, resolved type: java.sql.Statement} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v21, resolved type: java.sql.PreparedStatement} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v22, resolved type: java.sql.PreparedStatement} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v23, resolved type: java.sql.PreparedStatement} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v24, resolved type: java.sql.PreparedStatement} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v25, resolved type: java.sql.PreparedStatement} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v26, resolved type: java.sql.PreparedStatement} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v27, resolved type: java.sql.PreparedStatement} */
    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v2, types: [java.sql.Statement] */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: type inference failed for: r1v13 */
    /* JADX WARNING: type inference failed for: r1v14, types: [java.sql.Statement] */
    /* JADX WARNING: type inference failed for: r1v19 */
    /* JADX WARNING: type inference failed for: r1v20 */
    /* JADX WARNING: type inference failed for: r1v21 */
    /* JADX WARNING: type inference failed for: r1v22 */
    /* JADX WARNING: type inference failed for: r1v23 */
    /* JADX WARNING: type inference failed for: r1v24 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x0161 A[SYNTHETIC, Splitter:B:101:0x0161] */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x016d A[SYNTHETIC, Splitter:B:106:0x016d] */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x0179 A[SYNTHETIC, Splitter:B:111:0x0179] */
    /* JADX WARNING: Removed duplicated region for block: B:123:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0109 A[SYNTHETIC, Splitter:B:67:0x0109] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0124 A[SYNTHETIC, Splitter:B:75:0x0124] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0130 A[SYNTHETIC, Splitter:B:80:0x0130] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x013c A[SYNTHETIC, Splitter:B:85:0x013c] */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x0148 A[SYNTHETIC, Splitter:B:90:0x0148] */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0155 A[SYNTHETIC, Splitter:B:96:0x0155] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void cleanExpiredSessions() {
        /*
            r13 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            java.sql.Connection r2 = r13.getConnection()     // Catch:{ Exception -> 0x0103, all -> 0x00fe }
            r3 = 2
            r2.setTransactionIsolation(r3)     // Catch:{ Exception -> 0x00f8, all -> 0x00f5 }
            r3 = 0
            r2.setAutoCommit(r3)     // Catch:{ Exception -> 0x00f8, all -> 0x00f5 }
            java.lang.String r4 = r13._selectExpiredSessions     // Catch:{ Exception -> 0x00f8, all -> 0x00f5 }
            java.sql.PreparedStatement r4 = r2.prepareStatement(r4)     // Catch:{ Exception -> 0x00f8, all -> 0x00f5 }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            org.eclipse.jetty.util.log.Logger r7 = LOG     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            boolean r7 = r7.isDebugEnabled()     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            r8 = 1
            if (r7 == 0) goto L_0x0034
            org.eclipse.jetty.util.log.Logger r7 = LOG     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            java.lang.String r9 = "Searching for sessions expired before {}"
            java.lang.Object[] r10 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            java.lang.Long r11 = java.lang.Long.valueOf(r5)     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            r10[r3] = r11     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            r7.debug((java.lang.String) r9, (java.lang.Object[]) r10)     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
        L_0x0034:
            r4.setLong(r8, r5)     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            java.sql.ResultSet r5 = r4.executeQuery()     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
        L_0x003b:
            boolean r6 = r5.next()     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            if (r6 == 0) goto L_0x005e
            java.lang.String r6 = "sessionId"
            java.lang.String r6 = r5.getString(r6)     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            r0.add(r6)     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            org.eclipse.jetty.util.log.Logger r7 = LOG     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            boolean r7 = r7.isDebugEnabled()     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            if (r7 == 0) goto L_0x003b
            org.eclipse.jetty.util.log.Logger r7 = LOG     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            java.lang.String r9 = "Found expired sessionId={}"
            java.lang.Object[] r10 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            r10[r3] = r6     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            r7.debug((java.lang.String) r9, (java.lang.Object[]) r10)     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            goto L_0x003b
        L_0x005e:
            boolean r3 = r0.isEmpty()     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            if (r3 != 0) goto L_0x00ab
            java.sql.Statement r3 = r2.createStatement()     // Catch:{ Exception -> 0x00f0, all -> 0x00ed }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            r5.<init>()     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            java.lang.String r6 = "delete from "
            r5.append(r6)     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            java.lang.String r6 = r13._sessionTable     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            r5.append(r6)     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            java.lang.String r6 = " where sessionId in "
            r5.append(r6)     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            java.lang.String r5 = r13.createCleanExpiredSessionsSql(r5, r0)     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            r3.executeUpdate(r5)     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            java.sql.Statement r1 = r2.createStatement()     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            r5.<init>()     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            java.lang.String r6 = "delete from "
            r5.append(r6)     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            java.lang.String r6 = r13._sessionIdTable     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            r5.append(r6)     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            java.lang.String r6 = " where id in "
            r5.append(r6)     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            java.lang.String r5 = r13.createCleanExpiredSessionsSql(r5, r0)     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            r1.executeUpdate(r5)     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            goto L_0x00ac
        L_0x00ab:
            r3 = r1
        L_0x00ac:
            r2.commit()     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            java.util.HashSet<java.lang.String> r5 = r13._sessionIds     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            monitor-enter(r5)     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            java.util.HashSet<java.lang.String> r6 = r13._sessionIds     // Catch:{ all -> 0x00e3 }
            r6.removeAll(r0)     // Catch:{ all -> 0x00e3 }
            monitor-exit(r5)     // Catch:{ all -> 0x00e3 }
            if (r1 == 0) goto L_0x00c4
            r1.close()     // Catch:{ Exception -> 0x00be }
            goto L_0x00c4
        L_0x00be:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r1 = LOG
            r1.warn(r0)
        L_0x00c4:
            if (r3 == 0) goto L_0x00d0
            r3.close()     // Catch:{ Exception -> 0x00ca }
            goto L_0x00d0
        L_0x00ca:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r1 = LOG
            r1.warn(r0)
        L_0x00d0:
            if (r4 == 0) goto L_0x00dc
            r4.close()     // Catch:{ Exception -> 0x00d6 }
            goto L_0x00dc
        L_0x00d6:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r1 = LOG
            r1.warn(r0)
        L_0x00dc:
            if (r2 == 0) goto L_0x0183
            r2.close()     // Catch:{ SQLException -> 0x017d }
            goto L_0x0183
        L_0x00e3:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x00e3 }
            throw r0     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
        L_0x00e6:
            r0 = move-exception
            goto L_0x0122
        L_0x00e8:
            r0 = move-exception
            r12 = r2
            r2 = r1
            r1 = r12
            goto L_0x0107
        L_0x00ed:
            r0 = move-exception
            r3 = r1
            goto L_0x0122
        L_0x00f0:
            r0 = move-exception
            r3 = r1
            r1 = r2
            r2 = r3
            goto L_0x0107
        L_0x00f5:
            r0 = move-exception
            r3 = r1
            goto L_0x0101
        L_0x00f8:
            r0 = move-exception
            r3 = r1
            r4 = r3
            r1 = r2
            r2 = r4
            goto L_0x0107
        L_0x00fe:
            r0 = move-exception
            r2 = r1
            r3 = r2
        L_0x0101:
            r4 = r3
            goto L_0x0122
        L_0x0103:
            r0 = move-exception
            r2 = r1
            r3 = r2
            r4 = r3
        L_0x0107:
            if (r1 == 0) goto L_0x0153
            org.eclipse.jetty.util.log.Logger r5 = LOG     // Catch:{ Exception -> 0x0119 }
            java.lang.String r6 = "Rolling back clean of expired sessions"
            r5.warn((java.lang.String) r6, (java.lang.Throwable) r0)     // Catch:{ Exception -> 0x0119 }
            r1.rollback()     // Catch:{ Exception -> 0x0119 }
            goto L_0x0153
        L_0x0114:
            r0 = move-exception
            r12 = r2
            r2 = r1
            r1 = r12
            goto L_0x0122
        L_0x0119:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r5 = LOG     // Catch:{ all -> 0x0114 }
            java.lang.String r6 = "Rollback of expired sessions failed"
            r5.warn((java.lang.String) r6, (java.lang.Throwable) r0)     // Catch:{ all -> 0x0114 }
            goto L_0x0153
        L_0x0122:
            if (r1 == 0) goto L_0x012e
            r1.close()     // Catch:{ Exception -> 0x0128 }
            goto L_0x012e
        L_0x0128:
            r1 = move-exception
            org.eclipse.jetty.util.log.Logger r5 = LOG
            r5.warn(r1)
        L_0x012e:
            if (r3 == 0) goto L_0x013a
            r3.close()     // Catch:{ Exception -> 0x0134 }
            goto L_0x013a
        L_0x0134:
            r1 = move-exception
            org.eclipse.jetty.util.log.Logger r3 = LOG
            r3.warn(r1)
        L_0x013a:
            if (r4 == 0) goto L_0x0146
            r4.close()     // Catch:{ Exception -> 0x0140 }
            goto L_0x0146
        L_0x0140:
            r1 = move-exception
            org.eclipse.jetty.util.log.Logger r3 = LOG
            r3.warn(r1)
        L_0x0146:
            if (r2 == 0) goto L_0x0152
            r2.close()     // Catch:{ SQLException -> 0x014c }
            goto L_0x0152
        L_0x014c:
            r1 = move-exception
            org.eclipse.jetty.util.log.Logger r2 = LOG
            r2.warn(r1)
        L_0x0152:
            throw r0
        L_0x0153:
            if (r2 == 0) goto L_0x015f
            r2.close()     // Catch:{ Exception -> 0x0159 }
            goto L_0x015f
        L_0x0159:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r2 = LOG
            r2.warn(r0)
        L_0x015f:
            if (r3 == 0) goto L_0x016b
            r3.close()     // Catch:{ Exception -> 0x0165 }
            goto L_0x016b
        L_0x0165:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r2 = LOG
            r2.warn(r0)
        L_0x016b:
            if (r4 == 0) goto L_0x0177
            r4.close()     // Catch:{ Exception -> 0x0171 }
            goto L_0x0177
        L_0x0171:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r2 = LOG
            r2.warn(r0)
        L_0x0177:
            if (r1 == 0) goto L_0x0183
            r1.close()     // Catch:{ SQLException -> 0x017d }
            goto L_0x0183
        L_0x017d:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r1 = LOG
            r1.warn(r0)
        L_0x0183:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.session.JDBCSessionIdManager.cleanExpiredSessions():void");
    }

    private String createCleanExpiredSessionsSql(String str, Collection<String> collection) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append("(");
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            stringBuffer.append("'" + it.next() + "'");
            if (it.hasNext()) {
                stringBuffer.append(",");
            }
        }
        stringBuffer.append(")");
        if (LOG.isDebugEnabled()) {
            LOG.debug("Cleaning expired sessions with: {}", stringBuffer);
        }
        return stringBuffer.toString();
    }

    private void initializeDatabase() throws Exception {
        if (this._datasource == null) {
            if (this._jndiName != null) {
                this._datasource = (DataSource) new InitialContext().lookup(this._jndiName);
                return;
            }
            Driver driver = this._driver;
            if (driver == null || this._connectionUrl == null) {
                String str = this._driverClassName;
                if (str == null || this._connectionUrl == null) {
                    throw new IllegalStateException("No database configured for sessions");
                }
                Class.forName(str);
                return;
            }
            DriverManager.registerDriver(driver);
        }
    }
}
