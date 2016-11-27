package com.simoncherry.faketantan.realm;

import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Simon on 2016/11/27.
 */

public class MatchUserManager {

    private final static String TAG = MatchUserManager.class.getSimpleName();

    private volatile static MatchUserManager matchUserManager;
    private static Realm realm;

    private MatchUserManager() {
    }

    public static MatchUserManager getInstance() {
        if (matchUserManager == null) {
            synchronized (MatchUserManager.class) {
                if (matchUserManager == null) {
                    matchUserManager = new MatchUserManager();
                }
            }
        }
        return matchUserManager;
    }

    public static void initRealm(Realm r) {
        realm = r;
    }

    public void createMatchUser(MatchUser user) {
        try {
            realm.beginTransaction();
            Number maxId = realm.where(MatchUser.class).max("id");
            AtomicLong primaryKeyValue = new AtomicLong(maxId == null ? 0 : maxId.longValue());
            user.setId(primaryKeyValue.incrementAndGet());
            realm.copyToRealm(user);
            realm.commitTransaction();
        } catch (Exception e) {
            Logger.t(TAG).e(e.toString());
            realm.cancelTransaction();
        }
    }

    public void updateMatchUser(MatchUser user) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(user);
            realm.commitTransaction();
        } catch (Exception e) {
            Logger.t(TAG).e(e.toString());
            realm.cancelTransaction();
        }
    }

    public MatchUser retrieveMatchUserByIdentify(String identify) {
        try {
            RealmResults<MatchUser> results = realm.where(MatchUser.class)
                    .equalTo("identify", identify)
                    .findAll();
            if (results.size() > 0) {
                return results.first();
            } else {
                return null;
            }
        } catch (Exception e) {
            Logger.t(TAG).e(e.toString());
            return null;
        }
    }

    public List<MatchUser> retrieveMatchUsers() {
        try {
            RealmResults<MatchUser> results = realm.where(MatchUser.class)
                    .findAll();
            if (results.size() > 0) {
                return results;
            } else {
                return null;
            }
        } catch (Exception e) {
            Logger.t(TAG).e(e.toString());
            return null;
        }
    }
}
