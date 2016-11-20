package com.simoncherry.faketantan.utils;

/**
 * Created by Simon on 2016/11/19.
 */

public class TutorialUtil {

    public static String[] getTutorialArray(int index) {
        switch (index) {
            case 1:
                return TestUtil.guide_how_to_use;
                    case 11:
                        return TestUtil.guide_how_to_play;
                    case 12:
                        return TestUtil.guide_how_to_chat;
                    case 13:
                        return TestUtil.guide_why_need_match;
                    case 14:
                        return TestUtil.guide_why_no_match;
                            case 141:
                                return TestUtil.guide_why_no_any_match;
                            case 142:
                                return TestUtil.guide_why_match_few;
                    case 15:
                        return TestUtil.guide_match_chat_what;
                    case 16:
                        return TestUtil.guide_why_so_far;
            case 2:
                return TestUtil.guide_who_like_me;
            case 3:
                return TestUtil.guide_to_find_function;
                    case 31:
                        return TestUtil.guide_to_edit_profile;
                            case 311:
                                return TestUtil.guide_to_change_avatar;
                            case 312:
                                return TestUtil.guide_to_delete_photo;
                            case 313:
                                return TestUtil.guide_to_change_info;
                    case 32:
                        return TestUtil.guide_account_setting;
                            case 321:
                                return TestUtil.guide_allow_msg_notice;
                            case 322:
                                return TestUtil.guide_how_to_quit;
                            case 323:
                                return TestUtil.guide_to_del_account;
                            case 324:
                                return TestUtil.guide_to_change_password;
                            case 325:
                                return TestUtil.guide_to_clear_cache;
                            case 326:
                                return TestUtil.guide_to_change_language;
                    case 33:
                        return TestUtil.guide_to_search_friend;
                    case 34:
                        return TestUtil.guide_set_search_filter;
                    case 35:
                        return TestUtil.guide_del_chat_history;
                    case 36:
                        return TestUtil.guide_how_to_mask;
                            case 361:
                                return TestUtil.guide_to_mask_someone;
                            case 362:
                                return TestUtil.guide_to_cancel_mask;
                            case 363:
                                return TestUtil.guide_how_to_report;
            case 4:
                return TestUtil.guide_bye_by_mistake;
            case 5:
                return TestUtil.guide_have_more_question;
                    case 51:
                        return TestUtil.guide_why_match_disappear;
                    case 52:
                        return TestUtil.guide_no_one_appear;
                    case 53:
                        return TestUtil.guide_someone_like_me;
                    case 54:
                        return TestUtil.guide_cannot_send_msg;
                    case 55:
                        return TestUtil.guide_whether_auto_match;
                    case 56:
                        return TestUtil.guide_other_question;
            case 6:
                return TestUtil.guide_account_exception;
            default:
                return TestUtil.guide_common;
        }
    }

//    public static int getTutorialIndex(int index) {
//        switch (index) {
//            case
//        }
//    }
}
