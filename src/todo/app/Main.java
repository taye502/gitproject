package todo.app;

import todo.exception.InvalidProgressException;
import todo.exception.InvalidTaskException;
import todo.interfaces.SortStrategy;
import todo.model.Task;
import todo.service.ImportantTaskFactory;
import todo.service.RoutineTaskFactory;
import todo.service.SearchService;
import todo.service.SortByIdAsc;
import todo.service.SortByUrgentLevelDesc;
import todo.service.TagService;
import todo.service.TaskChangeLogger;
import todo.service.TaskFactory;
import todo.service.TaskService;

public class Main {
	public static void main(String[] args) {
		try {
			// 1) ------------------- TaskServiceを準備しタスクを追加 -------------------
			TaskService taskService = new TaskService();
			System.out.println("=== 1) TaskServiceにタスクを追加 ===");

			// Factory Method を使ってTask生成(例: 重要タスク)
			TaskFactory factory1 = new ImportantTaskFactory();
			Task t1 = factory1.createTask(5, "緊急タスク", "期限が近い重要タスク");

			// Observerパターン: タスクが更新されたらログ出力
			t1.addObserver(new TaskChangeLogger());
			t1.setProgress("進行中");
			// 別のタスク(通常TaskやRoutineTaskなど)
			Task t2 = new Task(2, "買い物", "日用品の購入");
			factory1 = new RoutineTaskFactory();
			Task t3 = factory1.createTask(3, "ルーチンタスク", "定期的に実行するタスク");

			// Serviceに登録
			taskService.addTask(t1);
			taskService.addTask(t2);
			taskService.addTask(t3);

			taskService.listAllTasks();

			// 2) ------------------- タスク更新 & 進捗変更(Observer動作テスト) -------------------
			System.out.println("\n=== 2) タスク更新 & 進捗変更(Observer発火) ===");
			taskService.updateTask(2, "スーパーで買い物", "卵・牛乳だけでなくお菓子も買う");
			taskService.changeProgress(2, "進行中"); // まだObserverを登録していなければ通知は起きない

			// t1にはObserverを登録済みなので、進捗を変えるとログが出る
			taskService.changeProgress(5, "進行中");
			taskService.changeProgress(5, "完了"); // TaskChangeLoggerが呼ばれるはず
		
//
			// 3) ------------------- タグ機能 (TagService) -------------------
			System.out.println("\n=== 3) タグ機能(TagService) ===");
			TagService tagService = new TagService();

			// タグ登録(タスク2に"日常", タスク1に"重要")
			tagService.addTagToTask(t2,"日常");
			tagService.addTagToTask(t1,"重要");
			tagService.removeTagFromTask(t1, "重要");

			// もし重複タグを追加しようとするとDuplicateTagExceptionになる

			// タグ検索
			tagService.listTasksByTag(taskService.getTasks(), "重要");
			tagService.listTasksByTag(taskService.getTasks(), "日常");
//
			// 4) ------------------- SearchServiceで検索 -------------------
			System.out.println("\n=== 4) SearchServiceで検索 ===");
			SearchService searchService = new SearchService();
			// "完了"キーワード → isDone == true
			searchService.searchTasks(taskService.getTasks(), "完了");
			// "買い物" → タイトルに"買い物"が含まれるタスク
			searchService.searchTasks(taskService.getTasks(), "買い物");
//
			// 5) ------------------- Strategyパターン(ソート) -------------------
			System.out.println("\n=== 5) Strategyパターン(ソート) ===");
			// ソート戦略を ID昇順 に切り替えてテスト
			SortStrategy byIdAsc = new SortByIdAsc();
			taskService.setSortStrategy(byIdAsc);
			taskService.sortTasks();
			taskService.listAllTasks();

			// ソート戦略を 重要度降順 に切り替えてテスト (t1がImportantTask, urgentLevelが既定値5など)
			SortStrategy byUrgentDesc = new SortByUrgentLevelDesc();
			taskService.setSortStrategy(byUrgentDesc);
			taskService.sortTasks();
			taskService.listAllTasks();
//
//			// 6) ------------------- 削除テスト -------------------
			System.out.println("\n=== 6) タスク削除テスト ===");
			taskService.removeTaskById(3); // t3削除 (例: ルーチンタスク)
			taskService.listAllTasks();
//
			// 7) ------------------- エラーケース(例外テスト) -------------------
			System.out.println("\n=== 7) エラーケース(例外テスト) ===");
			try {
				taskService.updateTask(99, "存在しないID", "エラー想定");
			} catch (InvalidTaskException e) {
				System.out.println("[ERROR] " + e.getMessage());
			}
			tagService.addTagToTask(t2,"日常"); // 重複タグ
		
			try {
				taskService.changeProgress(2, "終わり"); // 無効ステータス
			} catch (InvalidProgressException e) {
				System.out.println("[ERROR] 進捗エラー: " + e.getMessage());
			}

			System.out.println("\n=== 終了: 全機能テスト完了 ===");

		} catch (InvalidTaskException e) {
			System.out.println("[ERROR] タスク生成時の例外: " + e.getMessage());
		}
		
		System.out.println("システム終了");
	}
}
