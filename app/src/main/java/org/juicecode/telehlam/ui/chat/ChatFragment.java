package org.juicecode.hlam.ui.chat;

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

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import org.juicecode.hlam.MainActivity;
import org.juicecode.hlam.R;
import org.juicecode.hlam.core.DBClient;
import org.juicecode.hlam.core.contacts.AppDataBase;
import org.juicecode.hlam.core.contacts.Contact;
import org.juicecode.hlam.core.contacts.ContactDao;
import org.juicecode.hlam.core.contacts.InsertContact;
import org.juicecode.hlam.core.messaging.IncomingMessage;
import org.juicecode.hlam.core.messaging.Message;
import org.juicecode.hlam.core.messaging.OutgoingMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatFragment extends Fragment {
    RecyclerView chat;
    TextView nameOfContact;
    EditText messageField;
    ImageButton sendbutton;
    ImageButton goBack;
    String phoneNumber;
    String nameOfContactValue;
    Context context;
    MessageChatAdapter messageChatAdapter;
    List<Message> messageList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);

        Context context = getContext();
        chat = view.findViewById(R.id.chat);
        messageField = view.findViewById(R.id.message_field);

        Context context = getContext();
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

                hideKeyboard(getActivity());
                getActivity().onBackPressed();
                messageField.clearFocus();
            }
        });

        return view;
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();

        if (view != null) {

            // hide the keyboard
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}