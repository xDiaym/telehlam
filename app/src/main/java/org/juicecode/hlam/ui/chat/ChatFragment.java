package org.juicecode.hlam.ui.chat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.hlam.R;
import org.juicecode.hlam.ui.home.ChatListAdapter;

public class ChatFragment extends Fragment {
    RecyclerView chat;
    TextView nameOfContact;
    EditText messageField;
    ImageButton sendbutton;
    ImageButton goBack;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        chat  = view.findViewById(R.id.chat);
        messageField = view.findViewById(R.id.message_field);
        Context context = getContext();
        LinearLayoutManager linearLayout = new LinearLayoutManager(context);
        chat.setLayoutManager(linearLayout);
        final MessageChatAdapter messageListAdapter = new MessageChatAdapter();

        chat.setAdapter(messageListAdapter);
        chat.setHasFixedSize(false);
        chat.setNestedScrollingEnabled(false);
        sendbutton = view.findViewById(R.id.send_message_button);
        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageValue = messageField.getText().toString();
                StringBuffer stringBuffer= new StringBuffer(messageValue);
                Character lastletter = stringBuffer.charAt(stringBuffer.length()-1);
                String last = Character.toString(lastletter);
                if(last.equals(".")){
                IncomingMessage incomingMessage = new IncomingMessage("");
                OutcomingMessage outcomingMessage = new OutcomingMessage(messageValue);
                messageListAdapter.setItem(incomingMessage,outcomingMessage);
                }else{
                    IncomingMessage incomingMessage = new IncomingMessage(messageValue);
                    OutcomingMessage outcomingMessage = new OutcomingMessage("");
                    messageListAdapter.setItem(incomingMessage,outcomingMessage);
                }
                messageField.setText("");
            }
        });
        Bundle arguments = getArguments();
        String nameOfContactValue = arguments.getString("chatName");
        nameOfContact = view.findViewById(R.id.nameOfContact);
        goBack = view.findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
                messageField.clearFocus();

            }
        });
        nameOfContact.setText(nameOfContactValue);
        return view;
    }


}
