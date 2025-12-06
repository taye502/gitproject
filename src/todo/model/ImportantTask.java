package todo.model;

import todo.exception.InvalidTaskException;

/**
 * 重要タスクを表すサブクラス。
 * 親クラスTaskを継承し、urgentLevel(重要度)を追加管理する。
 */
public class ImportantTask extends Task {

    private int urgentLevel; // 重要度 (1以上を想定)

    /**
     * コンストラクタ
     * * @param id            タスクID
     * @param title         タイトル (null/空文字不可)
     * @param description   タスクの詳細説明
     * @param urgentLevel   重要度(1以上)
     * @throws InvalidTaskException 重要度が不正な場合や、タイトルが空の場合にスローされる
     */
    public ImportantTask(int id, String title, String description, int urgentLevel) throws InvalidTaskException {
        // 親クラス(Task)のコンストラクタを呼び出し、基本フィールドの初期化を委譲する
        super(id, title, description);
        
        // このクラス独自のバリデーションを行う
        if (urgentLevel <= 0) {
            throw new InvalidTaskException("重要度(urgentLevel)には1以上を指定してください。");
        }
        
        // バリデーション通過後、フィールドに値を設定する
        this.urgentLevel = urgentLevel;
    }

    /**
     * 現在の重要度を取得する。
     * @return 重要度の数値
     */
    public int getUrgentLevel() {
        return urgentLevel;
    }

    /**
     * 重要度を設定する。
     * 1以下の値が渡された場合はコンソールに警告を表示し、値の更新は行わない。
     * @param level 設定したい重要度
     */
    public void setUrgentLevel(int level) {
        if (level <= 0) {
            System.out.println("重要度は1以上でなければなりません。");
        } else {
            this.urgentLevel = level;
        }
    }

    /**
     * タスク情報の文字表現を返す。
     * 親のtoString()に「[重要度=XX]」を付与して表示を拡張する。
     */
    @Override
    public String toString() {
        // 親クラスのメソッド(super.toString)の結果を利用し、独自の情報を先頭に結合する
        return "[重要度=" + urgentLevel + "] " + super.toString();
    }
}