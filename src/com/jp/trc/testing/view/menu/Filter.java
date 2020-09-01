package com.jp.trc.testing.view.menu;

import java.util.Comparator;

/**
 * Filter for paging, search and sorting.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 28.08.2020 22:36
 */
public class Filter {

    /**
     * Offset for paging.
     */
    private long offset;

    /**
     * Amount elements on page.
     */
    private int limit;

    /**
     * Search phrase.
     */
    private String searchPhrase;

    /**
     * Sort direction comparator.
     */
    private Comparator comparator;

    /**
     * Constructor for creating a filter.
     * @param offset Offset for paging.
     * @param limit Amount elements on page.
     * @param comparator Sort direction comparator.
     */
    public Filter(long offset, int limit, Comparator comparator) {
        this.offset = offset;
        this.limit = limit;
        this.comparator = comparator;
    }

    /**
     * Gets offset.
     *
     * @return value of offset long
     */
    public long getOffset() {
        return offset;
    }

    /**
     * Sets value offset.
     *
     * @param offset value of offset
     */
    public void setOffset(long offset) {
        this.offset = offset;
    }

    /**
     * Gets limit.
     *
     * @return value of limit int
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Sets value limit.
     *
     * @param limit value of limit
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * Gets searchPhrase.
     *
     * @return value of searchPhrase java.lang.String
     */
    public String getSearchPhrase() {
        return searchPhrase;
    }

    /**
     * Sets value searchPhrase.
     *
     * @param searchPhrase value of searchPhrase
     */
    public void setSearchPhrase(String searchPhrase) {
        this.searchPhrase = searchPhrase;
    }

    /**
     * Gets comparator.
     *
     * @return value of comparator java.util.Comparator
     */
    public Comparator getComparator() {
        return comparator;
    }

    /**
     * Sets value comparator.
     *
     * @param comparator value of comparator
     */
    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }
}
