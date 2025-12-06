package model;

import exception.InvalidTaskException;

/**
 * ルーチンワーク（繰り返しタスク）を表すサブクラス。
 * 親クラスTaskを継承し、interval(繰り返し間隔)を追加で管理する。
 */
public class RoutineTask extends Task {
    
    // 繰り返し間隔(単位は日などを想定)
    private int interval; 

    /**
     * コンストラクタ
     * @param id          タスクID
     * @param title       タイトル
     * @param description タスクの詳細説明
     * @param interval    繰り返し間隔(1以上)
     * @throws InvalidTaskException 間隔が不正な場合や、タイトルが空の場合にスローされる
     */
    public RoutineTask(int id, String title, String description, int interval)
            throws InvalidTaskException {
        // 親クラス(Task)のコンストラクタを呼び出し、基本情報の初期化を委譲する
        super(id, title, description);
        
        // このクラス独自のバリデーションを行う
        if (interval <= 0) {
            throw new InvalidTaskException("繰り返し間隔(interval)は1以上である必要があります。");
        }
        
        this.interval = interval;
    }

    /**
     * 現在設定されている繰り返し間隔を取得する。
     * @return 間隔の値
     */
    public int getInterval() {
        return interval;
    }

    /**
     * 繰り返し間隔を設定する。
     * 1以下の値が渡された場合はコンソールに警告を表示し、値の更新は行わない。
     * @param interval 設定したい間隔
     */
    public void setInterval(int interval) {
        if (interval <= 0) {
            System.out.println("繰り返し間隔は1以上で設定してください。");
        } else {
            this.interval = interval;
        }
    }

    /**
     * タスク情報の文字表現を返す。
     * 親のtoString()の表示に加え、「[RoutineTask interval=XX]」を付与して識別しやすくする。
     */
    @Override
    public String toString() {
        return "[RoutineTask interval=" + interval + "] " + super.toString();
    }
}