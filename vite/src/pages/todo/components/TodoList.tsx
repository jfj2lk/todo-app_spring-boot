import { Todo, TodoReducerActions } from "@/types/todo";
import { useState } from "react";
import DeleteTodo from "./DeleteTodo";
import UpdateTodo from "./UpdateTodo";
import { apiRequest } from "@/utils/api";

const TodoList = (props: {
  todos: Todo[];
  todoDispatch: React.Dispatch<TodoReducerActions>;
}) => {
  // 編集中のTodoのID
  const [editingId, setEditingId] = useState<number | null>(null);
  // 未完了状態のTodos
  const inCompleteTodos = props.todos.filter((todo) => !todo.isCompleted);
  // 完了状態のTodos
  const completedTodos = props.todos.filter((todo) => todo.isCompleted);

  // Todoの完了・未完了状態を変更するAPIリクエストを送信
  const toggleComplete = async (todoId: number) => {
    const json = await apiRequest<Todo>(
      `/api/todos/${todoId}/toggleComplete`,
      "PATCH"
    );
    props.todoDispatch({ type: "updated", data: json.data });
  };

  // Todoを優先度で並び替える

  return (
    <div>
      {/* 並び替えメニュー */}
      <div className="flex">
        <div>
          <p>並び替え</p>
          <select name="sort">
            <option value="createdAt">作成日</option>
            <option value="dueDateTime">期限</option>
            <option value="priority">優先度</option>
          </select>
        </div>
        <div>
          <p>順序</p>
          <select name="order">
            <option value="ascending">昇順</option>
            <option value="descending">降順</option>
          </select>
        </div>
      </div>

      <h2>inComplete</h2>
      <ul>
        {inCompleteTodos.map((todo) => (
          <li key={todo.id}>
            {/* Todoが選択中の場合に、更新欄と削除ボタンを表示する */}
            {todo.id === editingId ? (
              <div className="flex">
                {/* Todo更新欄 */}
                <UpdateTodo
                  todo={todo}
                  todos={props.todos}
                  todoDispatch={props.todoDispatch}
                  editingId={editingId}
                  setEditingId={setEditingId}
                />

                {/* Todo削除ボタン */}
                <DeleteTodo
                  todo={todo}
                  todos={props.todos}
                  todoDispatch={props.todoDispatch}
                />
              </div>
            ) : (
              // Todo内容表示
              // Todoをクリックした場合、そのTodoを選択状態にする
              <div onClick={() => setEditingId(todo.id)}>
                <button onClick={() => toggleComplete(todo.id)}>〇</button>
                name: {todo.name}, description: {todo.desc}, priority:{" "}
                {todo.priority}, dueDate: {todo.dueDate}, dueTime:{" "}
                {todo.dueTime}
              </div>
            )}
          </li>
        ))}
      </ul>

      <h2>complete</h2>
      <ul>
        {completedTodos.map((todo) => (
          // Todo内容表示
          <li key={todo.id}>
            <div>
              <button onClick={() => toggleComplete(todo.id)}>〇</button>
              name: {todo.name}, description: {todo.desc}, priority:{" "}
              {todo.priority}, dueDate: {todo.dueDate}, dueTime: {todo.dueTime}
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TodoList;
