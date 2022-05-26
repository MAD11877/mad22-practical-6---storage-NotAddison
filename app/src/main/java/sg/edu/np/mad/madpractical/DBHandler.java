package sg.edu.np.mad.madpractical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String TAG = "DB";
    public static String DATABASE_NAME = "Users.db";
    public static String TABLE_USERS = "Users";
    public static String COLUMN_NAME= "Name";
    public static String COLUMN_DESCRIPTION = "Description";
    public static String COLUMN_ID = "id";
    public static String COLUMN_FOLLOWED = "Followed";
    public static int DATABASE_VERSION = 1;

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + TABLE_USERS
                + "("
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FOLLOWED + " TEXT"
                + ")";
        db.execSQL(CREATE_ACCOUNT_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
        onCreate(db);
    }

    public ArrayList<User> getUser(){
        String query = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);


        ArrayList<User> userList = new ArrayList<User>();

        if (cursor.moveToFirst()){
            do{
                User queryData = new User();
                queryData.setName(cursor.getString(0));
                queryData.setDescription(cursor.getString(1));
                queryData.setId(Integer.parseInt(cursor.getString(2)));
                queryData.setFollowed(Boolean.parseBoolean(cursor.getString(3)));
                Log.v(TAG,"User"+ queryData.getName());
                userList.add(queryData);
            }
            while(cursor.moveToNext());
        }

        db.close();
        return userList;
    }

    public void addUser(User user){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_DESCRIPTION, user.getDescription());
        values.put(COLUMN_ID, user.getId());
        values.put(COLUMN_FOLLOWED, user.isFollowed());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public void updateUser(User user){
        String query = "SELECT * FROM "+ TABLE_USERS
                + " WHERE " + COLUMN_NAME + "=\"" + user.getName() + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // Getting Values
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_DESCRIPTION, user.getDescription());
        values.put(COLUMN_ID, user.getId());
        values.put(COLUMN_FOLLOWED, user.isFollowed());


        // Update DB
        db.update(TABLE_USERS, values,COLUMN_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
        cursor.close();
        db.close();
    }
}
