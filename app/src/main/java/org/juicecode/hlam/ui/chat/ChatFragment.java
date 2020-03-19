package org.juicecode.hlam.ui.chat;

import android.content.Context;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
            // TODO(all): replace this test code
            private boolean isOutgoing = false;

            @Override
            public void onClick(View v) {
                String messageText = messageField.getText()
                        .toString()
                        .trim();
                if (!messageText.isEmpty()) {
                    Message message;

                    if (new Random().nextBoolean()) {
                        message = new OutgoingMessage(messageText, null);
                    } else {
                        message = new IncomingMessage(messageText, null);
                    }
                    messageListAdapter.addItem(message);
                    messageField.setText("");
                }
            }
        });

        Bundle arguments = getArguments();
        String nameOfContactValue = arguments.getString("chatName");
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

}