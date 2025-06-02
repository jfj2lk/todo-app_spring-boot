import { TodoType, TodoReducerActions } from "@/types/todo";
import { useEffect, useState } from "react";
import DeleteTodo from "./DeleteTodo";
import UpdateTodo from "./UpdateTodo";
import { apiRequest } from "@/utils/api";
import Todo from "./Todo";

const TodoList = (props: {
  todos: TodoType[];
  todoDispatch: React.Dispatch<TodoReducerActions>;
}) => {
  // 選択中のTodoのID
  const [selectedTodoId, setSelectedTodoId] = useState<number | null>(null);
  // 並び替え済みのTodos
  const [sortTodos, setSortTodos] = useState<TodoType[]>([]);
  // 未完了状態のTodos
  const inCompleteTodos = sortTodos.filter((todo) => !todo.isCompleted);
  // 完了状態のTodos
  const completedTodos = sortTodos.filter((todo) => todo.isCompleted);
  // 並び替え要素
  const [sortKey, setSortKey] = useState<string>("createdAt");
  // 並び変え順
  const [sortOrder, setSortOrder] = useState<string>("ascending");

  useEffect(() => {
    // Todosの要素を、並び替え要素と並び変え順の値で並び替える
    const sortedTodos = [...props.todos].sort((a, b) => {
      const valA = a[sortKey as keyof TodoType];
      const valB = b[sortKey as keyof TodoType];
      if (valA < valB) return sortOrder === "ascending" ? -1 : 1;
      if (valA > valB) return sortOrder === "ascending" ? 1 : -1;
      return 0;
    });
    setSortTodos(sortedTodos);
  }, [props.todos, sortKey, sortOrder]);

  return (
    <div>
      {/* 並び替えメニュー */}
      <div className="flex">
        <div>
          <p>並び替え</p>
          <select
            name="sort"
            value={sortKey}
            onChange={(e) => {
              setSortKey(e.target.value);
            }}
          >
            <option value="createdAt">作成日</option>
            <option value="dueDateTime">期限</option>
            <option value="priority">優先度</option>
          </select>
        </div>
        <div>
          <p>順序</p>
          <select
            name="order"
            value={sortOrder}
            onChange={(e) => {
              setSortOrder(e.target.value);
            }}
          >
            <option value="ascending">昇順</option>
            <option value="descending">降順</option>
          </select>
        </div>
      </div>

      <h2>未完了</h2>
      <hr />
      <ul>
        {inCompleteTodos.map((todo) => (
          <li key={todo.id}>
            <Todo
              todo={todo}
              todoDispatch={props.todoDispatch}
              selectedTodoId={selectedTodoId}
              setSelectedTodoId={setSelectedTodoId}
            />
          </li>
        ))}
      </ul>

      <h2>完了</h2>
      <hr />
      <ul>
        {completedTodos.map((todo) => (
          // Todo内容表示
          <li key={todo.id}>
            <Todo
              todo={todo}
              todoDispatch={props.todoDispatch}
              selectedTodoId={selectedTodoId}
              setSelectedTodoId={setSelectedTodoId}
            />
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TodoList;
