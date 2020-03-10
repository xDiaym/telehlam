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

import org.juicecode.hlam.R;

public class ChatFragment extends Fragment {
    // TODO(all): make local variable
    private RecyclerView chat;
    private TextView nameOfContact;
    private EditText messageField;
    private ImageButton sendButton;
    private ImageButton goBack;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        chat  = view.findViewById(R.id.chat);

        Context context = getContext();
        messageField = view.findViewById(R.id.message_field);

        LinearLayoutManager linearLayout = new LinearLayoutManager(context);
        chat.setLayoutManager(linearLayout);
        final MessageChatAdapter messageListAdapter = new MessageChatAdapter();

        chat.setAdapter(messageListAdapter);
        chat.setHasFixedSize(false);
        // chat.setNestedScrollingEnabled(false);

        sendButton = view.findViewById(R.id.send_message_button);
        // TODO(all): make inner class for this
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageValue = messageField.getText().toString();
                // TODO(all): StringBuilder -> StringBuilder
                StringBuffer stringBuffer= new StringBuffer(messageValue);
                Character lastLetter = stringBuffer.charAt(stringBuffer.length()-1);
                String last = Character.toString(lastLetter);
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
