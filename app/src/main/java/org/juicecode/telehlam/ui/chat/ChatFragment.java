package org.juicecode.telehlam.ui.chat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.core.contacts.Contact;
import org.juicecode.telehlam.database.DataBaseTask;
import org.juicecode.telehlam.database.messages.Message;
import org.juicecode.telehlam.ui.home.HomeFragment;
import org.juicecode.telehlam.utils.DrawerLocker;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;
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

        final DrawerLocker drawerLocker = (DrawerLocker) view.getContext();
        drawerLocker.setDrawerLock(true);

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
        DataBaseTask<List<Message>> getMessages = new DataBaseTask<>(getContext(),getViewLifecycleOwner(),messageChatAdapter,chat,phoneNumber, DataBaseTask.Task.GetAllMessages);
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
                    DataBaseTask<Void> dataBaseTask = new DataBaseTask<>(getContext(),new Contact(nameOfContactValue, phoneNumber),message, DataBaseTask.Task.InsertMessage);
                    dataBaseTask.execute();
                    messageChatAdapter.addItem(message);
                    messageField.setText("");
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
               drawerLocker.setDrawerLock(false);
            }
        });

        return view;
    }
}