package org.eclipse.paho.android.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Iterator;
import java.util.UUID;
import org.eclipse.paho.android.service.MessageStore;
import org.eclipse.paho.client.mqttv3.MqttMessage;

class DatabaseMessageStore implements MessageStore {
    private static final String ARRIVED_MESSAGE_TABLE_NAME = "MqttArrivedMessageTable";
    private static final String MTIMESTAMP = "mtimestamp";
    private static final String TAG = "DatabaseMessageStore";
    /* access modifiers changed from: private */

    /* renamed from: db */
    public SQLiteDatabase f4127db = null;
    /* access modifiers changed from: private */
    public MQTTDatabaseHelper mqttDb = null;
    private MqttTraceHandler traceHandler = null;

    private static class MQTTDatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "mqttAndroidService.db";
        private static final int DATABASE_VERSION = 1;
        private static final String TAG = "MQTTDatabaseHelper";
        private MqttTraceHandler traceHandler = null;

        public MQTTDatabaseHelper(MqttTraceHandler mqttTraceHandler, Context context) {
            super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
            this.traceHandler = mqttTraceHandler;
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            MqttTraceHandler mqttTraceHandler = this.traceHandler;
            mqttTraceHandler.traceDebug(TAG, "onCreate {" + "CREATE TABLE MqttArrivedMessageTable(messageId TEXT PRIMARY KEY, clientHandle TEXT, destinationName TEXT, payload BLOB, qos INTEGER, retained TEXT, duplicate TEXT, mtimestamp INTEGER);" + "}");
            try {
                sQLiteDatabase.execSQL("CREATE TABLE MqttArrivedMessageTable(messageId TEXT PRIMARY KEY, clientHandle TEXT, destinationName TEXT, payload BLOB, qos INTEGER, retained TEXT, duplicate TEXT, mtimestamp INTEGER);");
                this.traceHandler.traceDebug(TAG, "created the table");
            } catch (SQLException e) {
                this.traceHandler.traceException(TAG, "onCreate", e);
                throw e;
            }
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            this.traceHandler.traceDebug(TAG, "onUpgrade");
            try {
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS MqttArrivedMessageTable");
                onCreate(sQLiteDatabase);
                this.traceHandler.traceDebug(TAG, "onUpgrade complete");
            } catch (SQLException e) {
                this.traceHandler.traceException(TAG, "onUpgrade", e);
                throw e;
            }
        }
    }

    public DatabaseMessageStore(MqttService mqttService, Context context) {
        this.traceHandler = mqttService;
        this.mqttDb = new MQTTDatabaseHelper(this.traceHandler, context);
        this.traceHandler.traceDebug(TAG, "DatabaseMessageStore<init> complete");
    }

    public String storeArrived(String str, String str2, MqttMessage mqttMessage) {
        this.f4127db = this.mqttDb.getWritableDatabase();
        MqttTraceHandler mqttTraceHandler = this.traceHandler;
        mqttTraceHandler.traceDebug(TAG, "storeArrived{" + str + "}, {" + mqttMessage.toString() + "}");
        byte[] payload = mqttMessage.getPayload();
        int qos = mqttMessage.getQos();
        boolean isRetained = mqttMessage.isRetained();
        boolean isDuplicate = mqttMessage.isDuplicate();
        ContentValues contentValues = new ContentValues();
        String uuid = UUID.randomUUID().toString();
        contentValues.put("messageId", uuid);
        contentValues.put(MqttServiceConstants.CLIENT_HANDLE, str);
        contentValues.put(MqttServiceConstants.DESTINATION_NAME, str2);
        contentValues.put("payload", payload);
        contentValues.put(MqttServiceConstants.QOS, Integer.valueOf(qos));
        contentValues.put(MqttServiceConstants.RETAINED, Boolean.valueOf(isRetained));
        contentValues.put("duplicate", Boolean.valueOf(isDuplicate));
        contentValues.put(MTIMESTAMP, Long.valueOf(System.currentTimeMillis()));
        try {
            this.f4127db.insertOrThrow(ARRIVED_MESSAGE_TABLE_NAME, (String) null, contentValues);
            int arrivedRowCount = getArrivedRowCount(str);
            MqttTraceHandler mqttTraceHandler2 = this.traceHandler;
            mqttTraceHandler2.traceDebug(TAG, "storeArrived: inserted message with id of {" + uuid + "} - Number of messages in database for this clientHandle = " + arrivedRowCount);
            return uuid;
        } catch (SQLException e) {
            this.traceHandler.traceException(TAG, "onUpgrade", e);
            throw e;
        }
    }

    private int getArrivedRowCount(String str) {
        int i = 0;
        SQLiteDatabase sQLiteDatabase = this.f4127db;
        Cursor query = sQLiteDatabase.query(ARRIVED_MESSAGE_TABLE_NAME, new String[]{"messageId"}, "clientHandle=?", new String[]{str}, (String) null, (String) null, (String) null);
        if (query.moveToFirst()) {
            i = query.getInt(0);
        }
        query.close();
        return i;
    }

    public boolean discardArrived(String str, String str2) {
        this.f4127db = this.mqttDb.getWritableDatabase();
        MqttTraceHandler mqttTraceHandler = this.traceHandler;
        mqttTraceHandler.traceDebug(TAG, "discardArrived{" + str + "}, {" + str2 + "}");
        try {
            int delete = this.f4127db.delete(ARRIVED_MESSAGE_TABLE_NAME, "messageId=? AND clientHandle=?", new String[]{str2, str});
            if (delete != 1) {
                MqttTraceHandler mqttTraceHandler2 = this.traceHandler;
                mqttTraceHandler2.traceError(TAG, "discardArrived - Error deleting message {" + str2 + "} from database: Rows affected = " + delete);
                return false;
            }
            int arrivedRowCount = getArrivedRowCount(str);
            MqttTraceHandler mqttTraceHandler3 = this.traceHandler;
            mqttTraceHandler3.traceDebug(TAG, "discardArrived - Message deleted successfully. - messages in db for this clientHandle " + arrivedRowCount);
            return true;
        } catch (SQLException e) {
            this.traceHandler.traceException(TAG, "discardArrived", e);
            throw e;
        }
    }

    public Iterator<MessageStore.StoredMessage> getAllArrivedMessages(final String str) {
        return new Iterator<MessageStore.StoredMessage>() {

            /* renamed from: c */
            private Cursor f4128c;
            private boolean hasNext;
            private final String[] selectionArgs = {str};

            {
                DatabaseMessageStore databaseMessageStore = DatabaseMessageStore.this;
                SQLiteDatabase unused = databaseMessageStore.f4127db = databaseMessageStore.mqttDb.getWritableDatabase();
                if (str == null) {
                    this.f4128c = DatabaseMessageStore.this.f4127db.query(DatabaseMessageStore.ARRIVED_MESSAGE_TABLE_NAME, (String[]) null, (String) null, (String[]) null, (String) null, (String) null, "mtimestamp ASC");
                } else {
                    this.f4128c = DatabaseMessageStore.this.f4127db.query(DatabaseMessageStore.ARRIVED_MESSAGE_TABLE_NAME, (String[]) null, "clientHandle=?", this.selectionArgs, (String) null, (String) null, "mtimestamp ASC");
                }
                this.hasNext = this.f4128c.moveToFirst();
            }

            public boolean hasNext() {
                if (!this.hasNext) {
                    this.f4128c.close();
                }
                return this.hasNext;
            }

            public MessageStore.StoredMessage next() {
                Cursor cursor = this.f4128c;
                String string = cursor.getString(cursor.getColumnIndex("messageId"));
                Cursor cursor2 = this.f4128c;
                String string2 = cursor2.getString(cursor2.getColumnIndex(MqttServiceConstants.CLIENT_HANDLE));
                Cursor cursor3 = this.f4128c;
                String string3 = cursor3.getString(cursor3.getColumnIndex(MqttServiceConstants.DESTINATION_NAME));
                Cursor cursor4 = this.f4128c;
                byte[] blob = cursor4.getBlob(cursor4.getColumnIndex("payload"));
                Cursor cursor5 = this.f4128c;
                int i = cursor5.getInt(cursor5.getColumnIndex(MqttServiceConstants.QOS));
                Cursor cursor6 = this.f4128c;
                boolean parseBoolean = Boolean.parseBoolean(cursor6.getString(cursor6.getColumnIndex(MqttServiceConstants.RETAINED)));
                Cursor cursor7 = this.f4128c;
                boolean parseBoolean2 = Boolean.parseBoolean(cursor7.getString(cursor7.getColumnIndex("duplicate")));
                MqttMessageHack mqttMessageHack = new MqttMessageHack(blob);
                mqttMessageHack.setQos(i);
                mqttMessageHack.setRetained(parseBoolean);
                mqttMessageHack.setDuplicate(parseBoolean2);
                this.hasNext = this.f4128c.moveToNext();
                return new DbStoredData(string, string2, string3, mqttMessageHack);
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            /* access modifiers changed from: protected */
            public void finalize() throws Throwable {
                this.f4128c.close();
                super.finalize();
            }
        };
    }

    public void clearArrivedMessages(String str) {
        int i;
        this.f4127db = this.mqttDb.getWritableDatabase();
        String[] strArr = {str};
        if (str == null) {
            this.traceHandler.traceDebug(TAG, "clearArrivedMessages: clearing the table");
            i = this.f4127db.delete(ARRIVED_MESSAGE_TABLE_NAME, (String) null, (String[]) null);
        } else {
            MqttTraceHandler mqttTraceHandler = this.traceHandler;
            mqttTraceHandler.traceDebug(TAG, "clearArrivedMessages: clearing the table of " + str + " messages");
            i = this.f4127db.delete(ARRIVED_MESSAGE_TABLE_NAME, "clientHandle=?", strArr);
        }
        MqttTraceHandler mqttTraceHandler2 = this.traceHandler;
        mqttTraceHandler2.traceDebug(TAG, "clearArrivedMessages: rows affected = " + i);
    }

    private class DbStoredData implements MessageStore.StoredMessage {
        private String clientHandle;
        private MqttMessage message;
        private String messageId;
        private String topic;

        DbStoredData(String str, String str2, String str3, MqttMessage mqttMessage) {
            this.messageId = str;
            this.topic = str3;
            this.message = mqttMessage;
        }

        public String getMessageId() {
            return this.messageId;
        }

        public String getClientHandle() {
            return this.clientHandle;
        }

        public String getTopic() {
            return this.topic;
        }

        public MqttMessage getMessage() {
            return this.message;
        }
    }

    private class MqttMessageHack extends MqttMessage {
        public MqttMessageHack(byte[] bArr) {
            super(bArr);
        }

        /* access modifiers changed from: protected */
        public void setDuplicate(boolean z) {
            super.setDuplicate(z);
        }
    }

    public void close() {
        SQLiteDatabase sQLiteDatabase = this.f4127db;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
    }
}
