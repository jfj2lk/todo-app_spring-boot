import { todosReducer } from "@/state/todosReducer";
import { TodoType } from "@/types/todo";
import { apiRequest } from "@/utils/api";
import { useEffect, useReducer, useState } from "react";
import AddTodo from "./components/AddTodo";
import TodoList from "./components/TodoList";
import TodoDetail from "./components/TodoDetail";

const Todos = () => {
  const [todos, todoDispatch] = useReducer(todosReducer, []);
  // 選択中のTodoのID
  const [selectedTodo, setSelectedTodo] = useState<TodoType | null>(null);

  // Todos取得
  useEffect(() => {
    (async () => {
      const json = await apiRequest<TodoType[]>("/api/todos");
      todoDispatch({ type: "initialized", data: json.data });
    })();
  }, []);

  return (
    <div className="flex min-h-screen flex-col">
      <h2 className="px-7 py-4 text-2xl font-bold shadow">リスト名</h2>
      <div className="flex flex-1 px-7 py-5">
        <div className="w-[60%]">
          {/* Todo追加欄 */}
          <AddTodo todos={todos} todoDispatch={todoDispatch} />
          {/* Todo一覧 */}
          <TodoList
            todos={todos}
            todoDispatch={todoDispatch}
            selectedTodo={selectedTodo}
            setSelectedTodo={setSelectedTodo}
          />
        </div>
        {/* Todo詳細 */}
        {selectedTodo && (
          <TodoDetail todo={selectedTodo} todoDispatch={todoDispatch} />
        )}
      </div>
    </div>
  );
};

export default Todos;
