package sk.edu.pm_stage1.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import sk.edu.pm_stage1.R;
import sk.edu.pm_stage1.service.Utils;
import sk.edu.pm_stage1.storage.entity.MovieEntity;

public class DetailsActivity extends AppCompatActivity {
  private static final String TAG = DetailsActivity.class.getName();

  private ImageView imagePoster;
  private TextView movieTitle;
  private TextView releaseDate;
  private TextView movieDescription;
  private TextView averageVote;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_details);
    initializeComponent(savedInstanceState);
  }

  private void initializeComponent(Bundle savedInstanceState) {
    imagePoster = (ImageView)findViewById(R.id.image_view_movie_poster);
    movieTitle = (TextView)findViewById(R.id.text_view_movie_title);
    releaseDate = (TextView)findViewById(R.id.text_view_release_date);
    averageVote = (TextView)findViewById(R.id.text_view_average_vote);
    movieDescription = (TextView)findViewById(R.id.text_view_movie_description);

    MovieEntity movie = getIntent().getParcelableExtra(
        getResources().getString(R.string.key_single_movie_parcel));
    if (movie == null) {
      return;
    }
    Picasso.with(this).
      load(movie.getImageFullPath()).
      resize(
          getResources().getInteger(R.integer.W_185_WIDTH),
          getResources().getInteger(R.integer.W_185_HEIGHT)).
      error(R.drawable.not_found).
      placeholder(R.drawable.searching).
      into(imagePoster);
    movieTitle.setText(movie.getTitle());
    releaseDate.setText(Utils.convertDateToString(movie.getReleaseDate(), Utils.SHORT_FORMAT));
    averageVote.setText(TextUtils.concat(String.valueOf(movie.getAverageVote()),
        getResources().getString(R.string.out_of_ten)));
    movieDescription.setText(movie.getDescription());
  }
}
