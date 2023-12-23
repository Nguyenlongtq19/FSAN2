package com.kbyai.faceattribute;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kbyai.faceattribute.DBManager;
import com.kbyai.faceattribute.Person;
import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends ArrayAdapter<Person> {

    DBManager dbManager;
    private Context context;

    public PersonAdapter(Context context, List<Person> persons) {
        super(context, 0, persons);
        this.context = context;
    }

    public PersonAdapter(Context context, ArrayList<Person> personList) {
        super(context, 0, personList);

        dbManager = new DBManager(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Person person = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_person, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.textName);
        ImageView faceView = (ImageView) convertView.findViewById(R.id.imageFace);
        convertView.findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                dbManager.deletePerson(DBManager.personList.get(position).name);
                                notifyDataSetChanged();
            }
        });

        tvName.setText(getPosition(person) + 1 + ". " + person.name + " " + person.id);
        faceView.setImageBitmap(person.face);
        // Return the completed view to render on screen
        return convertView;
    }
}