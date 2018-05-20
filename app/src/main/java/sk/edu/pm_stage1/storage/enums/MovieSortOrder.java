package sk.edu.pm_stage1.storage.enums;

import android.text.TextUtils;

public enum MovieSortOrder {
  MOST_POPULAR(1, "most popular", "popular"),
  TOP_RATED(2, "top rated", "top_rated");

  private int id;
  private String name;
  private String paramName;

  MovieSortOrder(final int id, final String name, final String paramName) {
    this.id = id;
    this.name = name;
    this.paramName = paramName;
  }

  public static MovieSortOrder getValueFromString(final String sortOrder) {
    if (TextUtils.isEmpty(sortOrder)) {
      return MOST_POPULAR;
    }
    if (sortOrder.equalsIgnoreCase(MovieSortOrder.MOST_POPULAR.getParamName())) {
      return MOST_POPULAR;
    } else { // if (sortOrder.equalsIgnoreCase(MovieSortOrder.TOP_RATED.getParamName())) {
      return TOP_RATED;
    }
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getParamName() {
    return paramName;
  }
}
