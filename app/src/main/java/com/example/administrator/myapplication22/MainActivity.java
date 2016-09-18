package com.example.administrator.myapplication22;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button getAll;
    private ContentResolver resolver;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAll = (Button)findViewById(R.id.btn_getAll);

        resolver = this.getContentResolver();

        getAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ContactsContract.Contacts是一张表，代表了所有联系人的统计信息,需要打开权限
                Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

                while (cursor.moveToNext()) {

                    String msg;

                    //getId
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    msg = "id:" + id + "\n";

                    //getName
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    msg = msg + "name:" + name + "\n";

                    //getPhone
                    Cursor phoneNumbers = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);
                    while (phoneNumbers.moveToNext()) {
                        String phoneNumber = phoneNumbers.getString(phoneNumbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        msg = msg + "phone:" + phoneNumber + "\n";
                    }

                    //getEmail
                    Cursor emails = resolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + id, null, null);
                    while (emails.moveToNext()) {
                        String email = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        msg = msg + "email:" + email + "\n";
                    }

                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}