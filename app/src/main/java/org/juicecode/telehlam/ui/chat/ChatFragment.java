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
import org.juicecode.telehlam.core.contacts.ContactTask;
import org.juicecode.telehlam.database.messages.GetMessages;
import org.juicecode.telehlam.database.messages.InsertMessage;
import org.juicecode.telehlam.database.messages.Message;
import org.juicecode.telehlam.utils.KeyboardManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatFragment extends Fragment {
    RecyclerView chat;
    TextView nameOfContact;
    EditText messageField;
    ImageButton sendbutton;
    ImageButton goBack;
    String nameOfContactValue;
    String phoneNumber;
    MessageChatAdapter messageChatAdapter;
    List<Message> messageList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);

        Context context = getContext();
        chat = view.findViewById(R.id.chat);
        LinearLayoutManager linearLayout = new LinearLayoutManager(context);
        chat.setLayoutManager(linearLayout);
        messageChatAdapter = new MessageChatAdapter();
        Bundle arguments = getArguments();
        String[] values = arguments.getStringArray("information");
        nameOfContactValue = values[0];
        phoneNumber = values[1];
        chat.setAdapter(messageChatAdapter);
        chat.setHasFixedSize(false);
        chat.setNestedScrollingEnabled(false);
        messageField = view.findViewById(R.id.message_field);
        messageList = new ArrayList<>();
        GetMessages getMessages = new GetMessages(getContext(), getViewLifecycleOwner(), messageChatAdapter, chat, phoneNumber);
        getMessages.execute();
        sendbutton = view.findViewById(R.id.send_message_button);
        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageField.getText().toString().trim();

                if (!messageText.isEmpty()) {
                    Message message;
                    // TODO(all): delete test code
                    if (new Random().nextBoolean()) {
                        message = new Message(Message.MESSAGE_OUTGOING, messageText, "user", phoneNumber);
                    } else {
                        message = new Message(Message.MESSAGE_INCOMING, messageText, phoneNumber, "user");
                    }
                    InsertMessage insertMessage = new InsertMessage(getActivity(), message);
                    insertMessage.execute();
                    messageChatAdapter.addItem(message);
                    messageField.setText("");
                    ContactTask<Void> contactTask = new ContactTask<>(getContext(),new Contact(nameOfContactValue, phoneNumber), ContactTask.Task.Insert);
                    contactTask.execute();

                }
            }
        });


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
}