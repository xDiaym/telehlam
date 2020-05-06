package org.juicecode.telehlam.ui.chat;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.github.nkzawa.socketio.client.Socket;

import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.R;
import org.juicecode.telehlam.core.contacts.Contact;
import org.juicecode.telehlam.database.DataBaseTask;
import org.juicecode.telehlam.database.messages.Message;
import org.juicecode.telehlam.socketio.AppSocket;
import org.juicecode.telehlam.socketio.onMessageCallback;
import org.juicecode.telehlam.socketio.onMessageListener;
import org.juicecode.telehlam.utils.KeyboardManager;
import org.juicecode.telehlam.utils.SharedPreferencesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatFragment extends Fragment implements onMessageCallback {
    RecyclerView chat;
    TextView nameOfContact;
    EditText messageField;
    ImageButton sendbutton;
    ImageButton goBack;
    long userId;
    long receiverId;
    MessageChatAdapter messageChatAdapter;
    List<Message> messageList;
    Socket socket;
    Context context;
    String receiverLogin;
    SharedPreferences sharedPreferences;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        context = getContext();
        socket = AppSocket.getSocket();
        socket.on("message",new onMessageListener((MainActivity)getActivity(),this));
        //all variables get their values
        final Context context = getContext();
        chat = view.findViewById(R.id.chat);
        LinearLayoutManager linearLayout = new LinearLayoutManager(context);
        chat.setLayoutManager(linearLayout);
        messageChatAdapter = new MessageChatAdapter();
        Bundle arguments = getArguments();
        String[] values = arguments.getStringArray("information");
        receiverLogin = values[0];
        userId = new SharedPreferencesRepository(context).getId();
        chat.setAdapter(messageChatAdapter);
        chat.setHasFixedSize(false);
        chat.setNestedScrollingEnabled(false);
        messageField = view.findViewById(R.id.message_field);
        messageList = new ArrayList<>();
        sendbutton = view.findViewById(R.id.send_message_button);
        nameOfContact = view.findViewById(R.id.chat_name);
        nameOfContact.setText(receiverLogin);
        goBack = view.findViewById(R.id.go_back_button);
        //getting all messages for chat
        DataBaseTask<List<Message>> getMessages = new DataBaseTask<>(getContext(), getViewLifecycleOwner(), messageChatAdapter, chat,receiverId, DataBaseTask.Task.GetAllMessages);
        getMessages.execute();


        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageField.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    //TODO(all): delete test code
                    Message message;


                    if (new Random().nextBoolean()) {
                        message = new Message(Message.MESSAGE_OUTGOING, messageText, userId, receiverId);
                    } else {
                        message = new Message(Message.MESSAGE_INCOMING, messageText, receiverId, userId);
                    }
                    DataBaseTask<Void> dataBaseTask = new DataBaseTask<>(context, new Contact(receiverLogin), message, DataBaseTask.Task.InsertMessage);
                    dataBaseTask.execute();
                    /*
                    code for emitting messages is not ready
                    message = new Message(Message.MESSAGE_OUTGOING,messageText,userNick, receiverNick);

                    socket.emit("message",message);
                    */

                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    @Override
    public void savingIncomingMessage(String message) {
        Message incomingMessage;
        incomingMessage = new Message(Message.MESSAGE_INCOMING,message,userId, receiverId);
        DataBaseTask<Void> dataBaseTask = new DataBaseTask<>(context, new Contact(receiverLogin), incomingMessage, DataBaseTask.Task.InsertMessage);
        dataBaseTask.execute();
        messageChatAdapter.addItem(incomingMessage);
        messageField.setText("");
    }
}