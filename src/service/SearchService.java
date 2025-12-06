package service;

import java.util.ArrayList;

import model.Task;

public class SearchService {

    /**
     * 検索メソッド:
     *  「完了」なら isDone == true,
     *  「未完了」なら isDone == false,
     *  それ以外はタイトル部分一致。
     *
     * @param tasks   検索対象のタスクリスト
     * @param keyword ユーザーが指定したキーワード
     */
    public void searchTasks(ArrayList<Task> tasks, String keyword) {
        System.out.println("[検索: '" + keyword + "']");
        boolean found = false;

        for (Task t : tasks) {
            if (isMatch(t, keyword)) {
                System.out.println("  " + t);
                found = true;
            }
        }

        if (!found) {
            System.out.println("  該当タスクなし");
        }
    }

    /**
     * 「完了」「未完了」「タイトル部分一致」を判定するサブメソッド
     * @param t       判定対象のタスク
     * @param keyword キーワード
     * @return        合致するかどうか(true/false)
     */
    private boolean isMatch(Task t, String keyword) {
        if ("完了".equals(keyword)) {
            return t.isDone();
        } else if ("未完了".equals(keyword)) {
            return !t.isDone();
        } else {
            return t.getTitle().contains(keyword);
        }
    }

}
