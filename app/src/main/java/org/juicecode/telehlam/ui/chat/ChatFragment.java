package org.juicecode.telehlam.ui.chat;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;
import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.R;
import org.juicecode.telehlam.core.contacts.User;
import org.juicecode.telehlam.database.DataBaseTask;
import org.juicecode.telehlam.database.messages.Message;
import org.juicecode.telehlam.socketio.AppSocket;
import org.juicecode.telehlam.socketio.MessageSender;
import org.juicecode.telehlam.socketio.OnNewMessageListener;
import org.juicecode.telehlam.socketio.NewMessageAdapter;
import org.juicecode.telehlam.utils.Constant;
import org.juicecode.telehlam.utils.KeyboardManager;
import org.juicecode.telehlam.utils.SharedPreferencesRepository;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    RecyclerView chat;
    TextView nameOfContact;
    EditText messageField;
    ImageButton sendButton;
    ImageButton goBack;
    User user;
    long userId;
    long receiverId;
    MessageChatAdapter messageChatAdapter;
    List<Message> messageList;
    AppSocket socket;
    Context context;
    String receiverLogin;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        context = getContext();

        socket = AppSocket
                .getInstance(Constant.baseUrl);
        socket.addListener("message",
                        new NewMessageAdapter((MainActivity) getActivity(),
                                new ChatNewMessageListener()));

        //all variables get their values
        final Context context = getContext();
        chat = view.findViewById(R.id.chat);
        LinearLayoutManager linearLayout = new LinearLayoutManager(context);
        chat.setLayoutManager(linearLayout);
        messageChatAdapter = new MessageChatAdapter();
        Bundle arguments = getArguments();
        user = (User) arguments.getSerializable("user");
        receiverId = user.getId();
        userId = new SharedPreferencesRepository(context).getId();
        chat.setAdapter(messageChatAdapter);
        chat.setHasFixedSize(false);
        chat.setNestedScrollingEnabled(false);
        messageField = view.findViewById(R.id.message_field);
        messageList = new ArrayList<>();
        sendButton = view.findViewById(R.id.send_message_button);
        nameOfContact = view.findViewById(R.id.chat_name);
        nameOfContact.setText(receiverLogin);
        goBack = view.findViewById(R.id.go_back_button);
        nameOfContact.setText(user.getLogin());
        //getting all messages for chat
        DataBaseTask<List<Message>> getMessages = new DataBaseTask<>(getContext(), getViewLifecycleOwner(), messageChatAdapter, chat, receiverId, DataBaseTask.Task.GetAllMessages);
        getMessages.execute();


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageField.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    //TODO(all): delete test code
                    Message message = new Message(Message.MESSAGE_OUTGOING,messageText,userId, receiverId);
                    DataBaseTask<Void> dataBaseTask = new DataBaseTask<>(context, user, message, DataBaseTask.Task.InsertMessage);
                    dataBaseTask.execute();

                    messageField.setText("");
                    chat.scrollToPosition(messageChatAdapter.getItemCount() - 1);

                    new MessageSender(socket).sendMessage(message);
                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardManager.hideKeyboard(getActivity());
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    class ChatNewMessageListener implements OnNewMessageListener {

        @Override
        public void onNewMessage(Message message) {
            Log.e("Tag", message.getText());
            DataBaseTask<Void> dataBaseTask = new DataBaseTask<>(context, user, message, DataBaseTask.Task.InsertMessage);
            dataBaseTask.execute();

            messageChatAdapter.addItem(message);
        }
    }
}