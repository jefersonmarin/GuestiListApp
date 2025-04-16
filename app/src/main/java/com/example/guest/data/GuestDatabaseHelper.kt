import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.guest.data.Guest

class GuestDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, "databaseApp", null, 1
) {
    companion object {
        private const val TABLE_NAME = "tbl_guest"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_CONFIRMED = "confirmed"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_EMAIL TEXT,
                $COLUMN_PHONE TEXT,
                $COLUMN_CONFIRMED INTEGER DEFAULT 0
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertGuest(guest: Guest): Long {
        val db = this.writableDatabase
        //insert into tbl_guest (name, email, phone)
        //             values (guest.name, guest.email...)
        val values = ContentValues().apply {
            put(COLUMN_NAME, guest.name)
            put(COLUMN_EMAIL, guest.email)
            put(COLUMN_PHONE, guest.phone)
            put(COLUMN_CONFIRMED, guest.confirmed)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun updateGuest(guest: Guest): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, guest.name)
            put(COLUMN_EMAIL, guest.email)
            put(COLUMN_PHONE, guest.phone)
            put(COLUMN_CONFIRMED, guest.confirmed)
        }
        return db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(guest.id.toString()))
    }

    fun deleteGuest(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun getAllGuests(): List<Guest> {
        val guests = mutableListOf<Guest>()
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_NAME ASC"
        )

        with(cursor) {
            while (moveToNext()) {
                val guest = Guest(
                    id = getInt(getColumnIndexOrThrow(COLUMN_ID)),
                    name = getString(getColumnIndexOrThrow(COLUMN_NAME)),
                    email = getString(getColumnIndexOrThrow(COLUMN_EMAIL)),
                    phone = getString(getColumnIndexOrThrow(COLUMN_PHONE)),
                    confirmed = getInt(getColumnIndexOrThrow(COLUMN_CONFIRMED))
                )
                guests.add(guest)
            }
        }
        cursor.close()
        return guests
    }
}