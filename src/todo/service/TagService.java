package todo.service;

import java.util.ArrayList;
import java.util.List;

import todo.exception.DuplicateTagException;
import todo.model.Task;

public class TagService {

	/**
	 * 指定タスクにタグを追加。
	 * 重複するタグがあれば DuplicateTagException をスロー。
	 *
	 * @param task タグを追加する対象タスク
	 * @param newTag 追加したいタグ文字列
	 * @throws DuplicateTagException すでに存在しているタグの場合
	 */
	public void addTagToTask(Task task, String newTag)  {
		try {
			if (task == null) {
				System.out.println("【タグ登録失敗】taskがnullです。");
				return;
			}
			ArrayList<String> existingTags = task.getTags();
			TagUtils.checkDuplicateAndThrow(existingTags, newTag);
			existingTags.add(newTag);
			System.out.println("【タグ登録完了】'" + newTag + "' を タスクID=" + task.getId() + " に追加");
		} catch (DuplicateTagException e) {
			System.out.println("[ERROR] タグ登録時の例外: " + e.getMessage());
		}
	}

	/**
	 * 指定タスクからタグを削除。
	 * 存在しないタグの場合は何もしない方針。
	 *
	 * @param task タグを削除する対象タスク
	 * @param tag  削除したいタグ文字列
	 */
	public void removeTagFromTask(Task task, String tag) {
		if (task == null) {
			System.out.println("【タグ削除失敗】taskがnullです。");
			return;
		}
		ArrayList<String> existingTags = task.getTags();
		boolean removed = existingTags.remove(tag);
		if (removed) {
			System.out.println("【タグ削除完了】'" + tag + "' を タスクID=" + task.getId() + " から外しました");
		} else {
			System.out.println("【タグ削除失敗】'" + tag + "' は タスクID=" + task.getId() + " に存在しません。");
		}
	}

	/**
	 * 指定タグを含むタスクだけを表示する。
	 * @param tasks タスクリスト
	 * @param tag   検索するタグ
	 */
	public void listTasksByTag(List<Task> tasks, String tag) {
		System.out.println("\n--- タグ[" + tag + "]を含むタスク一覧 ---");
		boolean found = false;
		for (Task task : tasks) {
			if (task.getTags().contains(tag)) {
				System.out.println("  " + task);
				found = true;
			}
		}
		if (!found) {
			System.out.println(" 該当タスクなし");
		}
	}

	/**
	 * 必要に応じてタグ一覧を一括表示するなど、
	 * 他のタグ関連の処理もここに追加していける。
	 */
}
