package com.udacity.firebase.shoppinglistplusplus.ui.activeListDetails;

import android.app.Dialog;
import android.os.Bundle;

import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.udacity.firebase.shoppinglistplusplus.R;
import com.udacity.firebase.shoppinglistplusplus.model.ShoppingList;
import com.udacity.firebase.shoppinglistplusplus.utils.Constants;

import java.util.HashMap;

/**
 * Lets user edit the list name for all copies of the current list
 */
public class EditListNameDialogFragment extends EditListDialogFragment {
    private static final String LOG_TAG = ActiveListDetailsActivity.class.getSimpleName();
    String mListName;
    /**
     * Public static constructor that creates fragment and passes a bundle with data into it when adapter is created
     */
    public static EditListNameDialogFragment newInstance(ShoppingList shoppingList) {
        EditListNameDialogFragment editListNameDialogFragment = new EditListNameDialogFragment();
        Bundle bundle = EditListDialogFragment.newInstanceHelper(shoppingList, R.layout.dialog_edit_list);

        // TODO add any values you need here from the shopping list to make this change.
        // Once you put a value in the bundle, it available to you in onCreate
        bundle.putString(Constants.KEY_LIST_NAME, shoppingList.getListName());

        editListNameDialogFragment.setArguments(bundle);
        return editListNameDialogFragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO Extract any arguments you put in the bundle when the newInstance method
        // created the dialog. You can store these in an instance variable so that they
        // are available to you.
        mListName = getArguments().getString(Constants.KEY_LIST_NAME);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /** {@link EditListDialogFragment#createDialogHelper(int)} is a
         * superclass method that creates the dialog
         **/
        Dialog dialog = super.createDialogHelper(R.string.positive_button_edit_item);
        // TODO You can use the helper method in the superclass I made (EditListDialogFragment)
        // called helpSetDefaultValueEditText. This will allow you to set what text the
        // user sees when the dialog opens.

        /**
         * {@link EditListDialogFragment#helpSetDefaultValueEditText(String)} is a superclass
         * method that sets the default text of the TextView
         */
        helpSetDefaultValueEditText(mListName);
        return dialog;


    }

    /**
     * Changes the list name in all copies of the current list
     */
    protected void doListEdit() {
        // TODO Do the actual edit operation here.
        // Remember, you need to update the timestampLastChanged for
        // the shopping list.
        final String inputListName = mEditTextForList.getText().toString();

        /**
         * Set input text to be the current list name if it is not empty
         */
        if (!inputListName.equals("")) {

            if (mListName != null) {

                /**
                 * If editText input is not equal to the previous name
                 */
                if (!inputListName.equals(mListName)) {
                    Firebase shoppingListRef = new Firebase(Constants.FIREBASE_URL_ACTIVE_LIST);

                    /* Make a Hashmap for the specific properties you are changing */
                    HashMap<String, Object> updatedProperties = new HashMap<String, Object>();
                    updatedProperties.put(Constants.FIREBASE_PROPERTY_LIST_NAME, inputListName);

                    /* Add the timestamp for last changed to the updatedProperties Hashmap */
                    HashMap<String, Object> changedTimestampMap = new HashMap<>();
                    changedTimestampMap.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

                    /* Add the updated timestamp */
                    updatedProperties.put(Constants.FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED, changedTimestampMap);

                    /* Do the update */
                    shoppingListRef.updateChildren(updatedProperties);
                }
            }
        }

    }
}


