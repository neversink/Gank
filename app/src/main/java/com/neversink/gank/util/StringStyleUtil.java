package com.neversink.gank.util;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.text.style.TextAppearanceSpan;

/**
 * Created by never on 16/2/1.
 */
public class StringStyleUtil {

    public static SpannableString formatAppearance(Context context, String s, int style) {
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(new TextAppearanceSpan(context, style), 0, s.length(), 0);
        return spannableString;
    }

    public static SpannableString formatBullet(String s) {
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(new BulletSpan(12, 0xff303F9F), 0, s.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }
}
