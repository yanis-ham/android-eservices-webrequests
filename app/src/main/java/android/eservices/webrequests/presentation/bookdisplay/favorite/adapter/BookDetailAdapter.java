package android.eservices.webrequests.presentation.bookdisplay.favorite.adapter;

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

public class BookDetailAdapter extends RecyclerView.Adapter<BookDetailAdapter.BookDetailViewHolder> {


    public static class BookDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView authorsTextView;
        private TextView languageTextView;
        private TextView descriptionTextView;
        private TextView publishedTextView;
        private ImageView iconImageView;
        private View v;
        private BookDetailViewItem bookDetailViewItem;
        private BookDetailActionInterface bookDetailActionInterface;
        private Switch favoriteSwitch;

        public BookDetailViewHolder(View v, final BookDetailActionInterface bookDetailActionInterface) {
            super(v);
            this.v = v;
            titleTextView = v.findViewById(R.id.book_title_textview);
            languageTextView = v.findViewById(R.id.book_language_textview);
            descriptionTextView = v.findViewById(R.id.book_description_textview);
            publishedTextView = v.findViewById(R.id.book_published_textview);
            authorsTextView = v.findViewById(R.id.book_authors_textview);
            iconImageView = v.findViewById(R.id.book_icon_imageview);
            favoriteSwitch = v.findViewById(R.id.favorite_switch);
            setupListeners();
            this.bookDetailActionInterface = bookDetailActionInterface;
        }

        private void setupListeners() {
            favoriteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (!b) {
                        bookDetailActionInterface.onRemoveFavorite(bookDetailViewItem.getBookId());
                    }
                }
            });
        }

        void bind(BookDetailViewItem bookDetailViewItem) {
            this.bookDetailViewItem = bookDetailViewItem;
            titleTextView.setText(bookDetailViewItem.getBookTitle());
            authorsTextView.setText(bookDetailViewItem.getBookAuthors());
            languageTextView.setText(bookDetailViewItem.getBookLanguage());
            descriptionTextView.setText(bookDetailViewItem.getBookDescription());
            favoriteSwitch.setChecked(true);
            if (bookDetailViewItem.getBookDescription() == null) {
                descriptionTextView.setVisibility(View.GONE);
            } else {
                descriptionTextView.setVisibility(View.VISIBLE);
            }
            publishedTextView.setText(bookDetailViewItem.getBookPublishedDate());
            Glide.with(v)
                    .load(bookDetailViewItem.getIconUrl())
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(iconImageView);

        }

    }

    private List<BookDetailViewItem> bookDetailViewItemList;
    private BookDetailActionInterface bookDetailActionInterface;

    // Provide a suitable constructor (depends on the kind of dataset)
    public BookDetailAdapter(BookDetailActionInterface bookDetailActionInterface) {
        bookDetailViewItemList = new ArrayList<>();
        this.bookDetailActionInterface = bookDetailActionInterface;
    }

    public void bindViewModels(List<BookDetailViewItem> bookItemViewModelList) {
        this.bookDetailViewItemList.clear();
        this.bookDetailViewItemList.addAll(bookItemViewModelList);
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BookDetailViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detailed_book, parent, false);
        BookDetailViewHolder bookDetailViewHolder = new BookDetailViewHolder(v, bookDetailActionInterface);
        return bookDetailViewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BookDetailViewHolder holder, int position) {
        holder.bind(bookDetailViewItemList.get(position));
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return bookDetailViewItemList.size();
    }


}