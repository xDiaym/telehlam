package org.juicecode.telehlam.core.contacts;

import android.content.Context;
import android.os.AsyncTask;

import org.juicecode.telehlam.database.AppDataBase;
import org.juicecode.telehlam.database.DBClient;

public class InsertContact extends AsyncTask<Void, Void, Void> {
    Contact contact;
    Context context;

    public InsertContact(Context context, Contact contact) {
        this.contact = contact;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AppDataBase appDataBase = DBClient.getInstance(context).getAppDataBase();
        ContactDao contactDao = appDataBase.contactDao();
        if (contactDao.getNumberOfContactsByPhone(contact.getPhone()) > 0) {

        } else {
            //code for adding message
            contactDao.insert(contact);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //code of adding messages in RecyclerView
    }
}
