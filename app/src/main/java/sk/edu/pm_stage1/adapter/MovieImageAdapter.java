package sk.edu.pm_stage1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import sk.edu.pm_stage1.R;
import sk.edu.pm_stage1.storage.entity.MovieEntity;
import sk.edu.pm_stage1.ui.DetailsActivity;

public class MovieImageAdapter extends RecyclerView.Adapter<MovieImageAdapter.ViewHolder> {
  private static final String TAG = MovieImageAdapter.class.getName();
  private final MovieEntity[] moviesArray;
  private final Context mContext;

  // for stage 2 and "infinite scrolling"/"lazy loading"
  // private OnBottomViewClickListener onBottomViewClickListener;

  public MovieImageAdapter(Context context, MovieEntity[] array) {
    moviesArray = array;
    mContext = context;
  }

  @NonNull
  @Override
  public MovieImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.
        from(parent.getContext()).
        inflate(R.layout.list_item, parent, false);
    MovieImageAdapter.ViewHolder holder = new MovieImageAdapter.ViewHolder(v);
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull MovieImageAdapter.ViewHolder holder, final int position) {
    try{
      final MovieEntity movie = moviesArray[position];
      holder.movieTitle.setText(movie.getTitle());
      final String imagePath = moviesArray[position].getImageFullPath();
      Picasso.with(mContext).
          load(imagePath).
          resize(
              mContext.getResources().getInteger(R.integer.W_185_WIDTH),
              mContext.getResources().getInteger(R.integer.W_185_HEIGHT)).
          error(R.drawable.not_found).
          placeholder(R.drawable.searching).
          into(holder.moviePoster);
      holder.movieContainer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Intent intent = new Intent(mContext, DetailsActivity.class);
          intent.putExtra(mContext.getResources().
              getString(R.string.key_single_movie_parcel), movie);
          mContext.startActivity(intent);
        }
      });
    } catch(IndexOutOfBoundsException e) {
      Log.e(TAG, e.getMessage());
    }
  }

  @Override
  public int getItemCount() {
    return moviesArray.length;
  }

  public MovieEntity getMovieAtPosition(final int position) {
    if (moviesArray == null || moviesArray.length == 0 ||
        position < 0 || position >= moviesArray.length) {
      return null;
    }
    return moviesArray[position];
  }

  protected static class ViewHolder extends RecyclerView.ViewHolder {
    private final TextView movieTitle;
    private final ImageView moviePoster;
    private final CardView movieContainer;

    public ViewHolder(View itemView) {
      super(itemView);
      movieTitle = itemView.findViewById(R.id.text_view_video_title);
      moviePoster = itemView.findViewById(R.id.image_view_poster);
      movieContainer = itemView.findViewById(R.id.video_poster_container);
    }
  }
}