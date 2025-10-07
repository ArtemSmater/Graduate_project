package com.example.contactholderapp.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private static PersonDatabase database;
    private final LiveData<List<Person>> persons;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        database = PersonDatabase.getInstance(getApplication());
        persons = database.personDao().getAllItems();
    }

    public LiveData<List<Person>> getAllPersons() {
        return persons;
    }

    public void insertPerson(Person person) {
        new InsertTask().execute(person);
    }

    public void deletePerson(Person person) {
        new DeleteItem().execute(person);
    }

    public void updatePerson(Person person) {
        new UpdateItem().execute(person);
    }

    public void deleteAllPersons() {
        new DeleteEverything().execute();
    }

    private static class DeleteEverything extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            database.personDao().deleteAll();
            return null;
        }
    }

    private static class UpdateItem extends AsyncTask<Person, Void, Void> {

        @Override
        protected Void doInBackground(Person... persons) {
            if (persons != null && persons.length > 0) {
                database.personDao().updateItem(persons[0].getName(), persons[0].getPhone(), persons[0].getEmail(), persons[0].getNote(), persons[0].getImgRes(),
                        persons[0].getDate(), persons[0].get_id());
                database.close();
            }
            return null;
        }
    }

    private static class DeleteItem extends AsyncTask<Person, Void, Void> {

        @Override
        protected Void doInBackground(Person... persons) {
            if (persons != null && persons.length > 0) {
                database.personDao().deleteItem(persons[0]);
                database.close();
            }
            return null;
        }
    }

    private static class InsertTask extends AsyncTask<Person, Void, Void> {

        @Override
        protected Void doInBackground(Person... persons) {
            if (persons != null && persons.length > 0) {
                database.personDao().insertItem(persons[0]);
            }
            return null;
        }
    }
}
