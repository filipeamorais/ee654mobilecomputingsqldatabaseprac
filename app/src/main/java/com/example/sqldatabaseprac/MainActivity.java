package com.example.sqldatabaseprac;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
public class MainActivity extends AppCompatActivity {
    DBHandler dbh;
    TextView textV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textV = (TextView) findViewById(R.id.textV);
        dbh = new DBHandler(this);
        dbh.addFriend(new Friend(
                0, "John", "Smith", "john@uab.edu"));
        dbh.addFriend(new Friend(
                0, "Mary", "Tyler", "mary@uab.edu"));
        dbh.addFriend(new Friend(
                0, "Bob", "Johnson", "bob@uab.edu"));
    }
    void showRecords(View v) {
        List<Friend> friends = dbh.getAllFriends();
        String str = "";
        for (Friend f : friends) {
            String row = f.getId() + ": First Name: " +
                    f.getFirstName() + ", Last Name: " +
                    f.getLastName() + ", Email: " + f.getEmail();
            str += row + "\n";
        }
        textV.setText(str);
    }
    void deleteRecords(View v) {
        textV.setText("");
        dbh.clearDatabase();
    }
}

