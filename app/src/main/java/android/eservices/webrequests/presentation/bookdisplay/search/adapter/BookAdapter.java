package android.eservices.webrequests.presentation.bookdisplay.search.adapter;

import android.eservices.webrequests.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {


    public static class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView authorsTextView;
        private ImageView iconImageView;
        private View v;
        private BookViewItem bookViewItem;
        private BookActionInterface bookActionInterface;
        private Switch favoriteSwitch;

        public BookViewHolder(View v, final BookActionInterface bookActionInterface) {
            super(v);
            this.v = v;
            titleTextView = v.findViewById(R.id.book_title_textview);
            authorsTextView = v.findViewById(R.id.book_authors_textview);
            iconImageView = v.findViewById(R.id.book_icon_imageview);
            favoriteSwitch = v.findViewById(R.id.favorite_switch);
            this.bookActionInterface = bookActionInterface;
            setupListeners();
        }

        private void setupListeners() {
            favoriteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    bookActionInterface.onFavoriteToggle(bookViewItem.getBookId(), b);
                }
            });
        }

        void bind(BookViewItem bookViewItem) {
            this.bookViewItem = bookViewItem;
            titleTextView.setText(bookViewItem.getBookTitle());
            authorsTextView.setText(bookViewItem.getBookAuthors());
            favoriteSwitch.setChecked(bookViewItem.isFavorite());
            Glide.with(v)
                    .load(bookViewItem.getIconUrl())
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(iconImageView);

        }

    }

    private List<BookViewItem> bookViewItemList;
    private BookActionInterface bookActionInterface;

    // Provide a suitable constructor (depends on the kind of dataset)
    public BookAdapter(BookActionInterface bookActionInterface) {
        bookViewItemList = new ArrayList<>();
        this.bookActionInterface = bookActionInterface;
    }

    public void bindViewModels(List<BookViewItem> bookViewItemList) {
        this.bookViewItemList.clear();
        this.bookViewItemList.addAll(bookViewItemList);
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        BookViewHolder bookViewHolder = new BookViewHolder(v, bookActionInterface);
        return bookViewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.bind(bookViewItemList.get(position));
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return bookViewItemList.size();
    }


}