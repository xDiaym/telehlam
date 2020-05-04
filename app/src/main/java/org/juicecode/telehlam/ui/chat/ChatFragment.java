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
import org.juicecode.telehlam.socketio.SocketIOMethods;
import org.juicecode.telehlam.socketio.onMessageCallback;
import org.juicecode.telehlam.utils.DrawerLocker;
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
    String receiverNick;
    String userNick;
    MessageChatAdapter messageChatAdapter;
    List<Message> messageList;
    Socket socket;
    Context context;
    SharedPreferences sharedPreferences;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        context = getContext();
        socket = AppSocket.getSocket();
        socket.on("message",new SocketIOMethods((MainActivity)getActivity(),this).getOnMessage());
        final DrawerLocker drawerLocker = (DrawerLocker) view.getContext();
        drawerLocker.setDrawerLock(true);
        //all variables get their values
        final Context context = getContext();
        chat = view.findViewById(R.id.chat);
        LinearLayoutManager linearLayout = new LinearLayoutManager(context);
        chat.setLayoutManager(linearLayout);
        messageChatAdapter = new MessageChatAdapter();
        Bundle arguments = getArguments();
        String[] values = arguments.getStringArray("information");
        receiverNick = values[0];
        //TODO sharedRepository use
        userNick = new SharedPreferencesRepository(context).getLogin();
        chat.setAdapter(messageChatAdapter);
        chat.setHasFixedSize(false);
        chat.setNestedScrollingEnabled(false);
        messageField = view.findViewById(R.id.message_field);
        messageList = new ArrayList<>();
        sendbutton = view.findViewById(R.id.send_message_button);
        nameOfContact = view.findViewById(R.id.chat_name);
        nameOfContact.setText(receiverNick);
        goBack = view.findViewById(R.id.go_back_button);
        //getting all messages for chat
        DataBaseTask<List<Message>> getMessages = new DataBaseTask<>(getContext(), getViewLifecycleOwner(), messageChatAdapter, chat,receiverNick, DataBaseTask.Task.GetAllMessages);
        getMessages.execute();


        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageField.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    Message message;
                    /*
                    // TODO(all): delete test code
                    message = new Message(Message.MESSAGE_OUTGOING,messageText,userNick, receiverNick);
                    DataBaseTask<Void> dataBaseTask = new DataBaseTask<>(context, new Contact(receiverNick), message, DataBaseTask.Task.InsertMessage);
                    dataBaseTask.execute();*/
                    if (new Random().nextBoolean()) {
                        message = new Message(Message.MESSAGE_OUTGOING, messageText, userNick, receiverNick);
                    } else {
                        message = new Message(Message.MESSAGE_INCOMING, messageText, receiverNick, userNick);
                    }

                }
            }
        });

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

    @Override
    public void savingMessage(String message) {
        Message incomingMessage;
        incomingMessage = new Message(Message.MESSAGE_INCOMING,message,userNick, receiverNick);
        DataBaseTask<Void> dataBaseTask = new DataBaseTask<>(context, new Contact(receiverNick), incomingMessage, DataBaseTask.Task.InsertMessage);
        dataBaseTask.execute();
        messageChatAdapter.addItem(incomingMessage);
        messageField.setText("");
    }
}