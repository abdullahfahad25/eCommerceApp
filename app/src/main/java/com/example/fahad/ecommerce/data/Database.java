package com.example.fahad.ecommerce.data;

import static com.example.fahad.ecommerce.utils.Constants.DB_TABLE_USERS;
import static com.example.fahad.ecommerce.utils.Constants.DB_USERS_COLUMN_NAME;
import static com.example.fahad.ecommerce.utils.Constants.DB_USERS_COLUMN_PASSWORD;
import static com.example.fahad.ecommerce.utils.Constants.DB_USERS_COLUMN_PHONE;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.fahad.ecommerce.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Database {
    private static final String TAG = "Database";

    private final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    private Database() {}

    public static Database getInstance() {
        return new Database();
    }

    public void register(RegisterActivity.onCompleteListener completeListener, final String name,
                           final String phone, final String password) {
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.child(DB_TABLE_USERS).child(phone).exists()) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put(DB_USERS_COLUMN_PHONE, phone);
                    userdataMap.put(DB_USERS_COLUMN_PASSWORD, password);
                    userdataMap.put(DB_USERS_COLUMN_NAME, name);


                    rootRef.child(DB_TABLE_USERS).child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                completeListener.onSuccess();
                            } else {
                                completeListener.onFailed();
                            }
                        }
                    });
                } else {
                    completeListener.onExistsAlready(phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "DB Error. msg: " + error.getMessage());
                completeListener.onCancelled(error.getMessage());
            }
        });
    }
}
