package todo.model;

import todo.exception.InvalidTaskException;

/**
 * イベント用タスククラス
 * Taskクラスを継承し、日時(dateTime)と場所(location)の情報を追加で保持する。
 */
public class EventTask extends Task {
    
    // 追加フィールド：開催日時と場所
    private String dateTime; // 開催日時(例: "2025-08-01 14:00")
    private String location; // 開催場所(例: "会議室A")

    /**
     * コンストラクタ
     * 親クラス(Task)の初期化と、このクラス独自の初期化を同時に行う。
     */
    public EventTask(int id, String title, String description,
            String dateTime, String location)
            throws InvalidTaskException {
        
        // super() は親クラス（Task）のコンストラクタを呼び出す。
        // ID、タイトル、内容は親クラス側でのバリデーションや設定に任せる。
        super(id, title, description);
        
        // 独自のフィールドを初期化
        // 三項演算子 (条件 ? trueの値 : falseの値) を使い、nullなら空文字を設定する
        this.dateTime = (dateTime != null) ? dateTime : "";
        this.location = (location != null) ? location : "";
    }

    // --- ゲッターとセッター ---

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        // セット時もnullチェックを行い、nullなら空文字にする
        this.dateTime = (dateTime != null) ? dateTime : "";
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = (location != null) ? location : "";
    }

    /**
     * タスクの情報を文字列で返すメソッド
     * 親クラスの toString() の結果に、日時と場所の情報を連結して返す。
     */
    @Override
    public String toString() {
        // [EventTask dateTime=..., loc=...] Task #1 [未完了]: タイトル... のような形式になる
        return "[EventTask dateTime=" + dateTime + ", loc=" + location + "] "
                + super.toString(); // 親クラス（Task）の toString() 結果を呼び出して結合
    }
}