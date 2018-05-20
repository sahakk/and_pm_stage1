package sk.edu.pm_stage1.service;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import sk.edu.pm_stage1.storage.entity.MovieEntity;

public class FetchMoviesAsyncTask extends AsyncTask<String, Void, MovieEntity[]> {
  private final String TAG_NAME = FetchMoviesAsyncTask.class.getSimpleName();

  private static final String EMPTY_STRING = "";
  private static final String NEW_LINE = "\n";
  private static final String TAG_RESULTS = "results";
  private static final String TAG_ORIGINAL_TITLE = "original_title";
  private static final String TAG_POSTER_PATH = "poster_path";
  private static final String TAG_OVERVIEW = "overview";
  private static final String TAG_VOTE_AVERAGE = "vote_average";
  private static final String TAG_RELEASE_DATE = "release_date";

  private static final String HTTP_METHOD_GET = "GET";
  private static final String THE_MOVIE_DB_URL = "https://api.themoviedb.org/3/movie/";
  private static final String PARAM_API_KEY = "api_key";
  private static final String PARAM_LANGUAGE = "language";
  private static final String PARAM_LANGUAGE_VALUE = "en-US";
  private static final String PARAM_PAGE = "page";
  private static final String PAGE_VALUE = "1";

  private String apiKey;
  private final OnBackgroundTaskCompleted listener;

  public FetchMoviesAsyncTask(OnBackgroundTaskCompleted listener, final String apiKey) {
    super();
    this.listener = listener;
    this.apiKey = apiKey;
  }

  @Override
  protected MovieEntity[] doInBackground(String... params) {
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String jsonString = null;

    try {
      URL url = getApiUrl(params);
      urlConnection = (HttpURLConnection)url.openConnection();
      urlConnection.setRequestMethod(HTTP_METHOD_GET);
      urlConnection.connect();
      InputStream inputStream = urlConnection.getInputStream();
      StringBuilder builder = new StringBuilder();
      if (inputStream == null) {
        return null;
      }
      reader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      while ((line = reader.readLine()) != null) {
        builder.
            append(line).
            append(NEW_LINE);
      }
      if (builder.length() == 0) {
        return null;
      }
      jsonString = builder.toString();
    } catch (IOException e) {
      Log.e(TAG_NAME, e.getMessage(), e);
      return null;
    } finally {
      if (urlConnection != null) {
        urlConnection.disconnect();
      }
      if (reader != null) {
        try {
          reader.close();
        } catch (final IOException e) {
          Log.e(TAG_NAME, e.getMessage(), e);
        }
      }
    }
    try {
      return getMoviesDataFromJson(jsonString);
    } catch (JSONException e) {
      Log.e(TAG_NAME, e.getMessage(), e);
      e.printStackTrace();
    }
    return null;
  }

  private MovieEntity[] getMoviesDataFromJson(final String jsonString) throws JSONException {
    final JSONObject moviesJson = new JSONObject(jsonString);
    final JSONArray resultsArray = moviesJson.getJSONArray(TAG_RESULTS);
    MovieEntity[] movies = new MovieEntity[resultsArray.length()];
    for (int i = 0; i < resultsArray.length(); i++) {
      movies[i] = new MovieEntity();
      JSONObject entityJson = resultsArray.getJSONObject(i);
      movies[i].setTitle(entityJson.optString(TAG_ORIGINAL_TITLE, EMPTY_STRING));
      movies[i].setImagePath(entityJson.optString(TAG_POSTER_PATH, EMPTY_STRING));
      final String releaseDateStr = entityJson.optString(TAG_RELEASE_DATE, EMPTY_STRING);
      movies[i].setReleaseDate(Utils.convertStringToDate(releaseDateStr, Utils.MILITARY_SHORT_FORMAT));
      movies[i].setDescription(entityJson.optString(TAG_OVERVIEW, EMPTY_STRING));
      movies[i].setAverageVote(entityJson.optDouble(TAG_VOTE_AVERAGE, 0));
    }
    return movies;
  }

  private URL getApiUrl(String[] parameters) throws MalformedURLException {
    // TODO Sahak. For stage 2, pass page as parameter for lazy loading.
    Uri builtUri = Uri.
        parse(THE_MOVIE_DB_URL).
        buildUpon().
        appendEncodedPath(parameters[0]).
        appendQueryParameter(PARAM_API_KEY, apiKey).
        appendQueryParameter(PARAM_LANGUAGE, PARAM_LANGUAGE_VALUE).
        appendQueryParameter(PARAM_PAGE, PAGE_VALUE).
        build();
    return new URL(builtUri.toString());
  }

  @Override
  protected void onPostExecute(MovieEntity[] movies) {
    super.onPostExecute(movies);
    listener.onFetchMoviesTaskCompleted(movies);
  }
}
