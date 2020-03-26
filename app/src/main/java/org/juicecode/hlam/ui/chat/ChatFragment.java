package org.juicecode.hlam.ui.chat;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.hlam.R;
import org.juicecode.hlam.core.DBClient;
import org.juicecode.hlam.core.contacts.AppDataBase;
import org.juicecode.hlam.core.contacts.Contact;
import org.juicecode.hlam.core.contacts.ContactDao;
import org.juicecode.hlam.core.messaging.IncomingMessage;
import org.juicecode.hlam.core.messaging.Message;
import org.juicecode.hlam.core.messaging.OutgoingMessage;

import java.util.Random;

public class ChatFragment extends Fragment {
    RecyclerView chat;
    TextView nameOfContact;
    EditText messageField;
    ImageButton sendbutton;
    ImageButton goBack;
    String phoneNumber;
    String nameOfContactValue;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);

        chat = view.findViewById(R.id.chat);
        messageField = view.findViewById(R.id.message_field);

        Context context = getContext();
        LinearLayoutManager linearLayout = new LinearLayoutManager(context);
        chat.setLayoutManager(linearLayout);
        final MessageChatAdapter messageListAdapter = new MessageChatAdapter();

        chat.setAdapter(messageListAdapter);
        chat.setHasFixedSize(false);
        chat.setNestedScrollingEnabled(false); // What it mean?

        sendbutton = view.findViewById(R.id.send_message_button);
        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageField.getText()
                        .toString()
                        .trim();
                if (!messageText.isEmpty()) {
                    Message message;
                    // TODO(all): delete test code
                    if (new Random().nextBoolean()) {
                        message = new OutgoingMessage(messageText);
                    } else {
                        message = new IncomingMessage(messageText);
                    }
                    messageListAdapter.addItem(message);
                    messageField.setText("");
                    AddMessageInsertContact(new Contact(nameOfContactValue,phoneNumber));
                }
            }
        });

        Bundle arguments = getArguments();
        String[] values = arguments.getStringArray("information");
        nameOfContactValue = values[0];
        phoneNumber = values[1];
        nameOfContact = view.findViewById(R.id.chat_name);
        nameOfContact.setText(nameOfContactValue);

        goBack = view.findViewById(R.id.go_back_button);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
                messageField.clearFocus();
            }
        });

        return view;
    }
    public void AddMessageInsertContact(final Contact contact){
        class InsertContact extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                AppDataBase appDataBase = DBClient.getInstance(getContext()).getAppDatabase();
                ContactDao contactDao = appDataBase.contactDao();

                    //code for adding message
                    contactDao.insert(contact);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //code of adding messages in RecyclerView
            }
        }
        InsertContact insertContact = new InsertContact();
        insertContact.execute();
    }

}