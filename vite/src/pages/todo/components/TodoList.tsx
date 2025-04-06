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

  return (
    <div>
      <ul>
        {props.todos.map((todo) => (
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
    </div>
  );
};

export default TodoList;
