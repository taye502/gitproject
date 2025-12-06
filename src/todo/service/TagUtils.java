package todo.service;

import java.util.ArrayList;

import todo.exception.DuplicateTagException;

public final class TagUtils {
	private TagUtils() {
		
	}
	
	public static void checkDuplicateAndThrow(ArrayList<String> existingTags, String newTag)
            throws DuplicateTagException {

        if (existingTags.contains(newTag)) {
            throw new DuplicateTagException("タグ '" + newTag + "' は既に存在します。");
        }
    }
}
