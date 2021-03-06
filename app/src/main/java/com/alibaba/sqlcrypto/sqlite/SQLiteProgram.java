package com.alibaba.sqlcrypto.sqlite;

import com.alibaba.sqlcrypto.DatabaseUtils;
import java.util.Arrays;
import java.util.Locale;

public abstract class SQLiteProgram extends SQLiteClosable {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private final Object[] mBindArgs;
    private final String[] mColumnNames;
    private final SQLiteDatabase mDatabase;
    private final int mNumParameters;
    private final boolean mReadOnly;
    private final String mSql;

    SQLiteProgram(SQLiteDatabase db, String sql, Object[] bindArgs, Object cancellationSignalForPrepare) {
        boolean assumeReadOnly = true;
        this.mDatabase = db;
        this.mSql = sql.trim();
        int n = DatabaseUtils.getSqlStatementType(this.mSql);
        switch (n) {
            case 4:
            case 5:
            case 6:
                this.mReadOnly = false;
                this.mColumnNames = EMPTY_STRING_ARRAY;
                this.mNumParameters = 0;
                break;
            default:
                assumeReadOnly = n != 1 ? false : assumeReadOnly;
                SQLiteStatementInfo info = new SQLiteStatementInfo();
                db.getThreadSession().prepare(this.mSql, db.getThreadDefaultConnectionFlags(assumeReadOnly), cancellationSignalForPrepare, info);
                boolean ischeckChanges = false;
                try {
                    ischeckChanges = "SELECT CHANGES()".equals(sql.toUpperCase(Locale.ROOT));
                } catch (Throwable th) {
                }
                if (ischeckChanges) {
                    this.mReadOnly = false;
                } else {
                    this.mReadOnly = info.readOnly;
                }
                this.mColumnNames = info.columnNames;
                this.mNumParameters = info.numParameters;
                break;
        }
        if (bindArgs != null && bindArgs.length > this.mNumParameters) {
            throw new IllegalArgumentException("Too many bind arguments.  " + bindArgs.length + " arguments were provided but the statement needs " + this.mNumParameters + " arguments.");
        } else if (this.mNumParameters != 0) {
            this.mBindArgs = new Object[this.mNumParameters];
            if (bindArgs != null) {
                System.arraycopy(bindArgs, 0, this.mBindArgs, 0, bindArgs.length);
            }
        } else {
            this.mBindArgs = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public final SQLiteDatabase getDatabase() {
        return this.mDatabase;
    }

    /* access modifiers changed from: 0000 */
    public final String getSql() {
        return this.mSql;
    }

    /* access modifiers changed from: 0000 */
    public final Object[] getBindArgs() {
        return this.mBindArgs;
    }

    /* access modifiers changed from: 0000 */
    public final String[] getColumnNames() {
        return this.mColumnNames;
    }

    /* access modifiers changed from: protected */
    public final SQLiteSession getSession() {
        return this.mDatabase.getThreadSession();
    }

    /* access modifiers changed from: protected */
    public final int getConnectionFlags() {
        return this.mDatabase.getThreadDefaultConnectionFlags(this.mReadOnly);
    }

    /* access modifiers changed from: protected */
    public final void onCorruption() {
        this.mDatabase.onCorruption();
    }

    @Deprecated
    public final int getUniqueId() {
        return -1;
    }

    public void bindNull(int index) {
        bind(index, null);
    }

    public void bindLong(int index, long value) {
        bind(index, Long.valueOf(value));
    }

    public void bindDouble(int index, double value) {
        bind(index, Double.valueOf(value));
    }

    public void bindString(int index, String value) {
        if (value == null) {
            throw new IllegalArgumentException("the bind value at index " + index + " is null");
        }
        bind(index, value);
    }

    public void bindBlob(int index, byte[] value) {
        if (value == null) {
            throw new IllegalArgumentException("the bind value at index " + index + " is null");
        }
        bind(index, value);
    }

    public void clearBindings() {
        if (this.mBindArgs != null) {
            Arrays.fill(this.mBindArgs, null);
        }
    }

    public void bindAllArgsAsStrings(String[] bindArgs) {
        if (bindArgs != null) {
            for (int i = bindArgs.length; i != 0; i--) {
                bindString(i, bindArgs[i - 1]);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onAllReferencesReleased() {
        clearBindings();
    }

    private void bind(int index, Object value) {
        if (index <= 0 || index > this.mNumParameters) {
            throw new IllegalArgumentException("Cannot bind argument at index " + index + " because the index is out of range.  The statement has " + this.mNumParameters + " parameters.");
        }
        this.mBindArgs[index - 1] = value;
    }
}
