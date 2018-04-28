package com.example.android.bookstoreapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookstoreapp.data.BookContract.BookEntry;

public class BookCursorAdapter extends CursorAdapter {

    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView nameTextView = view.findViewById(R.id.name);
        TextView authorTextView = view.findViewById(R.id.author);
        TextView priceTextView = view.findViewById(R.id.price);
        final TextView quantityTextView = view.findViewById(R.id.quantity);
        Button btnSell = view.findViewById(R.id.btn_sell);

        final String id = cursor.getString(cursor.getColumnIndex(BookEntry._ID));
        String bookName = cursor.getString(cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME));
        String bookAuthor = cursor.getString(cursor.getColumnIndex(BookEntry.COLUMN_BOOK_AUTHOR));
        String bookPrice = cursor.getString(cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE));
        final String bookQuantity = cursor.getString(cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QTY));

        if (TextUtils.isEmpty(bookAuthor)) bookAuthor = context.getString(R.string.unknown_author);

        nameTextView.setText(bookName);
        authorTextView.setText(bookAuthor);
        priceTextView.setText(bookPrice);
        quantityTextView.setText(bookQuantity);

        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(bookQuantity);
                if (quantity > 0) {
                    quantity--;
                    Toast.makeText(context, R.string.book_sold, Toast.LENGTH_SHORT).show();
                }
                ContentValues values = new ContentValues();
                values.put(BookEntry.COLUMN_BOOK_QTY, String.valueOf(quantity));
                String currentUriString = BookEntry.CONTENT_URI + "/" + id;
                context.getContentResolver().update(Uri.parse(currentUriString), values, null, null);
                quantityTextView.setText(String.valueOf(quantity));
            }
        });
    }
}