package ru.coffeeplanter.androidlogin.platform;

/**
 * Created by Ilya Solovyov on 18.09.2017.
 * is3k@ya.ru
 */

public class ResourceSupplier {

    public static String getString(int stringId) {
        return App.getContext().getString(stringId);
    }

}
