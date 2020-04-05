package org.juicecode.telehlam.ui.chat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.core.contacts.Contact;
import org.juicecode.telehlam.core.contacts.InsertContact;
import org.juicecode.telehlam.database.DBClient;
import org.juicecode.telehlam.database.messages.Message;
import org.juicecode.telehlam.utils.KeyboardManager;

import java.util.Random;

public class ChatFragment extends Fragment {
    RecyclerView chat;
    TextView nameOfContact;
    EditText messageField;
    ImageButton sendbutton;
    ImageButton goBack;
    String nameOfContactValue;
    String phoneNumber;
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
                String messageText = messageField.getText().toString().trim();

                if (!messageText.isEmpty()) {
                    Message message;
                    // TODO(all): delete test code
                    if (new Random().nextBoolean()) {
                        message = new Message(Message.MESSAGE_OUTGOING, messageText,"user",nameOfContactValue);
                    } else {
                        message = new Message(Message.MESSAGE_INCOMING, messageText,nameOfContactValue,"user");
                    }

                    /*DBClient
                            .getInstance(getActivity())
                            .getAppDataBase()
                            .messageDao()
                            .insert(message);*/
                    messageListAdapter.addItem(message);
                    messageField.setText("");
                    AddMessageInsertContact(new Contact(nameOfContactValue, phoneNumber));

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
                KeyboardManager.hideKeyboard(getActivity());
                getActivity().onBackPressed();
            }
        });

        return view;
    }
    public void AddMessageInsertContact(final Contact contact) {

        InsertContact insertContact = new InsertContact(getContext(), contact);
        insertContact.execute();
    }

}