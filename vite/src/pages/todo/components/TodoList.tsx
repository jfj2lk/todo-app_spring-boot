import { Todo, TodoReducerActions } from "@/types/todo";
import { useState } from "react";
import DeleteTodo from "./DeleteTodo";
import UpdateTodo from "./UpdateTodo";

const TodoList = (props: {
  todos: Todo[];
  todoDispatch: React.Dispatch<TodoReducerActions>;
}) => {
  // 編集中のTodoのID
  const [editingId, setEditingId] = useState<number | null>(null);
  // 未完了状態のTodos
  const inCompleteTodos = props.todos.filter((todo) => todo.isCompleted);
  // 完了状態のTodos
  const completedTodos = props.todos.filter((todo) => !todo.isCompleted);

  return (
    <div>
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
              // Todoをクリックした場合、そのTodoを選択状態にする
              <div onClick={() => setEditingId(todo.id)}>
                {todo.name} : {todo.desc ?? "-"}
              </div>
            )}
          </li>
        ))}
      </ul>

      <h2>complete</h2>
      <ul>
        {completedTodos.map((todo) => (
          <li key={todo.id}>
            {todo.name} : {todo.desc ?? "-"}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TodoList;
