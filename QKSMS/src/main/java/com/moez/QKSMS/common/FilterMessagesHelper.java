package com.moez.QKSMS.common;

/**
 * Created by VladislavEmets on 6/6/17.
 */

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A set of helper methods to group the logic related to filtering messages by content
 */
public class FilterMessagesHelper {

    private static String kFilters = "UserFilters";
    private static String kFilteredMessages = "UserFilteredMessages";


    public static void addFilter(SharedPreferences prefs, String pattern) {
        Set<String> filters = prefs.getStringSet(FilterMessagesHelper.kFilters, new HashSet<String>());
        filters.add(pattern);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.putStringSet(FilterMessagesHelper.kFilters, filters).commit();
    }


    public static void removeFilter(SharedPreferences prefs, String pattern) {
        Set<String> filters = prefs.getStringSet(FilterMessagesHelper.kFilters, new HashSet<String>());
        filters.remove(String.valueOf(pattern));
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.putStringSet(FilterMessagesHelper.kFilters, filters).commit();
    }


    public static void clearFilters(SharedPreferences prefs) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.remove(FilterMessagesHelper.kFilters).commit();
    }


    public static ArrayList<String>allFilters(SharedPreferences prefs) {
        Set<String> filters = prefs.getStringSet(FilterMessagesHelper.kFilters, new HashSet<String>());
        return new ArrayList<String>(filters);
    }

    public static boolean matchToAnyFilter(SharedPreferences prefs, String message) {
        Set<String> filters = prefs.getStringSet(FilterMessagesHelper.kFilters, new HashSet<String>());
        Iterator<String> iterator = filters.iterator();
        while (iterator.hasNext()) {
            String pattern = iterator.next();
            if (message.contains(pattern)) {
                return  true;
            }
        }
        return false;
    }



    public static void addFilteredMessage(SharedPreferences prefs, String address, String message) {
        Set<String> filters = prefs.getStringSet(FilterMessagesHelper.kFilteredMessages, new HashSet<String>());
        filters.add(address + "\n" + message);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.putStringSet(FilterMessagesHelper.kFilteredMessages, filters).commit();
    }

    public static ArrayList<String>allFilteredMessages(SharedPreferences prefs) {
        Set<String> filters = prefs.getStringSet(FilterMessagesHelper.kFilteredMessages, new HashSet<String>());
        return new ArrayList<String>(filters);
    }

    public static void clearFilteredMessages(SharedPreferences prefs) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.remove(FilterMessagesHelper.kFilteredMessages).commit();
    }

}
