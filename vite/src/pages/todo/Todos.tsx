import { todosReducer } from "@/state/todosReducer";
import { Todo } from "@/types/todo";
import { apiRequest } from "@/utils/api";
import { useEffect, useReducer } from "react";
import AddTodo from "./components/AddTodo";
import TodoList from "./components/TodoList";

const Todos = () => {
  const [todos, todoDispatch] = useReducer(todosReducer, []);

  // Todos取得
  useEffect(() => {
    (async () => {
      const json = await apiRequest<Todo[]>("/api/todos");
      todoDispatch({ type: "initialized", data: json.data });
    })();
  }, []);

  return (
    <div>
      <h1>Todo</h1>

      {/* Todo追加欄 */}
      <AddTodo todos={todos} todoDispatch={todoDispatch} />

      {/* Todo一覧 */}
      <TodoList todos={todos} todoDispatch={todoDispatch} />
    </div>
  );
};

export default Todos;
