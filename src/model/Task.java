package model;

import java.util.ArrayList;

import exception.DuplicateTagException;
import exception.InvalidTaskException;
import interfaces.TaskObserver;
import service.TagUtils;

/**
 * タスク管理の基底クラス。
 * タスクの基本情報（ID、タイトル、内容）に加え、進捗状態やタグ、サブタスクを管理する。
 */
public class Task {
    
    // --- フィールド変数 ---
    private int id;                 // タスクID
    private String title;           // タスク名
    private String description;         // タスク内容
    private boolean isDone;         // 完了フラグ (true: 完了, false: 未完了)
    private int urgentLevel;        // 優先度 (1以上)
    private String progress;        // 進捗ステータス ("未着手", "進行中" など)
    private ArrayList<TaskObserver> observers = new ArrayList<>(); 

    // リスト等のコレクション
    private ArrayList<Task> subTasks;   // サブタスクのリスト
    private ArrayList<String> tags;     // タグのリスト

    /**
     * コンストラクタ
     * インスタンス生成時に基本情報を設定し、リストやステータスを初期化する。
     * * @param id      タスクID
     * @param title   タスク名 (nullや空文字は不可)
     * @param Description タスク内容
     * @throws InvalidTaskException タイトルが不正な場合にスローされる
     */
    public Task(int id, String title, String description) {
        // タイトルのバリデーション（入力チェック）
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidTaskException("タイトルが空です。ID=" + id);
        }

        // フィールドの初期化
        this.id = id;
        this.title = title;
        // descriptionがnullの場合は空文字を入れる（安全性のため）
        this.description = (description != null) ? description : "";
        
        // デフォルト値の設定
        this.urgentLevel = 1;
        this.isDone = false;
        this.progress = "未着手"; // 初期ステータスを設定
        
        // リストの実体を作成（これをしないとadd時にNullPointerExceptionになる）
        this.subTasks = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    public void addObserver(TaskObserver obs) {
        observers.add(obs);
    }

    // --- ゲッターとセッター ---

    /**
     * サブタスクのリストを取得する。
     * @return サブタスクリスト
     */
    public ArrayList<Task> getSubTasks() {
        return subTasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    /**
     * タイトルを設定する。
     * @param newTitle 新しいタイトル
     * @throws InvalidTaskException タイトルが空の場合にスローされる
     */
    public void setTitle(String newTitle) throws InvalidTaskException {
        if (newTitle == null || newTitle.trim().isEmpty()) {
            throw new InvalidTaskException("タイトルを空に設定できません。");
        }
        this.title = newTitle;
        notifyObservers();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = (description != null) ? description : "";
        notifyObservers();
    }

    /**
     * サブタスクを追加する。
     * @param sub 追加するタスク
     * @throws InvalidTaskException 引数がnullの場合にスローされる
     */
    public void addSubTask(Task sub) throws InvalidTaskException {
        if (sub == null) {
            throw new InvalidTaskException("サブタスクがnullです。");
        }
        subTasks.add(sub);
    }

    public int getUrgentLevel() {
        return urgentLevel;
    }

    /**
     * 優先度を設定する。
     * 1未満の値が指定された場合はコンソールに警告を出し、変更を行わない。
     * @param level 優先度
     */
    public void setUrgentLevel(int level) {
        if (level <= 0) {
            System.out.println("優先度は1以上で指定してください。ID=" + id);
        } else {
            this.urgentLevel = level;
        }
    }
    public void setDone(boolean done) {
        this.isDone = done;
        
        notifyObservers();
    }

    public boolean isDone() {
        return isDone;
    }

    // --- タグ関連処理 ---

    public ArrayList<String> getTags() {
        return tags;
    }
    

    /**
     * タグを追加する。
     * 既に同じタグが存在する場合はエラーとする。
     * @param tag 追加したいタグ文字列
     * @throws DuplicateTagException 既にタグが存在する場合にスローされる
     */
    public void addTag(String tag) throws DuplicateTagException {
        TagUtils.checkDuplicateAndThrow(tags, tag);
        tags.add(tag);
        notifyObservers();
    }

    /**
     * タグを削除する。
     * @param tag 削除したいタグ文字列
     */
    public void removeTag(String tag) {
        tags.remove(tag);
    }

    // --- 進捗ステータス関連処理 ---

    public String getProgress() {
        return progress;
    }

    public void setProgress(String newProgress) {
        this.progress = newProgress;
        notifyObservers();
    }
    
    private void notifyObservers() {
        for (TaskObserver obs : observers) {
            obs.onTaskUpdated(this);
        }
      }

    /**
     * 進捗ステータスの文字列が有効かどうかを判定する内部メソッド。
     * @param progress 判定対象の文字列
     * @return 有効ならtrue
     */
    private boolean isValidProgress(String progress) {
        switch (progress) {
            case "未着手":
            case "進行中":
            case "保留":
            case "完了":
                return true;
            default:
                return false;
        }
    }

    /**
     * タスク情報の文字列表現を返す。
     * ID, 完了状態, タイトル, 進捗, タグ情報を含む。
     */
    @Override
    public String toString() {
        // 完了状態と進捗ステータスの両方を出力
        String status = isDone ? "[完了]" : "[未完了]";
        return String.format("Task #%d %s: %s (進捗=%s, タグ=%s)",
                id, status, title, progress, tags);
    }
}
