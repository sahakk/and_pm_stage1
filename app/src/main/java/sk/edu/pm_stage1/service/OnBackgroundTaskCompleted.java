package sk.edu.pm_stage1.service;

import sk.edu.pm_stage1.storage.entity.MovieEntity;

public interface OnBackgroundTaskCompleted {
  void onFetchMoviesTaskCompleted(MovieEntity[] movies);
}
