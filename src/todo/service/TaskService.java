package todo.service;

import java.util.ArrayList;
import java.util.Collections;

import todo.exception.InvalidProgressException;
import todo.exception.InvalidTaskException;
import todo.interfaces.SortStrategy;
import todo.model.Task;

/**
 * タスクの登録・更新・削除・検索といったビジネスロジックを担当するサービスクラス。
 * タスクの実体はメモリ上のリスト(ArrayList)で管理する。
 */
public class TaskService {

    // 管理下の全タスクを保持するリスト
	private ArrayList<Task> tasks; 
    
	private SortStrategy currentStrategy;
    /**
     * コンストラクタ
     * タスク格納用のリストを初期化する。
     */
    public TaskService() {
    	  tasks = new ArrayList<>();

    }

    /**
     * 新しいタスクをリストに追加する。
     * @param t 追加するタスクオブジェクト
     * @throws InvalidTaskException タスクがnullの場合にスローされる
     */
    public void addTask(Task t) throws InvalidTaskException {
        // nullチェックを行い、不正な呼び出しを防ぐ
        if (t == null) {
            throw new InvalidTaskException("タスクがnullです。");
        }
    
        tasks.add(t);
        System.out.println("【登録】" + t);
    }

    /**
     * 現在登録されている全てのタスクをコンソールに表示する。
     * リストが空の場合は、その旨を表示して処理を終了する。
     */
    public void listAllTasks() {
        System.out.println("[全タスク一覧]");
        if (tasks.isEmpty()) {
            System.out.println(" 登録されたタスクはありません。");
            return;
        }
        // 拡張for文で全要素を走査して出力する
        for (Task t : tasks) {
            System.out.println("  " + t);
        }
    }

    /**
     * 指定されたIDのタスクのタイトルと内容を更新する。
     * @param id       更新対象のタスクID
     * @param newTitle 新しいタイトル
     * @param newDesc  新しい内容
     * @throws InvalidTaskException タイトルが不正な場合にスローされる
     */
    public void updateTask(int id, String newTitle, String newDesc) throws InvalidTaskException {
        for (Task task : tasks) {
            if (task.getId() == id) {
                // 対象が見つかった場合、値を更新して処理を終了する
                task.setTitle(newTitle);
                task.setDescription(newDesc);  
                System.out.println("【更新】" + task);
                return;
            }
        }
        // ループを抜けてもここに来る場合は、対象IDが見つからなかったことを意味する
        System.out.println("【更新失敗】タスクID=" + id + " は見つかりません。");
    }
    
    /**
     * 指定されたIDのタスクの進捗ステータスを変更する。
     * 不正なステータス変更の場合は例外をキャッチし、エラーメッセージを表示する。
     * @param id          対象のタスクID
     * @param newProgress 新しい進捗ステータス
     */
    public void changeProgress(int id, String newProgress) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                try {
                    task.setProgress(newProgress);
                    System.out.println("【進捗変更】" + task);
                } catch (InvalidProgressException e) {
                    // Taskクラス側で投げられた例外をここで補足し、ユーザーに通知する
                    System.out.println("[進捗変更エラー]" + e.getMessage());
                }
                return;
            }
        }
        System.out.println("【進捗変更失敗】ID=" + id + " のタスクが見つかりません。");
    }

    /**
     * 指定されたIDを持つタスクを削除する。
     * @param id 削除対象のタスクID
     */
    public void removeTaskById(int id) {
        // removeIfメソッドを使用し、条件(IDの一致)に合う要素をリストから削除する
        // 削除が行われた場合は true が返る
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        
        if (removed) {
            System.out.println("【削除】タスクID=" + id + " を削除しました。");
        } else {
            System.out.println("【削除失敗】タスクID=" + id + " は見つかりません。");
        }
    }

    /**
     * 指定されたタグを持つタスクを抽出して表示する。
     * @param tag 検索したいタグ
     */
    public void listTasksByTag(String tag) {
        System.out.println("\n--- タグ[" + tag + "]を含むタスク一覧 ---");
        boolean found = false;
        
        for (Task task : tasks) {
            // タスクが持つタグリストの中に、指定されたタグが含まれているか確認する
            if (task.getTags().contains(tag)) {
                System.out.println(" " + task);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println(" 該当タスクなし");
        }
    }
    
    public void sortTasks() {
        if (currentStrategy == null) {
            System.out.println("ソート戦略が設定されていません。");
            return;
        }
        // ArrayList<Task>に対してCollections.sort(..., comparator)を使う
        Collections.sort(tasks, currentStrategy::compare);
        System.out.println("【ソート完了】" + currentStrategy.getClass().getSimpleName());
    }

 
    
    
    public void setSortStrategy(SortStrategy strategy) {
        this.currentStrategy = strategy;
    }
    public ArrayList<Task> getTasks() {
        return tasks;
    }
    
    /**
     * 
     * 指定した日付(today)を過ぎている期限付きタスクを表示する
     */
    public void printOverdueTasks(String today) {
        System.out.println("\n=== 期限切れタスクの確認 (" + today + " 時点) ===");
        boolean found = false;

      
        for (Task task : tasks) {
            
            
            // タスクがDeadlineTask型、かつ 期限が今日より前(小さい)場合
            if (task instanceof todo.model.DeadlineTask) {
                todo.model.DeadlineTask dt = (todo.model.DeadlineTask) task;
                
                // 文字列同士の比較 
                if (dt.getDeadline().compareTo(today) < 0) {
                    System.out.println(" [期限切れ!] " + dt);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println(" 期限切れのタスクはありません。");
        }
    }


}