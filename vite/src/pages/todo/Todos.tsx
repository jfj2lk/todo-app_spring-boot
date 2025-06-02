import { todosReducer } from "@/state/todosReducer";
import { TodoType } from "@/types/todo";
import { apiRequest } from "@/utils/api";
import { useEffect, useReducer } from "react";
import AddTodo from "./components/AddTodo";
import TodoList from "./components/TodoList";

const Todos = () => {
  const [todos, todoDispatch] = useReducer(todosReducer, []);

  // Todos取得
  useEffect(() => {
    (async () => {
      const json = await apiRequest<TodoType[]>("/api/todos");
      todoDispatch({ type: "initialized", data: json.data });
    })();
  }, []);

  return (
    <div className="h-screen">
      <h2 className="px-7 py-4 text-2xl font-bold shadow">リスト名</h2>
      <div className="px-7 py-5">
        {/* Todo追加欄 */}
        <AddTodo todos={todos} todoDispatch={todoDispatch} />
        {/* Todo一覧 */}
        <TodoList todos={todos} todoDispatch={todoDispatch} />
      </div>
    </div>
  );
};

export default Todos;
