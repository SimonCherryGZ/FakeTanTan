package com.simoncherry.faketantan.realm;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Simon on 2016/11/26.
 */

public class ChatMsgManager {

    private final static String TAG = ChatMsgManager.class.getSimpleName();

    private static Realm realm;

    private volatile static ChatMsgManager chatMsgManager;

    private ChatMsgManager(){
    }

    public static ChatMsgManager getInstance() {
        if (chatMsgManager == null) {
            synchronized (ChatMsgManager.class) {
                if (chatMsgManager == null) {
                    chatMsgManager = new ChatMsgManager();
                }
            }
        }
//        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
//                .name(dbName)
//                .deleteRealmIfMigrationNeeded()
//                .build();
//        realm = Realm.getInstance(realmConfig);
        return chatMsgManager;
    }

    public static void initRealm(Realm r) {
        realm = r;
    }

    public void createChatMsg(ChatMessage message) {
        try {
            realm.beginTransaction();
            Number maxId = realm.where(ChatMessage.class).max("id");
            AtomicLong primaryKeyValue = new AtomicLong(maxId == null ? 0 : maxId.longValue());
            message.setId(primaryKeyValue.incrementAndGet());
            realm.copyToRealm(message);
            realm.commitTransaction();
        } catch (Exception e) {
            Logger.t(TAG).e(e.toString());
            realm.cancelTransaction();
        } finally {
            //realm.close();
        }
    }

    public void updateChatMsg(ChatMessage message) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(message);
            realm.commitTransaction();
        } catch (Exception e) {
            Logger.t(TAG).e(e.toString());
            realm.cancelTransaction();
        } finally {
            //realm.close();
        }
    }

    public ChatMessage retrieveChatMsgById(long id) {
        try {
            RealmResults<ChatMessage> results = realm.where(ChatMessage.class)
                    .equalTo("id", id)
                    .findAll();
            if (results.size() > 0) {
                //realm.close();
                return results.first();
            } else {
                //realm.close();
                return null;
            }
        } catch (Exception e) {
            Logger.t(TAG).e(e.toString());
            //realm.close();
            return null;
        }
    }

    public void deleteChatMsgById(long id) {
        try {
            RealmResults results = realm.where(ChatMessage.class)
                    .equalTo("id", id)
                    .findAll();
            if (results.size() > 0) {
                results.deleteAllFromRealm();
            }
            //realm.close();
        } catch (Exception e) {
            Logger.t(TAG).e(e.toString());
            //realm.close();
        }
    }

    public List<ChatMessage> retrieveChatMsgByPage(String conversation, int pageIndex) {
        try {
            int pageSize = 8;
            int startRow = pageIndex > 0 ? (pageIndex - 1) * pageSize : 0;
            int endRow = startRow + pageSize * (pageIndex > 0 ? 1 : 0);

            RealmResults<ChatMessage> results = realm.where(ChatMessage.class)
                    .equalTo("conversation", conversation)
                    .findAllSorted("id", Sort.DESCENDING);
            if (results.size() > 0) {
                results.sort("textTime", Sort.DESCENDING);
                List<ChatMessage> reverseResult = new ArrayList<>();
                if (startRow < results.size()) {
                    if (endRow >= results.size()) {
                        endRow = results.size();
                    }
                    for (int i = startRow; i < endRow; i++) {
                        if (pageIndex == 1) {
                            reverseResult.add(0, results.get(i));
                        } else {
                            reverseResult.add(results.get(i));
                        }
                    }

                }
                //realm.close();
                return reverseResult;
            }
            //realm.close();
            return null;
        } catch (Exception e) {
            Logger.t(TAG).e(e.toString());
            //realm.close();
            return null;
        }
    }
}
